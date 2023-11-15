<template>
  <div class="window2">
    <div class="head">
      <div class="buttons">
        <div class="button -red"></div>
        <div class="button -yellow"></div>
        <div class="button -green"></div>
      </div>
      <div class="tabs-group">

        <div class="tabs"  :style="tabsWidth">
          <div
              :class="[active(1), 'tab']"
              @mousedown="changeTab(1)"
          >
            <span class="text"> 관광 타입 선택 </span>
            <div
                class="close-button"
            ></div>
          </div>


        </div>


        <div key="add-button" class="add-button" @click="addTab"></div>
      </div>
    </div>
    <div class="menu">
      <div class="buttons">
        <div class="arrows">
          <div class="arrow -back"></div>
          <div class="arrow -forward"></div>
        </div>
      </div>
      <input class="search" type="text"  id="keyword" placeholder="지역/상호명 검색" @keyup="searchKeyword" />
    </div>

  </div>
</template>
<script setup>
import { ref, reactive, computed, onMounted } from 'vue';

const currentTabId = ref(1);
const nextTabId = ref(4);

const tabs = reactive([
  {
    id: 1,
    name: "여행 경로",
  },
  {
    id: 2,
    name: "관광지 리스트",
  }
]);


onMounted(() => {

})

const active = (id) => id === currentTabId.value ? "-active" : "";



const tabsWidth = computed(() => {
  return {
    maxWidth: tabs.length * 190 + "px",
    width: "100%",
    minWidth: "0px",
    transition: "max-width 0.2s",
  };
});

function changeTab(id) {
  currentTabId.value = id;
}


function addTab() {
  tabs.push({
    id: nextTabId.value,
    name: "new tab"
  });
  currentTabId.value = nextTabId.value;
  nextTabId.value++;
}








const { VITE_TRAVEL_API_KEY } = import.meta.env;
const selectedTourismType = ref("");
const searchInputKeyword = ref("");
const markers = ref([]);
const fixedMarkers = ref([]);
const fixedMarkerPositions = ref([]);

/* global kakao */
const infoWindow = ref(new kakao.maps.InfoWindow());

// map prop 정의
let prop = defineProps(['map']);

const addListenersMap = ref(new Map());
const removeListenersMap = ref(new Map());

defineExpose({callAPIWithRegionCode}); // 부모에 코드 노출

// 주어진 번호와 해당 번호에 대응하는 지역 코드를 매핑하는 객체
const regionCodeMapping = {
  11: 1,  // 서울
  26: 2,  // 인천
  27: 3,  // 대전
  28: 4,  // 대구
  29: 5,  // 광주
  21: 6,  // 부산
  31: 7,  // 울산
  36: 8,  // 세종특별자치시
  41: 31, // 경기도
  42: 32, // 강원특별자치도
  43: 33, // 충청북도
  44: 34, // 충청남도
  47: 35, // 경상북도
  48: 36, // 경상남도
  45: 37, // 전라북도
  46: 38, // 전라남도
  50: 39  // 제주도
};

onMounted(() => {
  // const marker = new kakao.maps.Marker({
  //   position: new kakao.maps.LatLng(37.566, 126.9784),
  //   map: prop.map
  // });
});


const searchKeyword = (event) => {
  let params = {
    numOfRows: 50,
    MobileOS: 'ETC',
    MobileApp: 'YourAppName',
    keyword: event.target.value,
    contentTypeId: selectedTourismType.value,
    serviceKey: VITE_TRAVEL_API_KEY,
    _type: 'json'
  };
  console.log(params.keyword)
  searchWithDebounce(params, "searchKeyword1");
}

let debounceTimeout;
function searchWithDebounce(params, keyword_search) {
  if (debounceTimeout) {
    clearTimeout(debounceTimeout);
  }
  debounceTimeout = setTimeout(() => {
    callAPI(params, keyword_search);
  }, 200);  // 300ms 동안 추가 입력이 없으면 callAPI를 호출합니다.
}


function callAPIWithRegionCode(originalCode) {
  // 매핑 객체를 사용해 지역 코드를 찾는다.
  let code = String(originalCode).slice(0, 2)
  const regionCode = regionCodeMapping[code];
  if (regionCode) {
    let params = {
      numOfRows: 60,
      MobileOS: 'ETC',
      MobileApp: 'YourAppName',
      contentTypeId: selectedTourismType.value,
      serviceKey: VITE_TRAVEL_API_KEY,
      areaCode: regionCode,
      _type: 'json'
    };
    callAPI(params, "areaBasedList1");  // 해당 코드를 함수에 전달

  } else {
    console.error(" 잘못된 지역코드 입니다. ");
  }
}



function callAPI(params, keyword_search) {
  for (let i = 0; i < markers.value.length; i++) {
    if (!fixedMarkers.value.includes(markers[i])) {
      markers.value[i].setMap(null);
    }
  }
  // markers = [];  // markers 배열 초기화

  const API_ENDPOINT = 'http://apis.data.go.kr/B551011/KorService1';
  const queryString = new URLSearchParams(params).toString();
  const requestURL = API_ENDPOINT + '/' + keyword_search + '?' + queryString;

  fetch(requestURL)
      .then(response => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
      })
      .then(data => {
        let coordinates = getMapCoordinates(data);
        updateMap(coordinates)
      })
      .catch(error => {
        console.error(error);
      });


  function getMapCoordinates(data) {
    let coordinates = [];

    for (let item of data.response.body.items.item) {
      coordinates.push({
        mapx: item.mapx,
        mapy: item.mapy,
        firstimage: item.firstimage,
        addr1: item.addr1,
        areacode: item.areacode,
        title: item.title
      });
    }
    return coordinates;
  }


  function updateMap(coordinates) {
    infoWindow.value.close();
    for (let coord of coordinates) {
      let markerPosition = new kakao.maps.LatLng(coord.mapy, coord.mapx);

      let isDuplicate = fixedMarkerPositions.value.some(fixedPosition => {
        return arePositionsClose(fixedPosition, markerPosition);
      });

      if (isDuplicate) continue;

      let marker = new kakao.maps.Marker({
        position: markerPosition,
        map: prop.map
      });

      markers.value.push(marker);

      kakao.maps.event.addListener(marker, 'click', function () {
        handleMarkerClick(marker, coord);
      });
    }
  }


  function handleAddButtonClick(marker, coord) {
    fixMarker(marker, coord);
    addToTravelPlan(coord);
  }

  function addToTravelPlan(coord) {
    const travelList = document.getElementById('travelRouteList');
    // 리스트 아이템 생성
    const listItem = document.createElement('li');
    listItem.textContent = coord.title + `-` + coord.addr1;


    listItem.draggable = true;  // 드래그 가능하게 설정

    // 드래그 시작 이벤트 처리
    listItem.addEventListener('dragstart', (e) => {
      e.dataTransfer.setData('text/plain', listItem.textContent);
      e.dataTransfer.dropEffect = 'move';
      e.target.classList.add('dragging');
    });

    // 드래그 종료 이벤트 처리
    listItem.addEventListener('dragend', (e) => {
      e.target.classList.remove('dragging');
    });


    // 삭제 버튼 추가 (선택적)
    const removeButton = document.createElement('button');
    removeButton.textContent = '삭제';
    removeButton.classList.add("deleteButton");
    removeButton.addEventListener('click', () => {
      travelList.removeChild(listItem);
    });
    listItem.appendChild(removeButton);
    listItem.dataset.lat = coord.mapy; // 위도
    listItem.dataset.lng = coord.mapx; // 경도
    listItem.dataset.title = coord.title;
    travelList.appendChild(listItem);
  }


  function handleRemoveButtonClick(marker) {
    revertMarker(marker);
  }


  function handleMarkerClick(marker, coord) {
    const isFixed = fixedMarkers.value.includes(marker);

    // 인포윈도우 업데이트 로직
    const content = generateInfoWindowContent(coord, isFixed);
    infoWindow.value.setContent(content);
    infoWindow.value.open(prop.map, marker);

    // 버튼 및 이벤트 처리 로직
    if (isFixed) {
      const removeButton = document.querySelector('.remove-marker-btn');
      if (removeListenersMap.value.has(marker)) {
        removeButton.removeEventListener('click', removeListenersMap.value.get(marker));
        removeListenersMap.value.delete(marker);
      }

      const removeListener = () => handleRemoveButtonClick(marker);
      removeListenersMap.value.set(marker, removeListener);
      removeButton.addEventListener('click', removeListener);
    } else {
      const addButton = document.querySelector('.add-marker-btn');

      if (addListenersMap.value.has(marker)) {
        addButton.removeEventListener('click', addListenersMap.value.get(marker));
        addListenersMap.value.delete(marker);
      }

      const addListener = () => handleAddButtonClick(marker, coord);
      addListenersMap.value.set(marker, addListener);
      addButton.addEventListener('click', addListener);
    }


    document.querySelector('.register-btn').addEventListener('click', function () {
      // showSweetAlert(coord);
    });

  }


  function revertMarker(targetMarker) {
    const markerTitle = targetMarker.getTitle();
    const listItemToRemove = document.querySelector(`li[data-title=" ` + markerTitle + `"]`);


    if (listItemToRemove) {
      removeFromTravelRoute(listItemToRemove);
    }

    const markerIndex = fixedMarkers.value.indexOf(targetMarker);
    if (markerIndex > -1) {
      fixedMarkers.value.splice(markerIndex, 1); // fixedMarkers 배열에서 제거
    }

    const positionIndex = fixedMarkerPositions.value.findIndex(pos => arePositionsClose(pos, targetMarker.getPosition()));
    if (positionIndex > -1) {
      fixedMarkerPositions.value.splice(positionIndex, 1);
    }

    targetMarker.setImage(null);

    // 이벤트 핸들러를 변수로 저장
    const addListener = () => handleAddButtonClick(targetMarker);

    // 이벤트 핸들러를 등록하고 제거할 때 동일한 함수를 사용
    const addButton = document.querySelector('.add-marker-btn');

    if (addListenersMap.value.has(targetMarker)) {
      addListenersMap.value.delete(targetMarker);
    }

    if (addListener != null && addButton != null) {

      addListenersMap.value.set(targetMarker, addListener);
      addButton.addEventListener('click', addListener);

    }
    // drawLine();
    infoWindow.value.close();
  }

  function removeFromTravelRoute(listItem) {
    listItem.remove();
  }


  function fixMarker(marker, coord) {
    marker.setTitle(coord.title);
    fixedMarkers.value.push(marker);
    console.log(fixedMarkers);
    fixedMarkerPositions.value.push(marker.getPosition());

    const redMarkerImage = new kakao.maps.MarkerImage(
        '${root}/assets/img/pickMarker.png',
        new kakao.maps.Size(40, 40)
    );

    marker.setImage(redMarkerImage);
    handleMarkerClick(marker, coord);  // Update the infoWindow.value content
  }

  function generateInfoWindowContent(coord, isFixed) {
    const buttonLabel = isFixed ? '삭제' : '추가';
    const buttonClass = isFixed ? 'remove-marker-btn' : 'add-marker-btn';

    return `
        <div style="display: flex; flex-direction: column; padding: 5px; align-items: start; max-width: 250px;">
            <div style="display: flex; align-items: center;">
                <img src= "` + coord.firstimage + `" alt="` + coord.title + `" style="width: 60px; height: 60px; object-fit: cover; margin-right: 10px;"/>
                <div style="font-size: 0.9rem;">
                    <strong>` + coord.title + `</strong><br/>
                    ` + coord.addr1 + `
                </div>
            </div>
            <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 5px;">
                <button class="button-common ` + buttonClass + `">` + buttonLabel + `  </button>
                <button class="button-common register-btn">등록</button>
            </div>
        </div>
    `;
  }


  function arePositionsClose(position1, position2, threshold = 0.00001) {
    const diffLat = Math.abs(position1.La - position2.La);
    const diffLng = Math.abs(position1.Ma - position2.Ma);
    return diffLat < threshold && diffLng < threshold;
  }


  document.getElementById('travelRouteList').addEventListener('click', function (event) {
    if (event.target.className === 'deleteButton') {  // 'deleteButton' 클래스를 가진 버튼을 클릭했을 때만 동작
      const listItem = event.target.closest('li');   // 삭제 버튼의 부모 li 요소를 찾습니다.
      const markerTitle = listItem.getAttribute('data-title');

      // fixedMarkers 배열에서 해당 제목과 일치하는 마커를 찾습니다.
      const targetMarker = fixedMarkers.value.find(marker => marker.getTitle() === markerTitle);
      if (targetMarker) {
        revertMarker(targetMarker); // 해당 마커의 고정을 해제합니다.
      }

      // 목록에서 해당 항목을 제거합니다.
      listItem.remove();
    }
  });
}



</script>


<style lang="scss">
$white: #fff;
$gray-dark: #dee1e6;
$gray-light: #e7eaef;

//body {
//  padding: 20px;
//  font-family: "Helvetica Neue", Arial, "Hiragino Kaku Gothic ProN", "Hiragino Sans", Meiryo, sans-serif;
//  display: flex;
//  align-items: center;
//  height: 100vh;
//  box-sizing: border-box;
//  background: linear-gradient(135deg, rgb(250,250,255) 0%, rgb(240,240,246) 50%, rgb(230,230,237) 100%);
//}

.window2 {
  position: absolute;
  z-index: 3;
  top: 20px;
  right: 30px;
  width: 30%;
  max-width: 350px;
  max-height: 20%;
  height: 15%;

  border-radius: 8px;
  overflow: hidden;
  background-color: #fff;
  box-shadow: 4px 8px 20px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;

  > .content {
    min-height: 0;
    height: 100%;
  }
}

.head {
  background-color: $gray-dark;
  display: flex;
  overflow: hidden;

  .buttons {
    display: flex;
    align-items: center;
    padding: 0 4px 0 12px;

    > .button {
      width: 12px;
      height: 12px;
      border-radius: 100%;

      &:not(:last-child) {
        margin-right: 6px;
      }

      &.-red {
        background-color: #fb5a59;
      }

      &.-yellow {
        background-color: #ffc139;
      }

      &.-green {
        background-color: #60d045;
      }
    }
  }
}

.menu {
  min-height: 36px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid $gray-dark;

  > .buttons {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 14px;

    > .arrows {
      display: flex;
      justify-content: space-between;
      width: 44px;

      > .arrow {
        width: 14px;
        height: 2px;
        background-color: #ccc;
        border-radius: 100px;
        position: relative;

        &::before,
        &::after {
          content: "";
          display: block;
          background-color: #ccc;
          position: absolute;
          width: 71%;
          height: 100%;
          border-radius: 100px;
          transform-origin: center left;
        }

        &::before {
          transform: rotate(45deg);
        }

        &::after {
          transform: rotate(-45deg);
        }

        &.-forward {
          transform: rotate(180deg);
        }
      }
    }
  }

  > .search {
    box-sizing: border-box;
    border-radius: 100px;
    border: 0;
    background-color: #f1f1f1;
    height: 26px;
    outline: 0;
    flex: 1;
    margin-right: 20px;
    padding: 0 12px;
    font-size: 12px;
    border: 2px solid transparent;

    &:focus {
      border: 2px solid #6dbce7;
    }
  }
}

.tabs-group {
  width: 100%;
  display: flex;
  align-items: center;
  padding-top: 8px;
  min-width: 0px;

  > .add-button {
    width: 14px;
    height: 14px;
    display: flex;
    align-items: center;
    justify-content: center;
    align-self: center;
    padding: 8px;
    margin: 0 4px 2px;
    border-radius: 100%;
    transition: background-color 0.5s;

    &:hover {
      background-color: #ccc;
    }

    &::before,
    &::after {
      content: "";
      position: absolute;
      display: block;
      width: 2px;
      height: 14px;
      background-color: #666;
    }

    &::before {
      transform: rotate(90deg);
    }
  }
}

.tabs {
  display: inline-flex;
  align-items: flex-end;
  box-sizing: border-box;
  padding: 0 8px;
  line-height: 1;
  position: relative;
  z-index: 5;
  user-select: none;
  width: 100%;
}

.tab {
  position: relative;
  background-color: transparent;
  padding: 14px 12px;
  border-radius: 10px 10px 0 0;
  font-size: 12px;
  display: block;
  max-width: 160px;
  transition: background-color 0.2s;
  cursor: default;
  position: relative;
  margin-right: -1px;
  white-space: nowrap;
  flex: 1;
  overflow: hidden;
  display: flex;
  justify-content: space-between;
  align-items: center;

  &:hover {
    background-color: $gray-light;

    &::before {
      background-color: $gray-light;
      right: 0;
    }
  }

  &.-active {
    background-color: $white;

    &::before {
      background-color: $white;
      right: 0;
    }
  }

  &.-dragging {
    opacity: 0;
  }

  &::after {
    content: "";
    display: block;
    height: 20px;
    width: 1px;
    background-color: #888;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    right: 0;
    z-index: -1;
  }

  &::before {
    content: "";
    display: block;
    height: 20px;
    width: 30px;
    position: absolute;
    right: 1px;
    background-color: $gray-dark;
    transition: background-color 0.2s;
  }

  > .close-button {
    width: 10px;
    height: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    transform: rotate(45deg);
    border-radius: 100%;
    padding: 4px;
    position: absolute;
    right: 8px;

    &:hover {
      background-color: #ddd;
    }

    &::before,
    &::after {
      content: "";
      position: absolute;
      display: block;
      width: 2px;
      height: 12px;
      background-color: #666;
    }

    &::before {
      transform: rotate(90deg);
    }
  }
}

.tab-content {
  display: none;
  padding: 20px;
  height: 100%;
  box-sizing: border-box;

  &.-active {
    display: block;
  }

  > .image {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}
</style>

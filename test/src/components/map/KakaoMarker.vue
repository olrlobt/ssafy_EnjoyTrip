<template>
  <template></template>
</template>
<script setup>
import {ref, onMounted, defineProps} from 'vue';
import {useMapStore} from "@/stores/map";

const mapStore = useMapStore();

onMounted(() => {

})

const {VITE_TRAVEL_API_KEY} = import.meta.env;
const selectedTourismType = ref("");
const markers = ref([]);
const fixedMarkers = ref([]);
const fixedMarkerPositions = ref([]);

/* global kakao */
const infoWindow = ref(new kakao.maps.InfoWindow());

// map prop 정의
let prop = defineProps(['map', 'changeSelectMarker']);
const addListenersMap = ref(new Map());
const removeListenersMap = ref(new Map());

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

});


const searchKeyword = (event) => {
  if (event) {
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
}

defineExpose({callAPIWithRegionCode, searchKeyword}); // 부모에 코드 노출

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
    prop.changeSelectMarker(false);

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
    console.log("click")
    fixMarker(marker, coord);
    addToTravelPlan(coord);
  }




  function addToTravelPlan(coord) {
    console.log("add Travel Plan")
    console.log(coord);
    console.log(mapStore.travelList);
    mapStore.travelList.push(coord);

  }


  function handleRemoveButtonClick(marker) {
    revertMarker(marker);
  }

  function handleMarkerClick(marker, coord) {
    prop.changeSelectMarker(true);

    const isFixed = fixedMarkers.value.includes(marker);

    mapStore.detailState = coord;
    console.log(mapStore.detailState);

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

    // 등록버튼
    // document.querySelector('.register-btn').addEventListener('click', function () {
    //   // showSweetAlert(coord);
    // });
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

    targetMarker.setImage(null); // 마커 기본 이미지로

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
    prop.changeSelectMarker(false);
  }

  const removeFromTravelRoute = (listItem) => {
    listItem.remove();
  }

  function fixMarker(marker, coord) {
    marker.setTitle(coord.title);
    fixedMarkers.value.push(marker);
    fixedMarkerPositions.value.push(marker.getPosition());

    const redMarkerImage = new kakao.maps.MarkerImage(
        'src/assets/images/pickMarker.png',
        new kakao.maps.Size(40, 40)
    );

    marker.setImage(redMarkerImage);
    handleMarkerClick(marker, coord);  // Update the infoWindow.value content
  }

  function generateInfoWindowContent(coord, isFixed) {
    const buttonLabel = isFixed ? '삭제' : '추가';
    const buttonClass = isFixed ? 'remove-marker-btn' : 'add-marker-btn';

    return `
        <div class="info-window-container">
            <div class="info-window-header">
                <img class="info-window-image" src= "` + coord.firstimage + `" alt="` + coord.title + `"/>
                <div class="info-window-title">` + coord.title + `</div>
            </div>
            <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 5px;">
                <button class="button-common ` + buttonClass + `">` + buttonLabel + `</button>
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
      listItem.remove();
    }
  });
}


</script>


<style lang="scss">

.close-button {
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

@import "src/assets/css/map/infowindow";

</style>

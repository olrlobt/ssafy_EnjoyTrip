<template>
  <template></template>
</template>
<script setup>
import {defineProps, ref, watch} from 'vue';
import {useMapStore} from "@/stores/map";

const mapStore = useMapStore();
const {VITE_TRAVEL_API_KEY} = import.meta.env;
const selectedTourismType = ref("");
const markers = ref([]);
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
    searchWithDebounce(params, "searchKeyword1");
  }
}

watch(() => mapStore.fixedMarkers, () => {
  console.log(mapStore.fixedMarkers)
});


defineExpose({callAPIWithRegionCode, searchKeyword}); // 부모에 코드 노출

let debounceTimeout;
function searchWithDebounce(params, keyword_search) {
  if (debounceTimeout) {
    clearTimeout(debounceTimeout);
  }
  debounceTimeout = setTimeout(() => {
    callAPI(params, keyword_search);
  }, 200);
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
    if (!mapStore.fixedMarkers.some(fixed => fixed === markers.value[i])) {
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
        updateMap(getMapCoordinates(data))
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

      if (mapStore.fixedMarkers.some(fixedPosition =>
          arePositionsClose(fixedPosition.getPosition(), markerPosition))) {
        continue;
      }

      let marker = new kakao.maps.Marker({
        position: markerPosition,
        map: prop.map
      });

      markers.value.push(marker);
      kakao.maps.event.addListener(marker, 'click', ()=> handleMarkerClick(marker, coord));
    }
  }


  /**
   * 마커 클릭 이벤트
   */
  function handleMarkerClick(marker, coord) {
    prop.changeSelectMarker(true);
    mapStore.currentSelectMarker = coord;
    const isFixed = mapStore.fixedMarkers.includes(marker);

    const content = generateInfoWindowContent(coord, isFixed);
    infoWindow.value.setContent(content);
    infoWindow.value.open(prop.map, marker);
    mapStore.infoWindow = infoWindow.value;

    if (isFixed) { // 고정된 마커일 경우
      const removeButton = document.querySelector('.remove-marker-btn');
      if (removeListenersMap.value.has(marker)) {
        removeButton.removeEventListener('click', removeListenersMap.value.get(marker));
        removeListenersMap.value.delete(marker);
      }

      removeListenersMap.value.set(marker, () => revertMarker(marker));
      removeButton.addEventListener('click', () => revertMarker(marker));
    } else {
      const addButton = document.querySelector('.add-marker-btn');

      if (addListenersMap.value.has(marker)) {
        addButton.removeEventListener('click', addListenersMap.value.get(marker));
        addListenersMap.value.delete(marker);
      }
      addListenersMap.value.set(marker, () => handleAddButtonClick(marker, coord));
      addButton.addEventListener('click', () => handleAddButtonClick(marker, coord));
    }

    // 등록버튼
    // document.querySelector('.register-btn').addEventListener('click', function () {
    //   // showSweetAlert(coord);
    // });
  }


  function revertMarker(targetMarker) {

    let travelList = mapStore.travelList;
    mapStore.travelList = travelList.filter(value => value.title !== targetMarker.getTitle());

    const markerIndex = mapStore.fixedMarkers.indexOf(targetMarker);
    if (markerIndex > -1) {
      mapStore.fixedMarkers.splice(markerIndex, 1); // fixedMarkers 배열에서 제거
    }

    const positionIndex = fixedMarkerPositions.value.findIndex(pos => arePositionsClose(pos, targetMarker.getPosition()));
    if (positionIndex > -1) {
      fixedMarkerPositions.value.splice(positionIndex, 1);
    }
    targetMarker.setImage(null); // 마커 기본 이미지로

    // 이벤트 핸들러를 변수로 저장
    const addListener = () => handleAddButtonClick(targetMarker);
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

  /**
   * 마커 추가 버튼 눌렀을때
   */
  function handleAddButtonClick(marker, coord) {
    fixMarker(marker, coord);
    mapStore.travelList.push(coord);
  }

  function fixMarker(marker, coord) {
    marker.setTitle(coord.title);
    marker.setImage(new kakao.maps.MarkerImage(
        'src/assets/images/pickMarker.png',
        new kakao.maps.Size(40, 40)
    ));

    mapStore.fixedMarkers.push(marker);
    fixedMarkerPositions.value.push(marker.getPosition());
    handleMarkerClick(marker, coord);
  }

  function generateInfoWindowContent(coord, isFixed) {
    return `
        <div class="info-window-container">
            <div class="info-window-header">
                <img class="info-window-image" src= "` + coord.firstimage + `" alt="` + coord.title + `"/>
                <div class="info-window-title">` + coord.title + `</div>
            </div>
            <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 5px;">
                <button class="button-common ` + (isFixed ? 'remove-marker-btn' : 'add-marker-btn') + `">` + (isFixed ? '삭제' : '추가') + `</button>
                <button class="button-common register-btn">등록</button>
            </div>
        </div>
    `;
  }

  const arePositionsClose = (position1, position2, threshold = 0.00001) =>
      Math.abs(position1.La - position2.La) < threshold && Math.abs(position1.Ma - position2.Ma) < threshold;
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

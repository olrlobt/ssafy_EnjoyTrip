<template>
  <template></template>
</template>
<script setup>
import {defineProps, onMounted, ref} from 'vue';
import {useMapStore} from "@/stores/map";
/* global kakao */
const mapStore = useMapStore();
const {VITE_TRAVEL_API_KEY} = import.meta.env;
const selectedTourismType = ref("");
const fixedMarkerPositions = ref([]);

// map prop 정의
let prop = defineProps(['changeSelectMarker']);

const addListenersMap = ref(new Map());
const removeListenersMap = ref(new Map());


onMounted( ()=> {

  mapStore.markers.splice(0, mapStore.markers.length);
  mapStore.fixedMarkers.splice(0, mapStore.fixedMarkers.length);
  if(mapStore.coord){
    mapStore.coord.forEach(value => mapStore.travelList.push({coord: value}));
    mapStore.coord.splice(0, mapStore.coord.length);
  }
  if(mapStore.travelList){
    const coordinates = [];
    mapStore.travelList.forEach(value => coordinates.push(value.coord));
    mapStore.travelList.splice(0, mapStore.travelList.length);
    updateMap(coordinates);
    for (let idx = 0; idx < mapStore.markers.length; idx++) {
        mapStore.travelList.push({
          marker: mapStore.markers[idx].marker,
          coord: coordinates[idx]
        })
        fixMarker(mapStore.markers[idx].marker,coordinates[idx])
    }
    // setMapBounds();
  }

  if(mapStore.infoWindow){
    mapStore.infoWindow.close();
  }

})


function setMapBounds() {
  let bounds = new kakao.maps.LatLngBounds();

  if(mapStore.markers){
    mapStore.markers.forEach(marker => {

      let markerPosition = new kakao.maps.LatLng(marker.mapy, marker.mapx);

      new kakao.maps.Marker({
        position: markerPosition,
        title: marker.title,
        map: mapStore.map
      });
      bounds.extend(new kakao.maps.LatLng(marker.mapy, marker.mapx));
    });

    mapStore.map.setBounds(bounds);
  }
}


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

// watch(() => mapStore.fixedMarkers, () => {
//   console.log(mapStore.fixedMarkers)
// });


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
      numOfRows: 100,
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

  for (let i = 0; i < mapStore.markers.length; i++) {
    if (!mapStore.fixedMarkers.some(fixed => fixed === mapStore.markers[i].marker)) {
      mapStore.markers[i].marker.setMap(null);
    }
  }

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

}

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
  mapStore.infoWindow.close();
  prop.changeSelectMarker(false);

  for (let coord of coordinates) {

    let markerPosition = new kakao.maps.LatLng(coord.mapy, coord.mapx);

    if (mapStore.fixedMarkers.some(fixedPosition =>
        arePositionsClose(fixedPosition.getPosition(), markerPosition))) {
      continue;
    }

    let marker = new kakao.maps.Marker({
      position: markerPosition,
      title: coord.title,
      map: mapStore.map
    });
    mapStore.markers.push({marker, coord});
    kakao.maps.event.addListener(marker, 'click', ()=> handleMarkerClick(marker, coord));
  }
}

function revertMarker(targetMarker) {

  mapStore.removeIndexOfTravelList(targetMarker);

  const markerIndex = mapStore.fixedMarkers.indexOf(targetMarker);
  if (markerIndex > -1) {
    mapStore.fixedMarkers.splice(markerIndex, 1);
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
  mapStore.infoWindow.close();
  prop.changeSelectMarker(false);
}

/**
 * 마커 클릭 이벤트
 */
function handleMarkerClick(marker, coord) {
  prop.changeSelectMarker(true);

  mapStore.currentSelectMarker.marker = marker;
  mapStore.currentSelectMarker.coord = coord;

  openMarkerInfowindow(marker, coord);

}


const openMarkerInfowindow = (marker, coord) => {
  const isFixed = mapStore.fixedMarkers.includes(marker);
  const content = generateInfoWindowContent(coord, isFixed);
  mapStore.infoWindow.setContent(content);
  mapStore.infoWindow.open(mapStore.map, marker);

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
}

mapStore.updateOpenMarkerInfowindow(openMarkerInfowindow);
/**
 * 마커 추가 버튼 눌렀을때
 */
function handleAddButtonClick(marker, coord) {
  fixMarker(marker, coord);
  mapStore.travelList.push({marker, coord});
}

function fixMarker(marker, coord) {
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
            <img class="info-window-image" src="` + coord.firstimage + `"/>
            <div class="info-window-title">` + coord.title + `</div>
          </div>
          <div class="info-window-footer">
            <button class="button-common ` + (isFixed ? 'remove-marker-btn' : 'add-marker-btn') + `"">
              ` + (isFixed ? '삭제' : '추가') + `
            </button>
          </div>
        </div>
    `;
}

const arePositionsClose = (position1, position2, threshold = 0.00001) =>
    Math.abs(position1.La - position2.La) < threshold && Math.abs(position1.Ma - position2.Ma) < threshold;


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

@import "../../assets/css/map/infowindow";

</style>

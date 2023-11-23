<script setup>
import {ref, watch} from 'vue';
import {useMapStore} from "@/stores/map";
import {VueDraggableNext} from 'vue-draggable-next'
import {saveTravelRoute} from '@/api/share'
import Swal from 'sweetalert2'
import {useMemberStore} from "@/stores/member";
import KakaoListItem from "@/components/map/KakaoListItem.vue";

/* global kakao */
const mapStore = useMapStore();
const memberStore = useMemberStore();
const currentTabId = ref(1);
const localFixedMarker = ref([]);
const active = (id) => id === currentTabId.value ? "-active" : "";


const props = defineProps({
  searchKeyword: Function,
});

const searchKeyup = (event) => {
  props.searchKeyword(event);
}

function changeTab(id) {
  currentTabId.value = id;
}

localFixedMarker.value = mapStore.fixedMarkers;

watch(() => mapStore.fixedMarkers, (fixedList) => {
  localFixedMarker.value = fixedList;
});

const removeFromTravelPlan = (index) => {
  const titleToRemove = mapStore.travelList[index].coord.title;

  for (let i = mapStore.fixedMarkers.length - 1; i >= 0; i--) {
    if (mapStore.fixedMarkers[i].Gb === titleToRemove) {
      mapStore.fixedMarkers[i].setImage(null);
      mapStore.fixedMarkers.splice(i, 1);
    }
  }

  if (mapStore.currentSelectMarker.coord.title === titleToRemove) {
    mapStore.infoWindow.close();
  }

  mapStore.travelList.splice(index, 1);
};

function clickSideList(item) {
  mapStore.currentSelectMarker = {...item};
  mapStore.infoWindow.close();
  mapStore.openMarkerInfowindow(item.marker, item.coord);
}

const isOpen = ref(false);

const toggleOpen = () => {
  isOpen.value = !isOpen.value;
};


watch([() => mapStore.travelList, () => mapStore.travelList.length],
    () => {
      drawTravelLine();
    }
);

const travelLine = ref(null);

function drawTravelLine() {
  if (travelLine.value) {
    travelLine.value.setMap(null);
  }

  const linePath = [];
  mapStore.travelList.forEach(item => {
    linePath.push(new kakao.maps.LatLng(item.coord.mapy, item.coord.mapx));
  });

  const polylineOptions = {
    path: linePath,
    strokeWeight: 3,
    strokeColor: '#db4040',
    strokeOpacity: 0.7,
    strokeStyle: 'solid'
  };

  travelLine.value = new kakao.maps.Polyline(polylineOptions);
  travelLine.value.setMap(mapStore.map);
}


function findShortestPathFromStart() {
  for (let i = 0; i < mapStore.travelList.length - 1; i++) {
    let minDistance = Infinity;
    let nearestIndex = -1;

    for (let j = i + 1; j < mapStore.travelList.length; j++) {
      const distance = getDistance(
          mapStore.travelList[i].coord.mapy, mapStore.travelList[i].coord.mapx,
          mapStore.travelList[j].coord.mapy, mapStore.travelList[j].coord.mapx
      );
      if (distance < minDistance) {
        nearestIndex = j;
        minDistance = distance;
      }
    }

    if (nearestIndex !== -1) {
      [mapStore.travelList[i + 1], mapStore.travelList[nearestIndex]] =
          [mapStore.travelList[nearestIndex], mapStore.travelList[i + 1]];
    }

    mapStore.travelList = [...mapStore.travelList];
  }
}


function findShortestRoundTrip() {
  let bestDistance = Infinity;
  let bestPath = [];

  function permute(arr, start = 1) {
    if (start === arr.length - 1) {
      let distance = 0;
      for (let i = 0; i < arr.length - 1; i++) {
        distance += getDistance(
            arr[i].coord.mapy, arr[i].coord.mapx,
            arr[i + 1].coord.mapy, arr[i + 1].coord.mapx
        );
      }
      if (distance < bestDistance) {
        bestDistance = distance;
        bestPath = arr.slice();
      }
      return;
    }

    for (let i = start; i < arr.length; i++) {
      [arr[start], arr[i]] = [arr[i], arr[start]];
      permute(arr, start + 1);
      [arr[start], arr[i]] = [arr[i], arr[start]];
    }
  }

  permute(mapStore.travelList);
  mapStore.travelList = [...bestPath];
}


//모든 경로중 최단경로 얻기
function findShortestPathAnyStart() {
  let bestDistance = Infinity;
  let bestPath = [];

  function permute(arr, start = 0) {
    if (start === arr.length - 1) {
      let distance = 0;
      for (let i = 0; i < arr.length - 1; i++) {
        distance += getDistance(
            arr[i].coord.mapy, arr[i].coord.mapx,
            arr[i + 1].coord.mapy, arr[i + 1].coord.mapx
        );
      }
      // 마지막 지점에서 시작 지점으로 돌아오는 거리 추가
      distance += getDistance(
          arr[arr.length - 1].coord.mapy, arr[arr.length - 1].coord.mapx,
          arr[0].coord.mapy, arr[0].coord.mapx
      );

      if (distance < bestDistance) {
        bestDistance = distance;
        bestPath = arr.slice();
      }
      return;
    }

    for (let i = start; i < arr.length; i++) {
      [arr[start], arr[i]] = [arr[i], arr[start]];
      permute(arr, start + 1);
      [arr[start], arr[i]] = [arr[i], arr[start]]; // 원상 복구
    }
  }

  permute(mapStore.travelList);
  mapStore.travelList = [...bestPath];
}


function toRadians(degrees) {
  return degrees * Math.PI / 180;
}

//하버사인 공식
function getDistance(lat1, lon1, lat2, lon2) {
  var R = 6371e3; // 지구의 반지름 (m)
  var φ1 = toRadians(lat1);
  var φ2 = toRadians(lat2);
  var Δφ = toRadians(lat2 - lat1);
  var Δλ = toRadians(lon2 - lon1);

  var a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
      Math.cos(φ1) * Math.cos(φ2) *
      Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

  return R * c; // 결과는 미터 단위
}


const saveMyTravelRoute = () => {
  console.log(mapStore.travelList);

  const markers = [];
  mapStore.travelList.forEach(value => {
    markers.push(value.coord);
  })

  Swal.fire({
    title: "경로 제목",
    input: "text",
    inputAttributes: {
      autocapitalize: "off"
    },
    showCancelButton: true,
    cancelButtonText : "취소",
    confirmButtonText: "저장",
    showLoaderOnConfirm: true,
    preConfirm: async (title) => {
      try {
        console.log(memberStore.userInfo)
        const travelRouteDto = {
          subject : title,
          userId: memberStore.userInfo.userId,
          markers : markers
        }
        saveTravelRoute(travelRouteDto);

      } catch (error) {
        Swal.showValidationMessage(`
        Request failed: ${error}
      `);
      }
    },
    allowOutsideClick: () => !Swal.isLoading()
  }).then((result) => {
    if (result.isConfirmed) {
      Swal.fire({
        title: `저장 완료`,
        icon: "success",
        timer: 700,
      });
    }
  });}



const clearMyTravelRoute = () => {
  mapStore.travelList.splice(0, mapStore.travelList.length);

  for (let i = mapStore.fixedMarkers.length - 1; i >= 0; i--) {
      mapStore.fixedMarkers[i].setImage(null);
  }
  mapStore.fixedMarkers.splice(0, mapStore.fixedMarkers.length);
  mapStore.infoWindow.close();
}



</script>

<template>
  <div class="window">
    <div class="head">
      <!--      <div class="buttons">-->
      <!--        <div class="button -red"></div>-->
      <!--        <div class="button -yellow"></div>-->
      <!--        <div class="button -green"></div>-->
      <!--      </div>-->
      <div class="tabs-group">

        <div class="tabs">
          <div
              :class="[active(1), 'tab']"
              @mousedown="changeTab(1)"
          >
            <span class="text">여행경로</span>
            <div
                class="close-button"
            ></div>
          </div>

          <div
              :class="[active(2), 'tab']"
              @mousedown="changeTab(2)"
          >
            <span class="text"> 관광리스트</span>
            <div
                class="close-button"
            ></div>
          </div>
        </div>


        <div class="icon nav-icon-5 " :class="{ open: isOpen }" @click="toggleOpen">
          <span></span>
          <span></span>
          <span></span>
        </div>
        <!-- 햄버거 토스트 자리 -->

      </div>
    </div>
    <div class="side-menu" :class="{ 'show-menu': isOpen }" >
      <div style="background-color: #f9f9f9; font-weight: bold; font-size: medium;" >  정렬 알고리즘 </div>
      <div @click="findShortestPathFromStart" style="background-color: #fff; font-size: small;"> 출발지 기준 최단거리</div>
      <div @click="findShortestRoundTrip" style="background-color: #fff; font-size: small;"> 출발지에서 출발지까지 최단거리</div>
      <div @click="findShortestPathAnyStart" style="background-color: #fff; font-size: small;"> 전체 중 최단 거리</div>
    </div>

    <div v-if="currentTabId == 2" class="menu">
      <div class="buttons">
        <!--        <div class="arrows">-->
        <!--          <div class="arrow -back"></div>-->
        <!--          <div class="arrow -forward"></div>-->
        <!--        </div>-->

        <select id="tourismType">
          <option value="12" selected>관광타입 선택</option>
          <option value="12">관광지</option>
          <option value="14">문화시설</option>
          <option value="15">축제공연행사</option>
          <option value="25">여행코스</option>
          <option value="28">레포츠</option>
          <option value="32">숙박</option>
          <option value="38">쇼핑</option>
          <option value="39">음식점</option>
        </select>
      </div>
      <input class="search" type="text" id="keyword" placeholder="지역/상호명 검색" @keyup="searchKeyup"/>
    </div>


    <div class="content">
      <div :class="[active(1), 'tab-content']">

        <VueDraggableNext id="travelRouteList" v-model="mapStore.travelList">
          <KakaoListItem :item="mapStore.travelList" :click="clickSideList" :removeClick="removeFromTravelPlan"></KakaoListItem>
        </VueDraggableNext>
        <div class="chrome-style-button-container">
          <button class="chrome-style-button">여행 경로</button>
          <div class="slide-menu">
            <!-- 여기에 메뉴 아이템들을 배치합니다 -->
            <a @click="saveMyTravelRoute">내 경로 저장하기</a>
            <a @click="clearMyTravelRoute">초기화</a>
            <!-- 추가 메뉴 아이템들 -->
          </div>
        </div>
      </div>
      <div :class="[active(2), 'tab-content']" class="side-list">
        <KakaoListItem :item="mapStore.markers" :click="clickSideList"></KakaoListItem>
      </div>
    </div>
  </div>

</template>


<style lang="scss" scoped>
@import "../../assets/scss/map/mapAppleThema";
@import "../../assets/scss/map/mapTravelList";


.window {
  width: 100%;
  max-width: 350px;
  max-height: 100%;
  height: 100%;
  z-index: 4;
}

.side-list {
  overflow: scroll;
}

.chrome-style-button-container {
  position: relative;
  width: 100%;
}

.chrome-style-button {
  display: block;
  width: 100%;
  padding: 10px 20px;

  background-color: #7fa3cc; /* 크롬 브라우저의 특징적인 파란색 */
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  text-align: center;
  cursor: pointer;
  transition: background-color 0.3s;
}

.chrome-style-button:hover {
  background-color: #357ae8; /* 마우스 호버 시 색상 변화 */
}


.slide-menu {
  position: absolute;
  bottom: 100%; /* 버튼 바로 위에 위치하도록 설정 */
  left: 0;
  width: 100%;
  background: #f0f0f0;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  border-radius: 4px;
  overflow: hidden;
  transform: translateY(10px);
  opacity: 0;
  transition: all 0.3s ease;
  z-index: 1000;
}

.chrome-style-button-container:hover .slide-menu {
  transform: translateY(0);
  opacity: 1;
}

.slide-menu a {
  display: block;
  padding: 10px 20px;
  color: black;
  text-decoration: none;
  border-bottom: 1px solid #eee;
}

.slide-menu a:hover {
  background-color: #f0f0f0;
}


</style>

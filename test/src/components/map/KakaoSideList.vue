<template>
  <div class="window">
    <div class="head">
<!--      <div class="buttons">-->
<!--        <div class="button -red"></div>-->
<!--        <div class="button -yellow"></div>-->
<!--        <div class="button -green"></div>-->
<!--      </div>-->
      <div class="tabs-group">

        <div class="tabs"  >
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


        <div key="add-button" class="minus-button"></div>
      </div>
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
      <input class="search" type="text"  id="keyword" placeholder="지역/상호명 검색" @keyup="searchKeyup" />
    </div>

    
    
    <div class="content">
        <div :class="[active(1), 'tab-content']" >

          <VueDraggableNext id="travelRouteList" v-model="mapStore.travelList">
            <div
                class="list-group-item bg-gray-300 m-1 p-3 rounded-md text-center travel-item"
                v-for="(item,index) in mapStore.travelList"
                :key="item.coord.id"
                @click="clickSideList(item)"
            >
              <img :src="item.coord.firstimage" class="travel-item-image" alt="">
              <span class="travel-item-title">{{ item.coord.title }}</span>
              <div class="close-button2" @click="removeFromTravelPlan(index)"></div>
            </div>
          </VueDraggableNext>

        </div>
      <div :class="[active(2), 'tab-content']" class="side-list">
        <div
            class="list-group-item bg-gray-300 m-1 p-3 rounded-md text-center travel-item"
            v-for="(item) in mapStore.currentSideList"
            :key="item.id"
        >
          <img :src="item.firstimage" class="travel-item-image" alt="">
          <span class="travel-item-title">{{ item.title }}</span>
        </div>
      </div>
    </div>
  </div>





</template>
<script setup>
import { ref,  watch } from 'vue';
import {useMapStore} from "@/stores/map";
import { VueDraggableNext } from 'vue-draggable-next'

const mapStore = useMapStore();
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

// watch(() => mapStore.travelList, (newList) => {
//   localTravelPlan.value = newList;
// }, { deep: true });

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

  if(mapStore.currentSelectMarker.title === titleToRemove){
    mapStore.infoWindow.close();
  }

  mapStore.travelList.splice(index, 1);
};

function clickSideList(item){

  mapStore.currentSelectMarker = item;

}


</script>


<style lang="scss" scoped>
@import "src/assets/scss/map/mapAppleThema";
@import "src/assets/scss/map/mapTravelList";

.window{
  width: 100%;
  max-width: 350px;
  max-height: 100%;
  height: 100%;
}

.side-list{
  overflow: scroll;
}

</style>

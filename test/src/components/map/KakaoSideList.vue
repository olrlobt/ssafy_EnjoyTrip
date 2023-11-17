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


        <div key="add-button" class="minus-button" @click="addTab"></div>
      </div>
    </div>

    <div v-if="currentTabId == 2" class="menu">
      <div class="buttons">
        <div class="arrows">
          <div class="arrow -back"></div>
          <div class="arrow -forward"></div>
        </div>
      </div>
      <input class="search" type="text"  id="keyword" placeholder="지역/상호명 검색" @keyup="searchKeyup" />
    </div>

    
    
    <div class="content">
        <div :class="[active(1), 'tab-content']" >

          <VueDraggableNext id="travelRouteList" v-model="localTravelPlan" @end="handleEnd">
            <div
                class="list-group-item bg-gray-300 m-1 p-3 rounded-md text-center travel-item"
                v-for="(item,index) in localTravelPlan"
                :key="item.id"
            >
              <img :src="item.firstimage" class="travel-item-image" alt="">
              <span class="travel-item-title">{{ item.title }}</span>
              <div class="close-button2" @click="removeFromTravelPlan(index)"></div>
            </div>
          </VueDraggableNext>

        </div>
      <div :class="[active(2), 'tab-content']" >
        <h1>hi</h1>
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
const localTravelPlan = ref([]);
const localFixedMarker = ref([]);
const active = (id) => id === currentTabId.value ? "-active" : "";

const props = defineProps({
  searchKeyword: Function
});

const searchKeyup = (event) => {
  props.searchKeyword(event);
}

function changeTab(id) {
  currentTabId.value = id;
}

localTravelPlan.value = mapStore.travelList;
localFixedMarker.value = mapStore.fixedMarkers;

watch(() => mapStore.travelList, (newList) => {
  localTravelPlan.value = newList;
}, { deep: true });

watch(() => mapStore.fixedMarkers, (fixedList) => {
  localFixedMarker.value = fixedList;
});

const handleEnd = () => {
  mapStore.updateTravelList(localTravelPlan.value);
};

const removeFromTravelPlan = (index) => {

  const { matching, notMatching } = localFixedMarker.value.reduce((acc, fixed) => {
    if (fixed.Gb === localTravelPlan.value[index].title) {
      acc.matching.push(fixed);
    } else {
      acc.notMatching.push(fixed);
    }
    return acc;
  }, { matching: [], notMatching: [] });

  mapStore.fixedMarkers = notMatching;

  if(matching){
    matching[0].setImage(null);
  }
  localTravelPlan.value.splice(index, 1);
};


</script>


<style lang="scss" scoped>
@import "src/assets/scss/map/mapAppleThema";
@import "src/assets/scss/map/mapTravelList";
.window{
  top: 0px;
  left: 0px;
  width: 100%;
  max-width: 350px;
  max-height: 100%;
  height: 100%;
}

</style>

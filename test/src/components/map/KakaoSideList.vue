<template>
  <div class="window">
    <div class="head">
<!--      <div class="buttons">-->
<!--        <div class="button -red"></div>-->
<!--        <div class="button -yellow"></div>-->
<!--        <div class="button -green"></div>-->
<!--      </div>-->
      <div class="tabs-group">

        <div class="tabs"  :style="tabsWidth">
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


        <div key="add-button" class="add-button" @click="addTab"></div>
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
          <ul id="travelRouteList">
            <li v-for="(item, index) in travelPlan" :key="index"
                draggable="true"
                @dragstart="handleDragStart(index, $event)"
                @dragover.prevent
                @drop="handleDrop(index)">
              {{ item.title }}
              <button @click="removeFromTravelPlan(index)">삭제</button>
            </li>
          </ul>



        </div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue';
import {useMapStore} from "@/stores/map";

const mapStore = useMapStore();
const currentTabId = ref(1);


onMounted(() => {

})

const active = (id) => id === currentTabId.value ? "-active" : "";


const props = defineProps({
  searchKeyword: Function
});

const searchKeyup = (event) => {
  props.searchKeyword(event);
}





const travelPlan = mapStore.travelList;


let draggedIndex = null; // 드래그 중인 항목의 인덱스





const handleDragStart = (index, event) => {
  draggedIndex = index;
  event.dataTransfer.effectAllowed = 'move';
  event.dataTransfer.setData('text/plain', index); // 드래그 데이터 설정
};

const handleDrop = (targetIndex) => {
  const itemToMove = travelPlan.splice(draggedIndex, 1)[0];
  travelPlan.splice(targetIndex, 0, itemToMove);
};

const removeFromTravelPlan = (index) => {
  travelPlan.splice(index, 1);
};


</script>


<style lang="scss" scoped>
@import "src/assets/scss/map/mapAppleThema";

.window{
  top: 0px;
  left: 0px;
  width: 100%;
  max-width: 350px;
  max-height: 100%;
  height: 100%;
}


</style>

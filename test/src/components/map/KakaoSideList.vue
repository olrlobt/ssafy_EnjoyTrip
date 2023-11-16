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
        <div :class="[active(2), 'tab-content']" >


        </div>
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

function beforeCloseTab(e) {
  e.stopPropagation();
}

function addTab() {
  tabs.push({
    id: nextTabId.value,
    name: "new tab"
  });
  currentTabId.value = nextTabId.value;
  nextTabId.value++;
}

function closeTab(id) {
  const index = tabs.findIndex(tab => tab.id === id);
  if (index !== -1) {
    tabs.splice(index, 1);
    if (currentTabId.value === id) {
      currentTabId.value = tabs.length > 0
          ? tabs[index] ? tabs[index].id : tabs[index - 1].id
          : null;
    }
  }
}

const props = defineProps({
  searchKeyword: Function
});

const searchKeyup = (event) => {
  props.searchKeyword(event);
}

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

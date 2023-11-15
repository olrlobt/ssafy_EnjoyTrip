<script setup>
import {ref} from 'vue';

const activeTab = ref('route');

function setActiveTab(tab) {
  activeTab.value = tab;
}
</script>

<template>
  <div class="container-map map-popup">

    <div class="tabs">
      <button @click="setActiveTab('route')">여행 경로</button>
      <button @click="setActiveTab('list')">관광지 리스트</button>
    </div>

    <div class="">
      <!--  여행 경로 탭  -->
      <div class="tab-content" v-if="activeTab === 'route'">
        <div class="sidebar ">
          <div>
            <h5>여행 경로</h5>
          </div>
          <ul id="travelRouteList">
            <!-- 여기에 장소가 추가됩니다 -->
          </ul>

          <div class="button-group">
            <button id="shortRoute_start" class="beautiful-button">시작지 최단경로 보기</button>
            <button id="shortRoute_return" class="beautiful-button">순회 최단경로 보기</button>
            <button id="shortRoute" class="beautiful-button">여행 최단경로 보기</button>
          </div>

          <button id="createRoute" class="route-button">경로 생성</button>
        </div>
      </div>

      <!--  관광지 리스트  -->
      <div class="tab-content" v-if="activeTab === 'list'">
        <div class="sidebar">
          <!-- 옵션 탭 내용 -->
        </div>
      </div>


    </div>


  </div>
</template>

<style scoped>



.tabs {
  font-size: 0;
  width: 600px;
  border: 1px solid #ddd;
}

.tabs button {
  display: inline-block;
  height: 46px;
  text-align: center;
  border-right: 1px solid #ddd;
}

.tabs button:before {
  content: "";
  position: absolute;
  left: 0;
  top: 0px;
  width: 100%;
  height: 3px;
}

.tabs button.active:before {
  background: #7ea21e;
}

.tabs button.active {
  border-bottom: 1px solid #fff;
}

.tabs button {
  position: relative;
  display: block;
  background: #f8f8f8;
  color: #000;
  padding: 0 30px;
  line-height: 46px;
  text-decoration: none;
  font-size: 16px;
}

.tabs button:hover,
.tabs button.active {
  background: #fff;
  color: #7ea21e;
}

.tabcontent {
  padding: 20px;
  height: 244px;
  border: 1px solid #ddd;
  border-top: none;
}
</style>

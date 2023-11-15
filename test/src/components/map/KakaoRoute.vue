<script setup>

</script>

<template>
  <div class="container-map ">
    <div class="sidebar map-popup">
      <h3>여행 경로</h3>
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
</template>

<style scoped>

</style>

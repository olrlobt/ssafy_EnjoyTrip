<script setup>
import KakaoSideList from "@/components/map/KakaoSideList.vue";

const props = defineProps({
  searchKeyword: Function,
  hideSideList: Function
});


</script>

<template>
  <div class="slide-list-box">
    <KakaoSideList :searchKeyword="props.searchKeyword" />
    <div class="slide-handle"  @click="hideSideList"></div>

  </div>
</template>

<style>

.slide-list-box{
  position: fixed;
  z-index: 100;
  top: 0px;
  left: 0px;
  width: 100%;
  max-width: 400px;
  max-height: 100%;
  height: 100%;
  transform: translateX(-100%);
}


.slide-handle {
  position: absolute;
  right: -20px;
  top: 50%;
  transform: translateY(-50%);
  width: 20px;
  height: 100px;
  background-color: #e8eaee;
  border-radius: 0 10px 10px 0px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.5);
  cursor: pointer;
}

.slide-handle::before {
  content: '';
  display: block;
  width: 0;
  height: 0;
  border-top: 10px solid transparent;
  border-bottom: 10px solid transparent;
  border-left: 10px solid #b6b7bb;
  position: absolute;
  left: 50%;
  top: 50%;

  transform: translate(-50%, -50%);
}



</style>

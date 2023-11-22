<script setup>


let props = defineProps(['item', 'click', 'removeClick']);

const clickList = (item) => {

  props.click(item);

}

</script>
<template>

  <div
      class="list-group-item bg-gray-300 m-1 p-3 rounded-md text-center travel-item"
      v-for="(item,index) in props.item"
      :key="item.coord.id"
      @click="clickList(item)"
  >
    <img :src="item.coord.firstimage" class="travel-item-image" alt="">
    <span class="travel-item-title">{{ item.coord.title }}</span>
    <div v-if="props.removeClick" class="close-button2" @click="props.removeClick(index)"></div>
  </div>

</template>
<style lang="scss" scoped>
@import "../../assets/scss/map/mapTravelList";

</style>
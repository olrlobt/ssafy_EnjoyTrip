<script setup>

import TheHero from "@/components/TheHero.vue";
import {ref} from "vue";

const title = ref("To6 여행경로");
const contents = ref("인기 여행지의 매력에 빠져보세요: 여행자들이 가장 사랑하는 Top 6 경로를 여기서 만나보세요.");
const changeHero = (title1, contents2) => {
  title.value = title1;
  contents.value = contents2;
}




</script>
<template>

  <div>
    <TheHero :title="title" :contents="contents"/>
    <router-view :changeHero="changeHero" :key="$route.path" ></router-view>
  </div>

</template>
<style>


</style>
<script setup>

import TheHero from "@/components/TheHero.vue";
import {ref} from "vue";

const title = ref("게시판");
const contents = ref("");
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
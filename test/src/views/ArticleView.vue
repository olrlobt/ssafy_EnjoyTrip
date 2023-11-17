<script setup>
import TheHero from "@/components/TheHero.vue"
import { ref } from "vue";
const title = ref("게시판");
const contents = ref("");

const changeHero = (title1, contents2) => {
  title.value = title1;
  contents.value = contents2;
}

</script>

<template>
  <TheHero :title="title" :contents="contents"/>
  <div class="container text-center mt-3">
    <router-view :changeHero="changeHero" :key="$route.path"></router-view>
  </div>
</template> 

<style>
mark.sky {
  background: linear-gradient(to top, #54fff9 20%, transparent 30%);
}
</style>

<!--   <TheHeroVue title="Write" contents ="쓰기 창입니다." />
 -->
<script setup>
import TheHero from "@/components/TheHero.vue"

</script>

<template>
  <TheHero/>
  <div class="container text-center mt-3">
    <div class="alert alert-info" role="alert">여행정보게시판 - 추후</div>
    <router-view :key="$route.path"></router-view>
  </div>
</template> 

<style>
mark.sky {
  background: linear-gradient(to top, #54fff9 20%, transparent 30%);
}
</style>

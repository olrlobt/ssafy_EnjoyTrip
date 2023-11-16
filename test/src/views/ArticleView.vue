<script setup>
import TheHero from "@/components/TheHero.vue"

</script>

<template>
  <TheHero title="게시판" contents ="게시판입니다."/>
  <div class="container text-center mt-3">
    <router-view :key="$route.path"></router-view>
  </div>
</template> 

<style>
mark.sky {
  background: linear-gradient(to top, #54fff9 20%, transparent 30%);
}
</style>

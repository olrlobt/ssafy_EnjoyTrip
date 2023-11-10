<script setup>
import { RouterLink, RouterView } from 'vue-router'
import TheFooterVue from "./components/layout/TheFooter.vue";
import TheNavbar from "./components/layout/TheNavbar.vue";
</script>

<template>
<div>
    <TheNavbar/>
    <TheFooterVue/>
    <RouterView />
</div>
</template>

<style scoped>

</style>

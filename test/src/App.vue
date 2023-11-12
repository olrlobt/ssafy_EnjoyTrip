<script setup>
import {RouterView} from 'vue-router'
import TheNavbar from "./components/layout/TheNavbar.vue";</script>

<template>
<div>
    <TheNavbar/>
    <!-- <TheFooterVue/> -->
    <RouterView />
</div>
</template>

<style scoped>

</style>

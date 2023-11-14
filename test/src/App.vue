

<script setup>
import Navbar from './components/Navbar.vue';
import Footer2 from './components/Footer.vue';

import './assets/js/jquery-3.4.1.min.js';
import './assets/js/bootstrap.min.js';
import './assets/js/jquery.animateNumber.min.js';
import './assets/js/jquery.waypoints.min.js';
import './assets/js/jquery.fancybox.min.js';


import {RouterView} from 'vue-router'

</script>

<template>
  <div id="app">
    <Navbar />
    <RouterView/>
    <Footer2 />
  </div>
</template>

<style>
/* 전역 스타일 */


</style>



<script>
import Navbar from './components/Navbar.vue';
import HeroSection from './components/HeroSection.vue';
import ServicesSection from './components/ServicesSection.vue';
import Footer2 from './components/Footer.vue';

import './assets/js/jquery-3.4.1.min.js';
// import './assets/js/popper.min';
import './assets/js/bootstrap.min.js';
import './assets/js/owl.carousel.min.js';
import './assets/js/jquery.animateNumber.min.js';
import './assets/js/jquery.waypoints.min.js';
import './assets/js/jquery.fancybox.min.js';


export default {
  name: 'App',
  components: {
    Navbar,
    HeroSection,
    ServicesSection,
    // eslint-disable-next-line vue/no-unused-components
    Footer2
  }
};
</script>

<template>
  <div id="app">
    <Navbar />
    <HeroSection />
    <ServicesSection />
    <Footer2 />
  </div>
</template>

<style>
/* 전역 스타일 */


</style>

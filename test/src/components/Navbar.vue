<script setup>
import { useMenuStore } from "@/stores/menu";
import { storeToRefs } from "pinia";

const menuStore = useMenuStore();

// 반응형을 유지하면서 스토어에서 속성을 추출하려면, storeToRefs()를 사용
// https://pinia.vuejs.kr/core-concepts/
const { menuList } = storeToRefs(menuStore);
const { changeMenuState } = menuStore;

const logout = () => {
  console.log("로그아웃!!!!");
  changeMenuState();
};

</script>


<template>
  <nav class="site-nav">
    <div class="container">
      <div class="site-navigation">
        <RouterLink to="/"><img src="@/assets/images/logo2.png" width="85"></RouterLink>
        <ul class="js-clone-nav d-none d-lg-inline-block text-left site-menu float-right">
          <li class="active"><RouterLink to="/">Home</RouterLink></li>

          <li><RouterLink to="/map">Map</RouterLink></li>

          <li class="has-children">
            <RouterLink to="#">Share UX</RouterLink>
            <ul class="dropdown">
              <li><RouterLink to="#">My Route</RouterLink></li>
              <li><RouterLink to="#">My Likes</RouterLink></li>
            </ul>
          </li>

          <li class="has-children menu-item">
            <RouterLink to="/article/notice">CS</RouterLink>
            <ul class="dropdown">
              <li><RouterLink to="/article/notice">Notice</RouterLink></li>
              <li><RouterLink to="/article/qna">Q&A</RouterLink></li>
            </ul>
          </li>

          <li><RouterLink to="/user/login">Login / Sign up</RouterLink></li>
<!-- 
        <li>
          <template v-for="menu in menuList" :key="menu.routeName">
            <template v-if="menu.show">
              <template v-if="menu.routeName === 'logout'">
                <li class="nav-item">
                  <router-link to="/" @click.prevent="logout" class="nav-link">{{
                    menu.name
                  }}</router-link>
                </li>
              </template>
              <template v-else>
                <li class="nav-item">
                  <router-link :to="{ name: menu.routeName }" class="nav-link">{{
                    menu.name
                  }}</router-link>
                </li>
              </template>
            </template>
          </template>
        </li> -->



          <!--          <li class="has-children">-->
<!--            <a href="#">Share UX</a>-->
<!--            <ul class="dropdown">-->
<!--              <li><a href="elements.html">Elements</a></li>-->
<!--              <li><a href="#">My route</a></li>-->
<!--              <li class="has-children">-->
<!--                <a href="#">Menu Two</a>-->
<!--                <ul class="dropdown">-->
<!--                  <li><a href="#">Sub Menu One</a></li>-->
<!--                  <li><a href="#">Sub Menu Two</a></li>-->
<!--                  <li><a href="#">Sub Menu Three</a></li>-->
<!--                </ul>-->
<!--              </li>-->
<!--              <li><a href="#">Menu Three</a></li>-->
<!--            </ul>-->
<!--          </li>-->



        </ul>
        <a href="#" class="burger ml-auto float-right site-menu-toggle js-menu-toggle d-inline-block d-lg-none light" data-toggle="collapse" data-target="#main-navbar">
          <span></span>
        </a>
      </div>
    </div>
  </nav>
</template>



<style scoped>
/* 네비게이션 바에 대한 CSS 스타일 */

.menu-item:not(:last-child):after {
  content: "|"; /* 구분자 */
  color: #cce0dc; /* 구분자 색상 */
  margin-left: 15px; /* 왼쪽 여백 */
}

</style>

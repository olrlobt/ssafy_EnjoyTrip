<script setup>
import {useMemberStore} from "@/stores/member";
const memberStore = useMemberStore();

function logout() {
     sessionStorage.removeItem('accessToken');
     sessionStorage.removeItem('refreshToken');
     window.location.reload();
}


</script>


<template>
<!--  <div>{{ memberStore.isLogin }}</div>-->
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
              <li><RouterLink to="/share/travelRoute">UX Travel</RouterLink></li>
<!--              <li><RouterLink to="#">My Likes</RouterLink></li>-->
            </ul>
          </li>

          <li class="has-children menu-item">
            <RouterLink to="/article/notice">CS</RouterLink>
            <ul class="dropdown">
              <li><RouterLink to="/article/notice">Notice</RouterLink></li>
              <li><RouterLink to="/article/qna">Q&A</RouterLink></li>
            </ul>
          </li>
          <li class="has-children menu-item2"  v-if="memberStore.isLogin">
            <RouterLink to="">MyPage</RouterLink>
            <ul class="dropdown">
              <li><RouterLink to="/user/modify">User Info</RouterLink></li>
              <li><RouterLink :to="`/${memberStore.userInfo.userId}/travelRoute`">My Travel</RouterLink></li>
              <!-- <li><RouterLink to="/user/like">나만의 핫플레이스</RouterLink></li> -->
            </ul>
          </li>

          <!-- <li><RouterLink to="/user/login">Login / Sign up</RouterLink></li> -->
          <li>
          <router-link to="/user/login" v-if="!memberStore.isLogin">Login</router-link>


          
          <!-- <router-link to="/user/mypage" v-if="isLoggedIn">My Page</router-link> -->
          
        </li>
          <li v-if="memberStore.isLogin" @click="logout" style="color: #cce0dc; margin-left: 15px; cursor: pointer">Logout</li>


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

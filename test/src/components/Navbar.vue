<script>
import { useRouter } from 'vue-router'

const router = useRouter();

export default {
  computed: {
    isLoggedIn() {
      const token = sessionStorage.getItem('accessToken')
      return token !== null && token !== undefined
    }
  },
  methods: {
    logout() {
      // 로그아웃 로직을 구현
      // sessionStorage에서 토큰을 삭제하거나 만료시키는 등의 작업이 필요
      sessionStorage.removeItem('accessToken')
      sessionStorage.removeItem('refreshToken')
      // 로그아웃 후 홈페이지로 리다이렉트 등의 추가 작업이 필요
      router.push('/')
    }
  }
}

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

          <!-- <li><RouterLink to="/user/login">Login / Sign up</RouterLink></li> -->
          <li>
          <router-link to="/user/login" v-if="!isLoggedIn">Login</router-link>
          <router-link to="/logout" v-if="isLoggedIn" @click="logout">Logout</router-link>
          <router-link to="/user/mypage" v-if="isLoggedIn">My Page</router-link>
        </li>



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

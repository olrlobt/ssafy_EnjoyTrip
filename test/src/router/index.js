import { createRouter, createWebHistory } from 'vue-router'
import HomeView from "../views/HomeView.vue"
import MapView from "../views/MapView.vue";

const router = createRouter({

  history: createWebHistory(import.meta.env.BASE_URL),

  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/user/login',
      name: 'login',
      component: () => import("@/components/user/TheLogin.vue"),
    },
    {
      path: '/join',
      name: 'join',
      component: () => import("@/components/user/TheJoin.vue"),
    },
    {
      path: '/map',
      name: 'map',
      component: MapView,
      
    },
    {
      path: '/user/modify',
      name: 'userInfo',
      meta: { requiresAuth: true },
      component: () => import("@/components/user/TheUserInfo.vue"),
      
    },
    {
      path: '/article/:boardType',
      name: 'article',
      component: () => import("@/views/ArticleView.vue"),
      meta: { requiresAuth: true } ,
      props: true,
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "view/:articleno",
          name: "article-view",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "article-write",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        {
          path: "modify/:articleno",
          name: "article-modify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
        {
          path: "reply",
          name: "article-reply",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
      ],
    },
  ],
})

// beforeEach를 사용하여 라우터 네비게이션 가드 구현
router.beforeEach((to, from, next) => {
  // to.meta.requiresAuth가 true이고 sessionStorage에 토큰이 없다면 홈페이지로 리다이렉트
  if (to.meta.requiresAuth && !isLoggedIn()) {
    next('/');
  } else {
    // 그렇지 않으면 계속 진행
    next();
  }
})

// sessionStorage에서 토큰이 있는지 여부를 확인하여 로그인 상태를 반환
function isLoggedIn() {
  const token = sessionStorage.getItem('accessToken')
  
  // sessionStorage에 토큰이 있는지 여부에 따라 로그인 상태를 결정
  return token !== null && token !== undefined
}

// 홈 경로에서도 사용자가 로그인 상태인지 체크
router.beforeEach((to, from, next) => {
  // 사용자가 로그인 상태가 아니라면
  if (to.path === '/' && isLoggedIn()) {
    // 로그인 페이지로 리다이렉트
    next();
  } else {
    // 그렇지 않으면 계속 진행
    next();
  }
});


export default router

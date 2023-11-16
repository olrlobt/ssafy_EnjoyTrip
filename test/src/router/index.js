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
      // beforeEnter(to, from, next) { // index 접근시 localStorage에 토큰이 있는지 확인.
      //   if (localStorage.getItem('accessToken')) {
      //     next({ name: 'home' }) //메뉴가  마이페이지/로그아웃 만들게 해야함.
      //   } else { 
      //     next({ name :  'login'});
      //   }
      // }
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
      // path: '/user/mypage/:userId',
      path: '/user/mypage',
      name: 'mypage',
      component: () => import("@/components/user/TheMypage.vue"),
    },
    {
      path: '/map',
      name: 'map',
      component: MapView,
    },
    {
      path: '/article/:boardType',
      name: 'article',
      component: () => import("@/views/ArticleView.vue"),
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
  ]
})

export default router

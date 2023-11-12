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
      path: '/login',
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

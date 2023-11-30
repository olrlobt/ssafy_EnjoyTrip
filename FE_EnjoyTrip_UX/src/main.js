import './assets/css/owl.carousel.min.css';
import './assets/css/bootstrap.min.css';
import './assets/css/owl.theme.default.min.css';
import './assets/css/jquery.fancybox.min.css';
import './assets/fonts/icomoon/style.css';
import './assets/fonts/flaticon/font/flaticon.css';
// import './assets/css/daterangepicker.css';

import './assets/css/style.css';


import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia';

const app = createApp(App);
const pinia = createPinia();
app.use(pinia);
app.use(router);
app.mount('#app');

// // main.js
// import { createApp } from 'vue';
// import App from './App.vue';
// import { createPinia } from 'pinia';
// import store from './stores/member'; // 이곳에서 정의한 store를 import
//
// const app = createApp(App);
// const pinia = createPinia();
//
// app.use(pinia);
// app.use(store);
//
// // 세션에서 토큰을 로드하고, getUserInfo 액션을 통해 상태를 초기화
// const loadUserInfoFromSession = async () => {
//     const accessToken = sessionStorage.getItem('accessToken');
//     if (accessToken) {
//         await store.dispatch('getUserInfo', accessToken);
//     }
// };
//
// loadUserInfoFromSession().then(() => {
//     app.mount('#app');
// });


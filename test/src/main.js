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



const app = createApp(App)
const pinia = createPinia();

app.use(pinia);

app.use(router)

app.mount('#app')

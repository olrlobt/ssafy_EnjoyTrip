import './assets/css/style.css'
import './assets/css/animate.css'
import './assets/css/aos.css'
import './assets/css/app.css'
import './assets/css/gijgo.min.css'
import './assets/css/index.css'
import './assets/css/magnific-popup.css'
import './assets/css/map.css'
import './assets/css/nice-select.css'
import './assets/css/owl.carousel.min.css'
import './assets/css/slick.css'
import './assets/css/swiper.min.css'
import './assets/css/bootstrap.min.css'
import './assets/css/font-awesome.min.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(router)

app.mount('#app')

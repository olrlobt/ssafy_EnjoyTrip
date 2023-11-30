<link rel="stylesheet" href="../../assets/css/share/shareTravelList.css">
<script setup>
import ShareTravelRouteCard from "@/components/share/ShareTravelRouteCard.vue";
import {getSharedTravelRoute, getTravelRoute} from "@/api/share"
import {onMounted, ref} from "vue";

const travelRouteList = ref([]);
let props = defineProps(['userId','changeHero']);

onMounted(async () => {

  if(props.userId){
    props.changeHero("My Route ","당신의 발자취: 당신의 모든 여행 경로를 한곳에 기록하고 소중히 간직하세요")

    await getTravelRoute(props.userId, (response) => {
      for (let idx = 0; idx < response.data.length; idx++) {
        travelRouteList.value.push(response.data[idx]);
      }
    }, (error) => {
      console.error(error);
    });
  }else{
    props.changeHero("UX Route ","아름다운 발자취: 회원들이 공유한 여행 경로를 탐색하고 영감을 받아보세요.")

    await getSharedTravelRoute( (response) => {
      for (let idx = 0; idx < response.data.length; idx++) {
        travelRouteList.value.push(response.data[idx]);
      }
    }, (error) => {
      console.error(error);
    });
  }

  console.log(travelRouteList.value)
})



</script>
<template>

  <section class="py-5">
    <div class="container px-4 px-lg-5 mt-5">
      <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">

        <template v-for="travelRoute in travelRouteList" :key="travelRoute.travelRouteNo">
          <ShareTravelRouteCard :travelRoute="travelRoute" :userId="props.userId">
          </ShareTravelRouteCard>
        </template>


      </div>
    </div>
  </section>

</template>
<style>
@import "../../assets/css/share/shareTravelList.css";

</style>
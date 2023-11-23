<link rel="stylesheet" href="../../assets/css/share/shareTravelList.css">
<script setup>
import ShareTravelRouteCard from "@/components/share/ShareTravelRouteCard.vue";
import {getTravelRoute} from "@/api/share"
import {onMounted, ref} from "vue";

const travelRouteList = ref([]);
let props = defineProps(['userId']);

onMounted(async () => {

  const travelRouteDto = {
    userId: props.userId
  }
  console.log(travelRouteDto)
  console.log(props.userId)
  await getTravelRoute(props.userId, (response) => {
    for (let idx = 0; idx < response.data.length; idx++) {
      travelRouteList.value.push(response.data[idx]);
    }
  }, (error) => {
    console.error(error);
  });
})



</script>
<template>

  <section class="py-5">
    <div class="container px-4 px-lg-5 mt-5">
      <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">

        <template v-for="travelRoute in travelRouteList" :key="travelRoute.travelRouteNo">
          <ShareTravelRouteCard :travelRoute="travelRoute">
          </ShareTravelRouteCard>
        </template>


      </div>
    </div>
  </section>

</template>
<style>
@import "../../assets/css/share/shareTravelList.css";

</style>
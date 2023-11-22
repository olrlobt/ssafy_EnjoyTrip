<script setup>
import ShareTravelRouteCardMap from "@/components/share/ShareTravelRouteCardMap.vue";
import { useRouter } from "vue-router";
import {useShareStore} from "@/stores/share";
const props = defineProps(['travelRoute']);

const router = useRouter();
const shareStore = useShareStore();


const showMyTravelDetail = () => {
  router.push({
    name: "travel-detail",
    params: { travelRouteNo: props.travelRoute.travelRouteNo }
  });
  shareStore.travelRoute = props.travelRoute;
}

</script>
<template>

  <div class="col mb-5">
    <div class="card h-100">
      <!-- Product image-->
      <ShareTravelRouteCardMap :markers="props.travelRoute.markers"
                               style="width: 100% ; height: 100%; min-height: 300px "></ShareTravelRouteCardMap>
      <div class="card-body p-4">
        <div class="text-center">
          <h5 class="fw-bolder">{{ props.travelRoute.subject }}</h5>
          {{ props.travelRoute.registerTime }}
        </div>
      </div>
      <!-- Product actions-->
      <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
        <button class="text-center" @click="showMyTravelDetail">자세히 보기</button>
      </div>
    </div>
  </div>

</template>
<style>


</style>
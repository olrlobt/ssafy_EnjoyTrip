<script setup>
import ShareTravelRouteCardMap from "@/components/share/ShareTravelRouteCardMap.vue";
import { useRouter } from "vue-router";
import {useShareStore} from "@/stores/share";
import {updateHit} from "@/api/share";
const props = defineProps(['travelRoute', 'userId']);
const router = useRouter();
const shareStore = useShareStore();

// const showMyTravelDetail = () => {
//   //hit 올리기
//
//   router.push({
//     path : "detail",
//     params: { travelRouteNo: props.travelRoute.travelRouteNo , userId: props.userId }
//   });
//   shareStore.travelRoute = props.travelRoute;
// }
const showMyTravelDetail = async() => {
  //hit 올리기
  try {
    // hit 올리기
    await updateHit(props.travelRoute.travelRouteNo);
    router.push({
      path: "detail",
      params: { travelRouteNo: props.travelRoute.travelRouteNo, userId: props.userId }
    });
    shareStore.travelRoute = props.travelRoute;
  } catch (error) {
    console.error('Hit를 올리고 상세 페이지로 이동하는 중 에러 발생:', error);
  }
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
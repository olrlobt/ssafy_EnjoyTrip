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
    <div class="card h-100" @click="showMyTravelDetail">
      <!-- Product image-->
      <div class="card-img-top">
        <ShareTravelRouteCardMap
            :markers="props.travelRoute.markers"
            style="width: 100%; height: 100%; min-height: 300px;"
        ></ShareTravelRouteCardMap>
      </div>
      <!-- Card Body -->
      <div class="card-body">
        <div class="text-center">
          <h5 class="card-title">{{ props.travelRoute.subject }}</h5>
          <p class="card-text">{{ props.travelRoute.registerTime }}</p>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
.card {
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  transition: all 0.3s ease-in-out;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0,0,0,0.2);
}

.card-img-top {
  overflow: hidden;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.card-title {
  font-size: 1.2em;
  font-weight: bold;
}

.card-text {
  font-size: 0.9em;
  color: #666;
}</style>
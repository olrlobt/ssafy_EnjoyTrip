<template>

  <div class="custom-card" @click="showMyTravelDetail">
    <ShareTravelRouteCardMap
        :markers="props.travelRoute.markers"
        style="width: 100%; height: 80%; min-height: 300px;"
    ></ShareTravelRouteCardMap>
    <div class="custom-card-body">
      <span class="flaticon-house display-4 text-primary"></span>
      <h3>{{ props.travelRoute.subject }}</h3>
      <div class="additional-info">
        <p>작성자: {{ props.travelRoute.userId }}</p>
        <p>조회수: {{ props.travelRoute.hit }}</p>
      </div>
      <!-- 추가적인 내용이 있다면 여기에 추가 -->
    </div>
  </div>

</template>

<script setup>
import { useRouter } from "vue-router";
import ShareTravelRouteCardMap from "@/components/share/ShareTravelRouteCardMap.vue";
import {updateHit} from "@/api/share";
import {useShareStore} from "@/stores/share";
import {onMounted} from "vue";
const router = useRouter();
const props = defineProps(['travelRoute']);
console.log(props.travelRoute)
const shareStore = useShareStore();
const getCardLink = () => {
  router.push({ name: "share-travelRoute" });
}


const showMyTravelDetail = async() => {
  //hit 올리기
  try {
    // hit 올리기\
    const userId = null;
    await updateHit(props.travelRoute.travelRouteNo);
    router.push({
      name: "share-travel-detail",
      params: { travelRouteNo: props.travelRoute.travelRouteNo, userId: userId }
    });
    shareStore.travelRoute = props.travelRoute;
  } catch (error) {
    console.error('Hit를 올리고 상세 페이지로 이동하는 중 에러 발생:', error);
  }
}



</script>

<style scoped>
.custom-card {
  width: 100%;
  max-width: 500px; /* 원하는 최대 가로 폭 지정 */
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  margin-bottom: 20px;
}

.custom-card-body {
  padding: 20px;
  text-align: center;
  white-space: nowrap; /* 텍스트 줄바꿈 방지 */
  overflow: hidden;
  text-overflow: ellipsis; /* 텍스트가 넘칠 경우 생략 부호(...) 표시 */
}

.custom-card-body h3 {
  margin-top: 10px;
}

.custom-card-body p {
  margin-bottom: 10px;
}
</style>
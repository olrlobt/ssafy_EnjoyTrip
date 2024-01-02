<script setup>
import KakaoListItem from "@/components/map/KakaoListItem.vue"
import ShareTravelRouteCardMap from "@/components/share/ShareTravelRouteCardMap.vue";
import {onMounted, ref} from "vue";
import {useShareStore} from "@/stores/share";
import {deleteTravelRoute, shareTravelRoute} from "@/api/share";
import router from "@/router";
import Swal from "sweetalert2";
import {useMapStore} from "@/stores/map";
import CommentList from "@/components/comment/CommentList.vue";

const shareStore = useShareStore();
const url = ref('sharedTravelRouteComments');


let travelList;
if(shareStore.travelRoute.markers){
  travelList = shareStore.travelRoute.markers.map(item => {
    return { coord: item };
  });
}

const isMount = ref(false)
onMounted(() => {
  isMount.value = true;

  console.log( shareStore.travelRoute )

});

const props = defineProps(['userId', '인기 여행지의 매력에 빠져보세요: 여행자들이 가장 사랑하는 Top 6 경로를 여기서 만나보세요.']);



const deleteTravelRouteBtn = () => {
  deleteTravelRoute(shareStore.travelRoute.travelRouteNo,
      ()=>{
        router.back();
        }),
      (error)=>{
        console.error(error);
      }
}

const shareTravelRouteBtn = () => {

  Swal.fire({
    title: "공유하기",
    html: `
     <label for="input1"> 글 제목 </label>
    <input type="text" id="input1" class="swal2-input" placeholder="글 제목" value="${ shareStore.travelRoute.subject }">
    <label for="input1"> 글 내용 </label>
    <input type="text" id="input2" class="swal2-input" placeholder="글 내용" style="min-height: 300px">
  `,
    showCancelButton: true,
    cancelButtonText : "취소",
    confirmButtonText: "저장",
    showLoaderOnConfirm: true,
    preConfirm: () => {
      const input1Value = document.getElementById('input1').value;
      const input2Value = document.getElementById('input2').value;

      shareStore.travelRoute.subject = input1Value;
      shareStore.travelRoute.content = input2Value;
      shareTravelRoute(shareStore.travelRoute,
          ()=>{
            console.log(" 공유 성공 ")
          }),
          (error)=>{
            console.error(error);
          }
    },
    allowOutsideClick: () => !Swal.isLoading()
  }).then((result) => {
    if (result.isConfirmed) {
      Swal.fire({
        title: `저장 완료`,
        icon: "success",
        timer: 700,
      });
    }
  });
}


const bringTravelRouteBtn = () => {
  useMapStore().coord = shareStore.travelRoute.markers;
  router.push({name : "map"});

  // shareStore.travelRoute

}

</script>
<template>
  <div class="container">
    <!-- 제목 카드 -->
    <div class="header">
      <h1 class="post-title">{{ shareStore.travelRoute.subject }}</h1>
      <span class="author">작성자 : {{ shareStore.travelRoute.userId }}</span>
    </div>

    <!-- 지도와 여행 리스트 컨테이너 -->
    <div class="map-list-container">
      <div class="map">
        <ShareTravelRouteCardMap
            v-if="isMount"
            :markers="shareStore.travelRoute.markers"
            class="w-100 h-100"
            style="min-height: 500px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);"
        />
      </div>
      <div class="travel-list">
        <KakaoListItem v-if="isMount" :item="travelList" style="margin-top: 20px;"></KakaoListItem>
      </div>
    </div>

    <!-- 게시글 내용 카드 -->
    <div class="card" v-if="!props.userId" style="margin-top: 20px;">
      <div class="card-body">
        <p class="card-text">{{ shareStore.travelRoute.content }}</p>
      </div>
    </div>

    <!-- 버튼 그룹 -->
    <div class="card-footer" style="margin-top: 20px;">
      <button v-if="props.userId" @click="shareTravelRouteBtn" class="btn btn-primary">공유하기</button>
      <button @click="bringTravelRouteBtn" class="btn btn-secondary">가져오기</button>
      <button @click="deleteTravelRouteBtn" class="btn btn-danger">삭제하기</button>
    </div>



    <!-- Comments List -->
    <div>

      <CommentList :url="url" :no="shareStore.travelRoute.sharedTravelRouteNo"  />
      <!-- Replace with actual comments loop -->
    </div>

  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  max-width: 1000px;
  margin: auto;
  padding: 20px;
  background-color: #ffffff; /* 흰색 배경 */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 부드러운 그림자 효과 */
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.post-title {
  font-size: 24px;
  color: #333; /* 진한 회색 */
}

.author {
  font-size: 14px;
  color: #555; /* 중간 회색 */
}

.map-list-container {
  display: flex;
  gap: 20px;
}

.map, .travel-list {
  flex: 1;
  background: #f8f9fa; /* 매우 밝은 회색 */
  padding: 15px;
  border-radius: 8px;
}

.card {
  background: #f8f9fa;
  margin-top: 20px;
  border-radius: 8px;
}

.card-body {
  padding: 20px;
}

.card-text {
  font-size: 16px;
  color: #333;
}

.card-footer {
  display: flex;
  justify-content: flex-end;
  padding: 10px 20px;
  background: #f8f9fa;
}

.btn {
  background-color: #007bff; /* 버튼 기본 색상 */
  color: white;
  border: none;
  border-radius: 4px;
  padding: 10px 20px;
  cursor: pointer;
  margin-left: 10px;
}

.btn:hover {
  background-color: #0056b3; /* 호버 시 버튼 색상 변경 */
}

.btn-secondary {
  background-color: #6c757d;
}

.btn-secondary:hover {
  background-color: #5a6268;
}

.btn-danger {
  background-color: #dc3545;
}

.btn-danger:hover {
  background-color: #c82333;
}

</style>

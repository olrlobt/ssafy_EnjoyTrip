<script setup>
import KakaoListItem from "@/components/map/KakaoListItem.vue"
import ShareTravelRouteCardMap from "@/components/share/ShareTravelRouteCardMap.vue";
import {onMounted, ref} from "vue";
import {useShareStore} from "@/stores/share";
import {deleteTravelRoute, shareTravelRoute} from "@/api/share";
import router from "@/router";
import Swal from "sweetalert2";
import {useMapStore} from "@/stores/map";

const shareStore = useShareStore();

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

const props = defineProps(['userId']);
console.log(props.userId)


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
    <div class="card">
      <div class="card-title">{{ shareStore.travelRoute.subject }}</div>
    </div>

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

    <div class="board">
      <div class="card" v-if="!props.userId" style="margin-top: 20px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
        <p class="card-text"><small class="text-muted">작성자: {{ shareStore.travelRoute.userId }}</small></p>
        <p class="card-text">{{ shareStore.travelRoute.content }}</p>
      </div>
    </div>

    <div class="buttons" style="margin-top: 20px;">
      <button v-if="props.userId" @click="shareTravelRouteBtn" class="btn btn-primary">공유하기</button>
      <button @click="bringTravelRouteBtn" class="btn btn-secondary">가져오기</button>
      <button @click="deleteTravelRouteBtn" class="btn btn-danger">삭제하기</button>
    </div>
  </div>
</template>
<style scoped>
.container {
  display: flex;
  max-width: 1000px !important;
  flex-direction: column;
  padding: 20px;
}

.map-list-container {
  display: flex; /* Flex 컨테이너로 설정 */
  flex-grow: 1;
}

.map {
  flex: 1.5; /* 두 컴포넌트가 동일한 비율로 공간을 차지하도록 설정 */
}

.travel-list {
  flex: 1; /* 두 컴포넌트가 동일한 비율로 공간을 차지하도록 설정 */
}

.card {
  background: white;
  padding: 15px;
  border-radius: 5px;
  margin-bottom: 20px;
  padding : 30px;
}

.card-title {
  font-size: 24px;
  font-weight: bold;
  padding-left: 30px;
  padding-right: 30px;
}

.card-text {


}

.btn {
  margin-right: 10px;
  padding: 10px 20px;
}
</style>

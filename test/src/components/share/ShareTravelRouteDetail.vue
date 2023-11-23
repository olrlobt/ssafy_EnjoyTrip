<script setup>
import KakaoListItem from "@/components/map/KakaoListItem.vue"
import ShareTravelRouteCardMap from "@/components/share/ShareTravelRouteCardMap.vue";
import {onMounted, ref} from "vue";
import {useShareStore} from "@/stores/share";
import {deleteTravelRoute, shareTravelRoute} from "@/api/share";
import router from "@/router";
import Swal from "sweetalert2";

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
  console.log("가져오기")
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
            style="min-height: 500px;"
        />
      </div>
      <div class="travel-list">
        <KakaoListItem v-if="isMount" :item="travelList"></KakaoListItem>
      </div>
    </div>
    <div class="board">
      <div class="card" v-if="!props.userId">
        <p class="card-text" >{{ shareStore.travelRoute.content }}</p>
        <p class="card-text"><small class="text-muted">작성자:{{ shareStore.travelRoute.userId }}</small></p>
      </div>
    </div>
    <div class="buttons">
      <button v-if="props.userId" @click="shareTravelRouteBtn">공유하기</button>
      <button v-if="props.userId" @click="bringTravelRouteBtn">가져오기</button>
      <button v-if="props.userId" @click="deleteTravelRouteBtn">삭제하기</button>
    </div>
  </div>

</template>

<style>

body {
  font-family: Arial, sans-serif;
}

.container {
  width: 80%;
  margin: auto;
  padding: 20px;
}

.map-list-container {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.map, .travel-list {
  border: 1px solid #ddd;
  padding: 10px;
}

.map {
  width: 60%;
  min-height: 500px;
}

.travel-list {
  width: 35%;
}

.board .card {
  border: 1px solid #ddd;
  padding: 10px;
  margin-bottom: 20px;
}

.buttons {
  text-align: center;
}

button {
  margin: 5px;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}


</style>
<script setup>
import KakaoListItem from "@/components/map/KakaoListItem.vue"
import ShareTravelRouteCardMap from "@/components/share/ShareTravelRouteCardMap.vue";
import {onMounted, ref} from "vue";
import {useShareStore} from "@/stores/share";
import {deleteTravelRoute, shareTravelRoute} from "@/api/share";
import router from "@/router";

const shareStore = useShareStore();
console.log(shareStore.travelRoute)

let travelList;
if(shareStore.travelRoute.markers){
  travelList = shareStore.travelRoute.markers.map(item => {
    return { coord: item };
  });
}

const isMount = ref(false)
onMounted(() => {
  isMount.value = true;
});


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
  shareTravelRoute(shareStore.travelRoute,
      ()=>{
        console.log(" 공유 성공 ")
      }),
      (error)=>{
        console.error(error);
      }
}


</script>
<template>

  <div class="container">
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
      <div class="card">
        <h5 class="card-title">{{ shareStore.travelRoute.subject }}</h5>
        <p class="card-text">내용...</p>
        <p class="card-text"><small class="text-muted">작성자: 사용자명</small></p>
      </div>
    </div>
    <div class="buttons">
      <button @click="shareTravelRouteBtn">공유하기</button>
      <button>수정하기</button>
      <button @click="deleteTravelRouteBtn">삭제하기</button>
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
<script setup>
import KakaoListItem from "@/components/map/KakaoListItem.vue"
import ShareTravelRouteCardMap from "@/components/share/ShareTravelRouteCardMap.vue";
import {onMounted, ref} from "vue";
import {useShareStore} from "@/stores/share";

const shareStore = useShareStore();
console.log(shareStore.travelRoute)
const travelList = shareStore.travelRoute.markers.map(item => {
  return { coord: item };
});


const isMount = ref(false)
onMounted(() => {
  isMount.value = true;
});

console.log(travelList)

</script>
<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col-md-6">
        <ShareTravelRouteCardMap
            v-if="isMount"
            :markers="shareStore.travelRoute.markers"
            class="w-100 h-100"
            style="min-height: 500px;"
        />
      </div>



      <div class="col-md-6">
        <div class="card">
          <div class="card-body">

            <h5 class="card-title">제목</h5>
            <p class="card-text">내용...</p>
            <p class="card-text"><small class="text-muted">작성자: 사용자명</small></p>

          </div>

        </div>

          <KakaoListItem v-if="isMount" :item="travelList"></KakaoListItem>

      </div>
    </div>
  </div>
</template>

<style>


</style>
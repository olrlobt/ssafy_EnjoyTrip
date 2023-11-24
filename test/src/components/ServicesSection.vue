<template>
  <div class="untree_co-section" style="margin-top: 250px">
    <div class="container">
      <div class="row mb-5 justify-content-center">
        <div class="col-lg-6 text-center">
          <h2 class="section-title text-center mb-3" style="font-weight: bold">인기 있는 여행지 경로</h2>
          <p>현재 사용자들의 마음을 사로잡은 TOP6 여행 경로입니다.</p>
        </div>
      </div>

          <div class="row">
            <!-- 동적으로 최대 3개의 카드 표시 -->
            <div class="col-4" v-for="(travelRoute, index) in top6TravelRoutes.slice(0, Math.min(top6TravelRoutes.length, 3))" :key="index">
              <ShareTravelTopSix :travelRoute="travelRoute" />
            </div>
          </div>
          <div class="row mt-4">
            <!-- 나머지 동적으로 최대 3개의 카드 표시 -->
            <div class="col-4" v-for="(travelRoute, index) in top6TravelRoutes.slice(3, Math.min(top6TravelRoutes.length, 6))" :key="index">
              <ShareTravelTopSix :travelRoute="travelRoute" />
            </div>
          </div>
        </div>
      </div>

</template>

<script>
import { ref, onMounted } from 'vue';
import { listTop6TravelRoute } from "@/api/share";
import ShareTravelTopSix from "@/components/share/ShareTravelTopSix.vue";
import ShareTravelRouteCardMap from "@/components/share/ShareTravelRouteCardMap.vue";

export default {
  name: 'ServicesSection',
  components: {ShareTravelRouteCardMap, ShareTravelTopSix },
  setup() {
    const dataIsLoaded = ref(false);
    const top6TravelRoutes = ref([]);

    onMounted(async () => {
      try {
        const data = await listTop6TravelRoute();
        console.log(data);
        top6TravelRoutes.value = data;
        dataIsLoaded.value = true;
      } catch (error) {
        console.error('Top 6 여행 경로를 가져오는 중 에러 발생:', error);
      }

    });

    return {
      top6TravelRoutes,
      dataIsLoaded
    };
  },
};
</script>

<style scoped>
/* 서비스 섹션에 대한 CSS 스타일 */
</style>

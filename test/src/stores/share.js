import { ref } from "vue";
import { defineStore } from "pinia";
import { listTop6TravelRoute } from '@/api/share';


export const useShareStore = defineStore("shareStore", () => {

    const travelRoute = ref({});
    const top6TravelRoutes = ref([]);

    const getTop6TravelRoutes = async () => {
        try {
            const data = await listTop6TravelRoute();
            console.log(data);
            top6TravelRoutes.value = data;
        } catch (error) {
            console.error('Top 6 여행 경로를 가져오는 중 에러 발생:', error);
        }
    };

    return {
        travelRoute,
        top6TravelRoutes,
        getTop6TravelRoutes,

    };
});

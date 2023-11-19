import { ref } from "vue";
import { defineStore } from "pinia";

export const useMapStore = defineStore("mapStore", () => {

    const currentSelectMarker = ref({
        marker: null,
        coord : {}
    }); // marker
    const travelList = ref([]); // coord
    const fixedMarkers = ref([]); // marker
    const infoWindow = ref(null);
    const currentSideList = ref([]); // coord
    const markers = ref([]); // marker, coord

    const updateTravelList = (travelPlan) => {
        travelList.value = travelPlan;
    }

    const removeIndexOfTravelList = (targetMarker) => {
        const index = travelList.value.findIndex(item => item.coord.title === targetMarker.getTitle());

        if (index !== -1) {
            travelList.value.splice(index, 1);
        }
    };



    return {
        currentSelectMarker,
        travelList,
        fixedMarkers,
        currentSideList,
        markers,
        updateTravelList,
        removeIndexOfTravelList
    };
});

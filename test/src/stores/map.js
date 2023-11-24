import { ref } from "vue";
import { defineStore } from "pinia";

export const useMapStore = defineStore("mapStore", () => {

    const currentSelectMarker = ref({
        marker: null,
        coord : {}
    }); // marker
    const travelList = ref([]); // marker, coord
    const fixedMarkers = ref([]); // marker
    const infoWindow = ref(null); // infoWindow
    const markers = ref([]); // marker, coord
    const openMarkerInfowindow = ref(null);
    const map = ref(null); // map
    const coord = ref([]);

    const searchFromHome = ref({type: null , keyword: null});

    const updateTravelList = (travelPlan) => {
        travelList.value = travelPlan;
    }

    const updateInfoWindow = (newInfoWindow) => {
        infoWindow.value = newInfoWindow;
    }

    const removeIndexOfTravelList = (targetMarker) => {
        const index = travelList.value.findIndex(item => item.coord.title === targetMarker.getTitle());

        if (index !== -1) {
            travelList.value.splice(index, 1);
        }
    };

    const updateOpenMarkerInfowindow = (newOpenMarkerInfowindow) => {
        openMarkerInfowindow.value = newOpenMarkerInfowindow;
    }

    const updateMap = (newMap) => {
        map.value = newMap;
    }

    return {
        map,
        currentSelectMarker,
        travelList,
        fixedMarkers,
        markers,
        infoWindow,
        openMarkerInfowindow,
        coord,
        searchFromHome,
        updateOpenMarkerInfowindow,
        updateTravelList,
        removeIndexOfTravelList,
        updateInfoWindow,
        updateMap
    };
});

import { ref } from "vue";
import { defineStore } from "pinia";


export const useShareStore = defineStore("shareStore", () => {

    const travelRoute = ref({});


    return {
        travelRoute
    };
});

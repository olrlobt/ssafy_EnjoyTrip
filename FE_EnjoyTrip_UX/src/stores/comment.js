import { ref } from "vue";
import { defineStore } from "pinia";


export const useCommentStore = defineStore("shareStore", () => {

    const comments = ref([]);

    return {
        comments
    };
});

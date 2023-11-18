<template>
  <div class="window">
    <div class="head">
      <div class="buttons">
        <div class="button -red"></div>
        <div class="button -yellow"></div>
        <div class="button -green"></div>
      </div>
      <div class="tabs-group">

        <div class="tabs"  >
          <div
              :class="[active(1), 'tab']"
          >
            <span class="text"> 세부사항</span>
            <div
                class="close-button"
            ></div>
          </div>
        </div>

        <div key="add-button" class="minus-button" @click="props.hideMarkerPopup"></div>
      </div>
    </div>
    <div class="menu">
      <div class="buttons">
        <div class="arrows">
          <div class="arrow -back"></div>
          <div class="arrow -forward"></div>
        </div>
      </div>
      <input class="search" type="text" />
    </div>

    <div class="content" style=" padding: 20px;">
      <div style="display: flex; align-items: flex-start; justify-content: center; padding-bottom: 20px;">
        <img
            :src="mapStore.currentSelectMarker.firstimage"
            :alt="mapStore.currentSelectMarker.title"
            style="width: 150px; height: 150px; object-fit: cover; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);"
        />
        <div style="margin-left: 20px; max-width: 60%; ">
          <strong style="font-size: 1.2rem; color: #333;">{{ mapStore.currentSelectMarker.title }}</strong><br/>
          <span style="font-size: 0.9rem; color: #666;">{{ mapStore.currentSelectMarker.addr1 }}</span>
        </div>
      </div>

      <div style="height: 100%; background-color: #9fcdff; width: 100%; height: 50%; object-fit: cover; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);">

      </div>

    </div>


  </div>
</template>
<script setup>
import { ref,  onMounted,  } from 'vue';
import {useMapStore} from "@/stores/map";

const mapStore = useMapStore();
const currentTabId = ref(1);

onMounted(() => {

})

const active = (id) => id === currentTabId.value ? "-active" : "";

const props = defineProps({
  hideMarkerPopup: Function
})


</script>


<style lang="scss" scoped>
@import "src/assets/scss/map/mapAppleThema";

.window{
  width: 100%;
  height: 100%;
}

</style>

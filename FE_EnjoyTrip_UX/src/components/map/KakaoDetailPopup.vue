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
      <input class="search" type="text" :value="mapStore.currentSelectMarker.coord.title " />
    </div>

    <div class="content" style=" padding: 20px; ">
      <div style="display: flex; align-items: flex-start; justify-content: center; padding-bottom: 20px;">
        <img
            :src="mapStore.currentSelectMarker.coord.firstimage"
            :alt="mapStore.currentSelectMarker.coord.title"
            style="width: 250px; height: 250px; object-fit: cover; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);"
        />
        <div style="margin-left: 20px; max-width: 60%; ">
          <span style="font-size: 1.1rem; color: #333;">주소지 :</span><br/>
          <span style="font-size: 0.9rem; color: #666;">{{ mapStore.currentSelectMarker.coord.addr1 }}</span>
          <br/>
          <br/> <!-- 한칸 띄우긴 했는데 .. to. 승헌씨,,-->
          <img class="icon" src="@/assets/images/chatgpt-icon.png" alt="Icon">
          <span style="font-size: 1.1rem; color: #333;">상세내용 :</span><br/>
          <span style="font-size: 0.9rem; color: #666;">{{ summary }}</span>
        </div>
      </div>


    </div>


  </div>
</template>
<script setup>
import {onMounted, ref} from 'vue';
import {useMapStore} from "@/stores/map";
import {getContentWithOpenAI} from "@/api/chat";

const mapStore = useMapStore();
const currentTabId = ref(1);
 //Gpt 요약본
const summary = ref("ChatGpt 가 요약한 내용입니다. ");
const title = ref(mapStore.currentSelectMarker.coord.title);
const getChatGptResponse = () => {
  getContentWithOpenAI(title.value, ({data}) =>{
    console.log(data); //반환 데이터
    summary.value = data;
  })
}


const active = (id) => id === currentTabId.value ? "-active" : "";

const props = defineProps({
  hideMarkerPopup: Function
})

// onMounted(()=>{
//   getChatGptResponse();
// });


</script>


<style lang="scss" scoped>


.window {
  position: relative;
  width: 30vw;
  max-width: 600px;
  min-width: 25vw;
  min-height: 25vw;
  max-height: 500px;
  z-index: 100;
  overflow: hidden;
}

.search {
  height: 40px !important;
  font-size: 1.2rem !important;
  font-weight: bold;
}

.icon {
  margin-right: 6px;
  width:25px;
  height: 25px;
}

@import "../../assets/scss/map/mapAppleThema";
</style>

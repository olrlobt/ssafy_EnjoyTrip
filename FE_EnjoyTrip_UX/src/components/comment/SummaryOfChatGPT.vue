<template>
  <div class="review-card">
    <div class="card-header">
      <img class="icon" src="@/assets/images/chatgpt-icon.png" alt="Icon">
      <h6>ChatGPT가 요약한 최근 리뷰 내용입니다.</h6>
    </div>
    <div class="card-content">
      <!-- 여기에 ChatGPT가 요약한 최근 리뷰 내용을 보여주는 내용을 적습니다. -->
      {{ summary }}
    </div>
  </div>
</template>

<script setup>
import {defineProps, onMounted, ref} from 'vue';
import {chatWithOpenAI, chatWithOpenAIByShare} from "@/api/chat";
const props = defineProps([
  'no','itemKey'
]);

console.log(props.itemKey);

const boardNo = ref(props.no);
// const icon = '@/assets/images/chatgpt-icon.png'; // ChatGPT 아이콘 이미지 URL을 업데이트하세요.
const summary = ref('ChatGPT가 최근 리뷰를 요약한 내용이 여기에 나타납니다.');

const getChatGptResponse = () => {
  // API 호출
  chatWithOpenAI(boardNo.value, ({ data }) => {
        console.log(data);
        summary.value = data;
      },
      (error) => {
        console.log(error)
      });
};

const getShareGptResponse = () => {
  // API 호출
  chatWithOpenAIByShare(boardNo.value, ({ data }) => {
        console.log(data);
        summary.value = data;
      },
      (error) => {
        console.log(error)
      });
};

onMounted(()=>{
  if(props.itemKey === 'comments'){
    getChatGptResponse();
  }else if(props.itemKey === 'share'){
    getShareGptResponse();
  }
});
</script>

<style scoped>
.review-card {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 16px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  align-items: center;
  margin-bottom: 12px;
}

.icon {
  margin-right: 8px;
  width:30px;
  height: 30px;
}

.card-content {
  font-size: 16px;
  color: #555;
}
</style>

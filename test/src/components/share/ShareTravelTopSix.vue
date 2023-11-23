<template>
  <div class="custom-card">
    <div class="custom-card-body">
      <span class="flaticon-house display-4 text-primary"></span>
      <h3>{{ props.travelRoute.subject }}</h3>
      <div class="additional-info">
        <p>User ID: {{ props.travelRoute.userId }}</p>
        <p>Hit: {{ props.travelRoute.hit }}</p>
      </div>
      <!-- 추가적인 내용이 있다면 여기에 추가 -->
    </div>
  </div>
</template>

<script setup>
const props = defineProps(['travelRoute']);
// console.log(props.travelRoute)
</script>

<style scoped>
.custom-card {
  width: 100%;
  max-width: 500px; /* 원하는 최대 가로 폭 지정 */
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  margin-bottom: 20px;
}

.custom-card-body {
  padding: 20px;
  text-align: center;
  white-space: nowrap; /* 텍스트 줄바꿈 방지 */
  overflow: hidden;
  text-overflow: ellipsis; /* 텍스트가 넘칠 경우 생략 부호(...) 표시 */
}

.custom-card-body h3 {
  margin-top: 10px;
}

.custom-card-body p {
  margin-bottom: 10px;
}
</style>
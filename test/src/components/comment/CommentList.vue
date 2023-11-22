<template>
  <div v-for="comment in comments" :key="comment.id" class="comment-item">
  <div class="comment-card">
    <div class="comment-header">{{ maskUserId(comment.userId) }}</div>
    <div class="comment-content">
      {{ comment.content }}
    </div>
  </div>
  </div>

</template>

<script setup>
defineProps(['comments']);
const maskUserId = (userId) => {
  // 앞의 두 글자를 남기고 나머지 부분을 *로 바꾸어 반환
  return userId ? userId.slice(0, 2) + '*'.repeat(userId.length - 2) : '';
};


</script>

<style scoped>
.comment-item {
  margin-bottom: 10px;
}

.comment-card {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 16px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.comment-header {
  font-size: 14px;
  font-weight: bold;
  color: #333;
}

.comment-content {
  margin-top: 8px;
  font-size: 16px;
  color: #555;
}
</style>

<template>
  <div>
    <div v-for="comment in comments" :key="comment.id" class="comment-item">
      <strong>{{ comment.username }}</strong> {{ comment.content }}
    </div>
  </div>
</template>

<script setup>
// import { ref, defineProps } from 'vue';
defineProps(['comments']);

</script>

<style scoped>
.comment-item {
  margin-bottom: 10px;
}
</style>

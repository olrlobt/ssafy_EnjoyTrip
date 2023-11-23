<script setup>
defineProps({ article: Object });
</script>

<template>
<!--  {{article}}-->
  <tr :style="{ marginLeft: `${article.depth * 20}px` }" class="text-center" >
    <th scope="row">{{ article.articleNo }}</th>
    <td class="text-start">
      <router-link
        :to="{ name: 'article-view', params: { articleno: article.articleNo } }"
        class="article-title link-dark"
      >
        {{ article.subject }}
      </router-link>
    </td>
    <td>{{ article.userId }}</td>
    <td>{{ article.hit }}</td>
    <td>{{ article.registerTime }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
/* 추가한 부분 */
.text-center {
  transition: background-color 0.3s;
  cursor: pointer;
}

.text-center:hover {
  background-color: #f2f2f2;
}
</style>

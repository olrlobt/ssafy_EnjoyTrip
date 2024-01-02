<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";

import { onMounted} from "vue";

const props = defineProps([
  'changeHero'
])

onMounted(() => {
  props.changeHero("Modify","수정화면입니다.")
 
});

</script>

<template>
  <div class="container">
    <div class="modify-container">
      <div class="text-start">
        <BoardFormItem type="modify" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  max-width: 800px;
  position: relative;
}

.modify-container{
  max-width: 800px; /* 컨테이너의 최대 너비 증가 */
  margin: auto;
  padding: 30px; /* 내부 패딩 증가 */
  border: 1px solid #ddd;
  box-shadow: 0 2px 5px rgba(0,0,0,0.2);
  border-radius: 10px;
}


</style>

<script setup>
// import BoardFormItem from "./item/BoardFormItem.vue";
import BoardFormItem from "./item/BoardFormItem.vue";
import {onMounted} from "vue";

const props = defineProps([
  'changeHero',
  'boardType'
])


onMounted(() => {
  props.changeHero("글쓰기","🔅글쓰는 공간입니다 욕 또는 비속어는 삼가해주세요🔅")
});

</script>

<template>
  <div class="mb-3">
    <div class="row justify-content-center">
      <div class="col-lg-10 text-start">
        <BoardFormItem type="regist" refNo="refNo" :board-type="props.boardType"/>
      </div>
    </div>
  </div>
</template>

<style scoped></style>

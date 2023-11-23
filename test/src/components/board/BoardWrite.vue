<script setup>
// import BoardFormItem from "./item/BoardFormItem.vue";
import BoardFormItem from "./item/BoardFormItem2.vue";
import {onMounted, ref} from "vue";

const props = defineProps([
  'changeHero',
  'boardType'
])

const board = ref("");

onMounted(() => {
  props.changeHero("쓰기","방가방가")
  board.value = props.boardType;
  console.log(board.value)
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

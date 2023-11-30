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
      <div class="col-lg-10 text-start">
        <BoardFormItem type="modify" />
      </div>
  </div>
</template>

<style scoped></style>

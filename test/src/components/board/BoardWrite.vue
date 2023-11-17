<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
import { onMounted} from "vue";

const props = defineProps([
  'changeHero'
])

onMounted(() => {
  props.changeHero("쓰기","방가방가")
 
});

</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10 text-start">
        <BoardFormItem type="regist" refNo="refNo"/>
      </div>
    </div>
  </div>
</template>

<style scoped></style>

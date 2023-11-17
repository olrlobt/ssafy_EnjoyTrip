<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
import TheHeroVue from "../TheHero.vue";

</script>

<template>
  <!-- <TheHeroVue title="Modify" contents ="수정하는 창입니다." /> -->
  <div class="container">
      <div class="col-lg-10 text-start">
        <BoardFormItem type="modify" />
      </div>
  </div>
</template>

<style scoped></style>

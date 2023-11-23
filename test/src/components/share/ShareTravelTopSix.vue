<template>
  <div v-for="(share) in shareStore.share" :key="share.id" class="share-item">
    <div class="col-6 col-sm-6 col-lg-4 feature-1-wrap d-md-flex flex-md-column order-lg-1">
      <div class="feature-1 d-md-flex">
        <div class="align-self-center">
          <span class="flaticon-house display-4 text-primary"></span>
          <h3>Beautiful Condo</h3>
          <p class="mb-0">Even the all-powerful Pointing has no control about the blind texts.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {storeToRefs} from "pinia";
import {useShareStore} from "@/stores/share";
const { shareInfo } = storeToRefs(useShareStore);


</script>

<style scoped>
/* 서비스 섹션에 대한 CSS 스타일 */
</style>
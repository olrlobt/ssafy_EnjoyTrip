<script setup>
import TheHeroVue from "../TheHero.vue";
import { useRoute, useRouter } from "vue-router";
const router = useRouter();


const join = () => {
  router.push({ name: "join" });
}


</script>

<template>
  <TheHeroVue/>
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <form id="form-login" method="POST" action="">
          	<input type="hidden" name="action" value="login">
            <div class="mb-3">
              <label for="userid" class="form-label">아이디 : </label>
              <input
                type="text"
                class="form-control"
                id="userid"
                name="userid"
                placeholder="아이디..."
                value="${saveid}"
              />
            </div>
            <div class="mb-3">
              <label for="userpwd" class="form-label">비밀번호 : </label>
              <input
                type="password"
                class="form-control"
                id="userpwd"
                name="userpwd"
                placeholder="비밀번호..."
              />
            </div>

            <div class="form-check mb-3 float-end">
              <input
                class="form-check-input"
                type="checkbox"
                value="ok"
                id="saveid"
                name="saveid"
              />
              <label class="form-check-label" for="saveid"> 아이디저장 </label>
            </div>


            <div class="col-auto text-center">
              <button type="button" @click="login" id="btn-login" class="btn btn-outline-primary mb-3">로그인</button>
              <button type="button" @click="join" id="btn-mv-join" class="btn btn-outline-success mb-3">회원가입</button>
            </div>
          </form>
        </div>
      </div>
    </div>
</template>

<style scoped>

</style>
<script setup>

import TheHeroVue from "../TheHero.vue";
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
// import { useMenuStore } from "@/stores/menu";

const router = useRouter();
const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;
// const { changeMenuState } = useMenuStore();

const loginUser = ref({
  userId: "",
  userPwd: "",
});


const login = async () => {
  console.log("login ing!!!! !!!");
  console.log("loginUser: " + loginUser.value.userId);
  try {
    // userLogin 메서드가 Promise를 반환하도록 가정합니다.
    await userLogin(loginUser.value);

    let token = sessionStorage.getItem("accessToken");
    console.log("111. ", token);
    console.log("isLogin: ", isLogin);

    if (isLogin.value) {
      console.log("로그인 성공아닌가???");
      console.log("token" + token);
      getUserInfo(token);
      
      router.push("/").then(()=>{
        // 페이지 새로 고침
        window.location.reload();
      });

      console.log("가나욤")
    }
  } catch (error) {
    console.error("로그인 중 에러 발생:", error);
  }
};

const join = () => {
  router.push({ name: "join" });
}




</script>

<template>
  <TheHeroVue title="Login" contents ="로그인 창입니다." />
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
                v-model="loginUser.userId"
                id="userid"
                name="userid"
                placeholder="아이디..."
              />
            </div>
            <div class="mb-3">
              <label for="userpwd" class="form-label">비밀번호 : </label>
              <input
                type="password"
                class="form-control"
                v-model="loginUser.userPwd"
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

<!-- 함수를 하위 컴포넌트로 제공 -->
<!-- <provide :checkLoggedIn="checkLoggedIn" /> -->

<style scoped>

</style>
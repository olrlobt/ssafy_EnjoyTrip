<script setup>

import TheHeroVue from "../TheHero.vue";
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
// import { useMenuStore } from "@/stores/menu";

const router = useRouter();
const memberStore = useMemberStore();

const { isLogin, userInfo } = storeToRefs(memberStore);
// const { changeMenuState } = useMenuStore();

const loginUser = ref({
  userId: "",
  userPwd: "",
});
const login = async () => {

  try {
    // userLogin 메서드가 Promise를 반환하도록 가정합니다.
    await memberStore.userLogin(loginUser.value);

    let token = sessionStorage.getItem("accessToken");
    if (isLogin.value) {

      // console.log("token" + token);
      console.log("userInfo:" + userInfo.value.userId);

      await memberStore.getUserInfo(token);
      await router.push("/");
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
  <TheHeroVue title="Login" contents ="당신의 이야기가 시작되는 곳, 우리와 함께해주세요." />
    <div class="container" style="margin-bottom: 100px;">
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <div class="login-container">
            <form id="form-login" method="POST" action="">
              <input type="hidden" name="action" value="login">
              <div class="form-field">
                <label for="userid" class="form-label">아이디 :</label>
                <input type="text" class="form-control" v-model="loginUser.userId" id="userid" name="userid" placeholder="아이디..." />
              </div>
              <div class="form-field">
                <label for="userpwd" class="form-label">비밀번호 :</label>
                <input type="password" class="form-control" v-model="loginUser.userPwd" id="userpwd" name="userpwd" placeholder="비밀번호..." />
              </div>
              <div class="form-check form-field">
                <input class="form-check-input" type="checkbox" value="ok" id="saveid" name="saveid" />
                <label class="form-check-label" for="saveid">아이디 저장</label>
              </div>
              <div class="form-buttons">
                <button type="button" @click="login" id="btn-login" class="btn btn-primary">로그인</button>
                <button type="button" @click="join" id="btn-mv-join" class="btn btn-secondary">회원가입</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
</template>

<!-- 함수를 하위 컴포넌트로 제공 -->
<!-- <provide :checkLoggedIn="checkLoggedIn" /> -->

<style scoped>
.login-container {
  max-width: 500px; /* 컨테이너의 최대 너비 증가 */
  margin: auto;
  padding: 30px; /* 내부 패딩 증가 */
  border: 1px solid #ddd;
  box-shadow: 0 2px 5px rgba(0,0,0,0.2);
  border-radius: 10px;
}

.form-field {
  margin-bottom: 20px; /* 각 필드의 여백 증가 */
}

.form-control, .btn {
  font-size: 1.1em; /* 폰트 크기 증가 */
  border-radius: 5px;
}

.btn {
  padding: 10px 20px; /* 버튼의 패딩 증가 */
  margin-right: 15px; /* 버튼 사이의 간격 증가 */
  transition: all 0.3s ease;
}

.btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0,0,0,0.2);
}

.form-buttons {
  text-align: center;
}
</style>
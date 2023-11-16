<script setup>
import TheHeroVue from "../TheHero.vue";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { joinUser } from "@/api/user.js";

const router = useRouter();
// const props = defineProps({ type: String });

const memberInfo = ref({
  userId: "",
  userName: "",
  userPwd: "",
  emailId: "",
  emailDomain: "",
});


function join() { 
  console.log("회원가입 클릭")
  joinUser(memberInfo.value,
    (response) => { 
      let msg = "글등록 처리시 문제 발생했습니다.";
      if (response.status == 201) msg = "회원가입 성공"
      alert(msg);
      router.push({ name: "login" });
    },
    (error) => console.error(error));
}



</script>

<template>
    <TheHeroVue title="Join" contents ="회원가입 창입니다."/>
      <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <form @submit.prevent="onSubmit">
            <div class="mb-3">
              <label for="username" class="form-label">이름 : </label>
              <input
                type="text"
                class="form-control"
                v-model="memberInfo.userName"
                id="username"
                name="userName"
                placeholder="이름..."
              />
            </div>
            <div class="mb-3">
              <label for="userid" class="form-label">아이디 : </label>
              <input
                type="text"
                class="form-control"
                v-model="memberInfo.userId"
                id="userid"
                name="userId"
                placeholder="아이디..."
              />
            </div>
            <div id="result-view" class="mb-3"></div>
            <div class="mb-3">
              <label for="userpwd" class="form-label">비밀번호 : </label>
              <input
                type="text"
                class="form-control"
                v-model="memberInfo.userPwd"
                id="userpwd"
                name="userPwd"
                placeholder="비밀번호..."
              />
            </div>
            <div class="mb-3">
              <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
              <input type="text" class="form-control" id="pwdcheck" placeholder="비밀번호확인..." />
            </div>
            <div class="mb-3">
              <label for="emailid" class="form-label">이메일 : </label>
              <div class="input-group">
                <input
                  type="text"
                  class="form-control"
                  v-model="memberInfo.emailId"
                  id="emailid"
                  name="emailId"
                  placeholder="이메일아이디"
                />
                <span class="input-group-text">@</span>
                <select
                  class="form-select"
                  v-model="memberInfo.emailDomain"
                  id="emaildomain"
                  name="emailDomain"
                  aria-label="이메일 도메인 선택"
                >
                  <option selected>선택</option>
                  <option value="ssafy.com">ssafy.com</option>
                  <option value="google.com">google.com</option>
                  <option value="naver.com">naver.com</option>
                  <option value="kakao.com">kakao.com</option>
                </select>
              </div>
            </div>
    
            <div class="col-auto text-center">
              <button type="button" @click = join() id="btn-join" class="btn btn-outline-primary mb-3" >
                회원가입
              </button>
              <button type="button" @click = reset() class="btn btn-outline-success mb-3" >초기화</button>
            </div>
          </form>
        </div>
      </div>
    </div>
</template>

<style scoped>

</style>
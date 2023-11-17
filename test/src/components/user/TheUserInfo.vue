<script setup>
import TheHeroVue from "../TheHero.vue";
import { ref, onMounted} from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { modifyUser } from "@/api/user.js";

const router = useRouter();
const memberStore = useMemberStore();

const token = sessionStorage.getItem('accessToken');

const memberInfo = ref({
  userId: "",
  userName: "",
  userPwd: "",
  emailId: "",
  emailDomain: "",
});

onMounted(() => getMemberInfo());
const getMemberInfo = async() => {
  try {
    await memberStore.getUserInfo(token);
    const userInfo =  memberStore.userInfo;
    console.log(userInfo);

    if (userInfo) {
      memberInfo.value = userInfo;
      console.log('MyPage User Info: ', userInfo);
    }
  }  catch (error) {
    console.error("마이페이지 중 에러 발생:", error);
  }
};

function modify() { 
  console.log("수정하기 클릭");
  modifyUser(memberInfo.value,
  (response) => {
    let msg = "수정 중에 문제 발생";
      // console.log(msg);
      if (response.status == 200) {
        msg = "수정 성공";
        console.log(msg);
        sessionStorage.removeItem('accessToken');
        sessionStorage.removeItem('refreshToken');
        router.push('/').then(()=>{
          window.location.reload();
      });
      }
    },
    (error) => console.error(error));
  }
  

</script>

<template>
  <TheHeroVue title="User Info" contents ="회원정보를 수정할 수 있습니다."/>
  <div class="container">
        <!-- {{ userInfo.userName }} -->
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <form @submit.prevent="onSubmit">
            <div class="mb-3">
              <label for="username" class="form-label">이름 : </label>
              <input
                type="text"
                class="form-control"
                id="username"
                name="userName"
                placeholder="이름..."
                v-model="memberInfo.userName"
              />
            </div>
            <div class="mb-3">
              <label for="userid" class="form-label">아이디 : </label>
              <input
                type="text"
                class="form-control"
                id="userid"
                name="userId"
                placeholder="아이디..."
                v-model="memberInfo.userId"
              />
            </div>
            <div id="result-view" class="mb-3"></div>
            <div class="mb-3">
              <label for="userpwd" class="form-label">비밀번호 : </label>
              <input
                type="text"
                class="form-control"
                id="userpwd"
                name="userPwd"
                placeholder="비밀번호..."
                v-model="memberInfo.userPwd"
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
                  id="emailid"
                  name="emailId"
                  placeholder="이메일아이디"
                  v-model="memberInfo.emailId"

                />
                <span class="input-group-text">@</span>
                <select
                  class="form-select"
                  id="emaildomain"
                  name="emailDomain"
                  aria-label="이메일 도메인 선택"
                v-model="memberInfo.emailDomain"

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
              <button type="button" @click = modify() id="btn-modify" class="btn btn-outline-primary mb-3" >
                수정하기
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
</template>

<style scoped>

</style>
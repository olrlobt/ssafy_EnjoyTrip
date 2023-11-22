<script setup>
import Viewer from "@toast-ui/editor/dist/toastui-editor-viewer";

import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/board";
import {useMemberStore} from "@/stores/member";

const memberStore = useMemberStore();

const route = useRoute();
const router = useRouter(); 

const { articleno } = route.params;

const article = ref({});

const props = defineProps([
  'changeHero'
]);

const token = sessionStorage.getItem('accessToken');

const memberInfo = ref({
  userId: "",
  userName: "",
  userPwd: "",
  emailId: "",
  emailDomain: "",
});

const getMemberInfo = async () => {
  try {
    await memberStore.getUserInfo(token);
    const userInfo = memberStore.userInfo;
    console.log(userInfo);

    if (userInfo) {
      memberInfo.value = userInfo;
      article.value.userId = memberInfo.value.userId;
      console.log('MyPage User Info: ', userInfo);
    }
  } catch (error) {
    console.error("마이페이지 중 에러 발생:", error);
  }
};

const content = ref('');
const viewer = ref(null);
const viewerValid = ref(null);

onMounted(async () => {
  getMemberInfo();
  try {
    const { data } = await new Promise((resolve, reject) => {
      detailArticle(
          articleno,
          (response) => resolve(response),
          (error) => reject(error)
      );
    });

    console.log(data);

    // 데이터 처리
    article.value = data;
    content.value = data.content;
    console.log("Data content: ", data.content);
    console.log("content: ", content.value);

    console.log("article content: " + content.value);

    // 프로퍼티 변경
    props.changeHero("Detail", "디테일화면입니다.");

    // Viewer 생성
    viewerValid.value = new Viewer({
      el: viewer.value,
      height: "600px",
      minHeight:"600px",
      initialEditType: "wysiwyg",
      initialValue: content.value,
    });
  } catch (error) {
    console.error(error);
  }
});


function reply() {
  router.push({ name: "article-reply", query: { articleno: articleno, refNo: article.value.ref, step: article.value.step, depth: article.value.depth } });
}

function moveList() {
  router.push({ name: "article-list" });
}

function moveModify() {
  // 사용자 확인.
  if(article.value.userId === memberInfo.value.userId){
    router.push({ name: "article-modify", query: { articleno, ...article.value } });
  }
}

function onDeleteArticle() {
  // const { articleno } = route.params;
  console.log(articleno + "번글 삭제하러 가자!!!");
  // API 호출
  deleteArticle(articleno, ({ data }) => {
    console.log(data)
    article.value = data;
    moveList();
  },
    (error) => {
      console.log(error)
    });

}


</script>


<template>
  <div class="container mt-5">
    <!-- User Info and Date -->
    <div class="dropdown-container">
      <div class="dropdown">
        <!-- 이미지를 드롭다운 버튼으로 사용 -->
        <div class="dropbtn"><img src="@/assets/images/edit_icon.png" alt="드롭다운 아이콘"></div>
        <div class="dropdown-content">
          <a href="#" @click="moveModify">글 수정</a>
          <a href="#" @click="onDeleteArticle">글 삭제</a>
        </div>
      </div>
    </div>
    <div class="mb-3">
      <h1 style="font-weight: bold">{{ article.subject }}</h1>
      <p class="lead">
        <strong>{{ article.userId }}</strong> | {{ new Date(article.registerTime).toLocaleDateString() }}
      </p>

      <hr/>
    </div>

    <!-- Article Content -->
    <div class="mb-3">
      <div style="min-height: 600px;">
        <div ref="viewer"></div>
      </div>
      <hr/>
    </div>

    <!-- Comment Input -->
    <div class="mb-3">
      <input type="text" class="form-control" placeholder="댓글을 입력하세요">
      <button type="button" class="btn btn-primary mt-2" @click="reply">답글달기</button>
      <button type="button" class="btn btn-primary mt-2" @click="moveList">글목록</button>
      <!--      <button class="btn btn-primary mt-2">댓글 입력</button>-->
    </div>

    <!-- Comments List -->
    <div>
      <p>...댓글 리스트</p>
      <!-- Replace with actual comments loop -->
    </div>
  </div>
</template>

<style scoped lang="scss">
.container {
  max-width: 800px;
  position: relative;
}
.dropdown-container {
  position: absolute;
  top: 0;
  right: 0;
  margin: 10px; /* Adjust margin as needed */
}
.dropdown {
  position: relative;
  display: inline-block;
  text-align: right;
}

/* 드롭다운 버튼 이미지 스타일 */
.dropbtn img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  cursor: pointer;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
  border-radius: 5px;
  overflow: hidden;
  right: 0;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  transition: background-color 0.3s ease;
}

.dropdown-content a:hover {
  background-color: #ddd;
}

.dropdown:hover .dropdown-content {
  display: block;
}

h1 {
  font-size: 2rem;
}
hr {
  border-top: 1px solid #ccc;
}

/* 이미지 스타일 */
//.dropdown-content img {
//  width: 1%;
//  height: auto;
//  border-radius: 5px;
//  margin-bottom: 10px;
//}

body {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background-color: #f4f4f4;
  margin: 0;
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}



</style>









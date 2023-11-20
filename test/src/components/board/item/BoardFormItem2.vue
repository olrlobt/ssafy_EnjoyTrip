<script setup>
import {onMounted, ref, watch} from 'vue';
import Editor from '@toast-ui/editor';
import '@toast-ui/editor/dist/toastui-editor.css';

import { useRoute, useRouter } from "vue-router";
import { registArticle, getModifyArticle, modifyArticle } from "@/api/board";

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });
const { boardType } = route.params;
const isUseId = ref(false);


const { articleno, refNo ,step, depth } = route.query;

const article = ref({
  articleNo: 0,
  subject: "",
  content: "",
  userId: "",
  userName: "",
  hit: 0,
  registerTime: "",
  ref: 0,
  step: 0,
  depth: 0
});


if (props.type === "modify") {
  let { articleno } = route.params;
  console.log(articleno + "번글 얻어와서 수정할거야");
  // API 호출
  getModifyArticle(articleno, ({ data }) => {
        console.log(data);
        article.value = data;
      },
      (error) => {
        console.log(error);
      });

  isUseId.value = true;
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
    () => article.value.subject,
    (value) => {
      let len = value.length;
      if (len == 0 || len > 30) {
        subjectErrMsg.value = "제목을 확인해 주세요!!!";
      } else subjectErrMsg.value = "";
    },
    { immediate: true }
);
watch(
    () => article.value.content,
    (value) => {
      let len = value.length;
      if (len == 0 || len > 500) {
        contentErrMsg.value = "내용을 확인해 주세요!!!";
      } else contentErrMsg.value = "";
    },
    { immediate: true }
);

function onSubmit() {
  // event.preventDefault();

  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  console.log("글등록하자!!", article.value);
  // API 호출

  article.value.ref = article.value.articleNo;
  if(articleno){
    console.log(" 답 글 인 듯 ")
    article.value.ref = refNo;
    article.value.step = parseInt(step);
    article.value.depth = parseInt(depth);
  }

  registArticle(boardType ,article.value, ({ data }) => {
        console.log("write data: "+ data)
        article.value = data;
        moveList();
      },
      (error) => {
        console.log(error)
      });
}

function updateArticle() {
  console.log(article.value.articleNo + "번글 수정하자!!", article.value);
  // API 호출
  modifyArticle(article.value, ({ data }) => {
        console.log(data);
        article.value = data;
      },
      (error) => {
        console.log(error);
      });
  moveDetail();
}

function moveList() {
  router.push({ name: "article-list" });
}
function moveDetail(){
  router.push({name: "article-view"});
}
const emit = defineEmits(['update:modelValue']);
const editor = ref();
const editorValid = ref();
const testHtml = ref();

//마운트될때 Editor 생성
onMounted(() => {
  editorValid.value = new Editor({
    el: editor.value,
    height: '500px',
    //'wysiwyg', 'markdown' 택 1
    initialEditType: 'wysiwyg',
    // initialEditType: 'markdown',
    previewStyle:'vertical',
    events: {
      change: () => emit('update:modelValue', editorValid.value.getMarkdown()),
    },

  });
});

//작성한 내용 불러와서 html 적용

// eslint-disable-next-line vue/no-export-in-script-setup
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <label for="userid" class="form-label">작성자 ID : </label>
      <input
          type="text"
          class="form-control"
          v-model="article.userId"
          :disabled="isUseId"
          placeholder="작성자ID..."
      />
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input type="text" class="form-control" v-model="article.subject" placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <div ref="editor" />
      <!--      <textarea class="form-control" v-model="article.content" rows="10"></textarea>-->
    </div>

    <!--ref 로 editor value 적용-->
<!-- 작성한 내용 불러와서 html 적용-->
<!--  <div v-html="Html"></div>-->
  <div class="board-form bnt">
  <div class="col-auto text-center">
    <button type="submit" class="btn btn-primary mt-2" v-if="type === 'regist'">
      글작성
    </button>
    <button type="submit" class="btn btn-primary mt-2" v-else>글수정</button>
    <button type="button" class="btn btn-primary mt-2" @click="moveList">
      목록으로이동...
    </button>
  </div>
  </div>
  </form>
</template>

<style scoped></style>

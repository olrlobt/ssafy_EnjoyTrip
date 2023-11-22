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
      <input type="text" class="form-control" v-model="article.subject" placeholder="제목..."/>
      <div v-if="subjectErrMsg" class="text-danger">{{ subjectErrMsg }}</div>
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <div ref="editor"/>
      <div v-if="contentErrMsg" class="text-danger">{{ contentErrMsg }}</div>
    </div>
    <div class="board-form bnt">
      <div class="col-auto text-center">
        <button type="submit" class="btn btn-primary mt-2" v-if="type === 'regist'">
          글작성
        </button>
        <button type="submit" class="btn btn-primary mt-2" v-else>
          글수정
        </button>
        <button type="button" class="btn btn-primary mt-2" @click="moveList">
          목록으로 이동...
        </button>
      </div>
    </div>
  </form>
</template>

<script setup>
import {onMounted, ref, defineProps, watch} from 'vue';
import Editor from '@toast-ui/editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import {useRoute, useRouter} from 'vue-router';
import {registArticle, getModifyArticle, modifyArticle} from '@/api/board';
import {useMemberStore} from "@/stores/member";

const memberStore = useMemberStore();
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


const router = useRouter();
const route = useRoute();
const props = defineProps({type: String});
const isUseId = ref(false);

const {articleno, refNo, step, depth} = route.query;

const article = ref({
  articleNo: 0,
  subject: '',
  content: '',
  userId: '',
  userName: '',
  hit: 0,
  registerTime: '',
  ref: 0,
  step: 0,
  depth: 0,
});

// const emit = defineEmits(['update:modelValue']);
const editor = ref();
const editorValid = ref();
const articleContent = ref();

onMounted(() => {
  if (props.type === 'modify') {
    let {articleno} = route.params;
    console.log(articleno + '번 글을 가져와서 수정할 것입니다.');

    // API 호출
    getModifyArticle(
        articleno,
        ({data}) => {
          console.log(data.content);
          article.value = data;
          articleContent.value = data.content;

          // Create the editor after fetching article content
          createEditor();
        },
        (error) => {
          console.log(error);
        }
    );
  } else {
    // If not 'modify', create the editor directly
    createEditor();



  }
});

const createEditor = () => {
  editorValid.value = new Editor({
    el: editor.value,
    height: '500px',
    //'wysiwyg', 'markdown' 택 1
    initialEditType: 'wysiwyg',
    // initialEditType: 'markdown',
    previewStyle: 'vertical',
    initialValue: articleContent.value,

    // events: {
    //   change: () => {
    //     // 에디터의 Markdown 내용을 article.content에 할당
    //     article.value.content = editorValid.value.getHTML();
    //     emit('update:modelValue', article.value.content); // optional: 다른 컴포넌트로 내용을 전달할 수 있도록 emit
    //   },
    // },
  });
};


const subjectErrMsg = ref('');
const contentErrMsg = ref('');
watch(
    () => article.value.subject,
    (value) => {
      let len = value.length;
      if (len === 0 || len > 30) {
        subjectErrMsg.value = '제목을 확인해 주세요!!!';
      } else subjectErrMsg.value = '';
    },
    {immediate: true}
);


const onSubmit = () => {
  console.log(editor.value);
  // 에디터가 완전히 초기화된 후에만 getHTML에 접근하도록 대기
  editorValid.value.on('load', () => {
    const htmlContent = editorValid.value.getHTML();
    console.log('에디터 HTML 콘텐츠:', htmlContent);
  });
  article.value.content = editorValid.value.getHTML();

  props.type === 'regist' ? writeArticle() : updateArticle();

};

const writeArticle = () => {
  console.log('글등록하자!!', article.value);
  // API 호출
  article.value.ref = article.value.articleNo;
  if (articleno) {
    console.log(' 답 글 인 듯 ');
    article.value.ref = refNo;
    article.value.step = parseInt(step);
    article.value.depth = parseInt(depth);
  }

  registArticle(
      article.value,
      ({data}) => {
        console.log('write data: ' + data);
        article.value = data;
        moveList();
      },
      (error) => {
        console.log(error);
      }
  );
};

const updateArticle = () => {
  console.log(article.value.articleNo + '번글 수정하자!!', article.value);
  // API 호출
  modifyArticle(
      article.value,
      ({data}) => {
        console.log(data);
        article.value = data;
      },
      (error) => {
        console.log(error);
      }
  );
  moveDetail();
};

const moveList = () => {
  router.push({name: 'article-list'});
};

const moveDetail = () => {
  router.push({name: 'article-view'});
};


</script>

<style scoped></style>

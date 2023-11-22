<script setup>
import {ref, onMounted , defineProps} from "vue";
import {useRoute, useRouter} from "vue-router";
import { listArticle } from "@/api/board";

import VSelect from "@/components/common/VSelect.vue";
import BoardListItem from "@/components/board/item/BoardListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";

const router = useRouter();
const route = useRoute();

const props = defineProps([
  'changeHero'
])


const selectOption = ref([
  { text: "검색조건", value: "" },
  { text: "글번호", value: "articleno" },
  { text: "제목", value: "subject" },
]);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;

const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "",
  word: "",
  boardType: ""
});
const { boardType } = route.params;

onMounted(() => {
  param.value.boardType = boardType;
  props.changeHero("리스트","헬로")
  getArticleList();
});

const changeKey = (val) => {
  console.log("BoarList에서 선택한 조건 : " + val);
  param.value.key = val;
};

const getArticleList = () => {
  console.log("서버에서 글목록 얻어오자!!!", param.value);
   // API 호출
  listArticle(param.value, ({ data }) => { 
    console.log(data)
    articles.value = data.articles;
    currentPage.value = data.currentPage;
    totalPage.value = data.totalPageCount;
  },
    (error) => { 
      console.log(error)
    });
};

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!");
  currentPage.value = val;
  param.value.pgno = val;
  getArticleList();
};

const moveWrite = () => {
  router.push({ name: "article-write" });
};

</script>


<template>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <div class="row mb-3">
          <div class="col-md-2 text-start">
            <button type="button" class="btn btn-light btn-sm" @click="moveWrite">
              <i class="bi bi-plus-lg"></i> 글쓰기
            </button>
          </div>
          <div class="col-md-5 offset-md-5">
            <form class="d-flex">
              <VSelect :selectOption="selectOption" @onKeySelect="changeKey" class="form-select form-select-sm me-2" />
              <div class="input-group input-group-sm">
                <input type="text" class="form-control" v-model="param.word" placeholder="검색어..." />
                <button class="btn btn-outline-secondary" type="button" @click="getArticleList">검색
                  <i class="bi bi-search"></i>
                </button>
              </div>
            </form>
          </div>
        </div>
        <table class="table table-hover">
          <thead class="text-center">
          <tr>
            <th scope="col">글번호</th>
            <th scope="col">제목</th>
            <th scope="col">작성자</th>
            <th scope="col">조회수</th>
            <th scope="col">작성일</th>
          </tr>
          </thead>
          <tbody>
          <BoardListItem
              v-for="article in articles"
              :key="article.articleNo"
              :article="article"
          ></BoardListItem>
          </tbody>
        </table>
        <PageNavigation
            :current-page="currentPage"
            :total-page="totalPage"
            @pageChange="onPageChange"
            class="pagination justify-content-center"
        ></PageNavigation>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 여기에 필요한 추가 스타일을 정의하세요 */
</style>


<script setup>
import {ref, onMounted, defineProps} from "vue";
import {useRoute} from "vue-router";
import { listArticle } from "@/api/board";

import BoardListItem from "@/components/board/item/BoardListItem.vue";


// const router = useRouter();
const route = useRoute();

const props = defineProps([
  'changeHero'
])

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
  console.log("mount")
  getArticleList();
});




const getArticleList = () => {
  console.log("서버에서 글목록 얻어오자!!!", param.value);
   // API 호출
  listArticle(param.value, ({ data }) => { 
    console.log(data)
    articles.value = data;
    currentPage.value = data.currentPage;
    totalPage.value = data.totalPageCount;
  },
    (error) => { 
      console.log(error)
    });
};



// const moveWrite = () => {
//   router.push({ name: "article-write" });
// };

</script>

<template>

  <div class="untree_co-section">
    <div class="container">
      <div class="row">
        <div class="col-6 col-md-6 col-lg-3">
          <div class="media-1">
            <a href="#" class="d-block mb-3"><img src="@/assets/images/hero-slider-1.jpg" alt="Image" class="img-fluid"></a>
            <div class="d-flex">
              <div>
                <h3><a href="#">Excellence in Travel</a></h3>
                <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
<!--        <table class="table table-hover">-->
<!--          <thead>-->
<!--            <tr class="text-center">-->
<!--              <th scope="col">글번호</th>-->
<!--              <th scope="col">제목</th>-->
<!--              <th scope="col">작성자</th>-->
<!--              <th scope="col">조회수</th>-->
<!--              <th scope="col">작성일</th>-->
<!--            </tr>-->
<!--          </thead>-->
<!--          <tbody>-->
<!--            <BoardListItem-->
<!--              v-for="article in articles"-->
<!--              :key="article.articleNo"-->
<!--              :article="article"-->
<!--            ></BoardListItem>-->
<!--          </tbody>-->
<!--        </table>-->

</template>

<style scoped></style>

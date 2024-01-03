<template>
    <input type="text" class="form-control" placeholder="댓글을 입력하세요" v-model="write">
    <div class="btn-class">
      <button class="btn btn-primary mt-2" @click="clickComment()">댓글 입력</button>
    </div>
<!--   <summary-of-chat-g-p-t :articleno="articleno"/>-->
    <CommentUnit v-for="(comment,index) in commentStore.comments"
                 :key="comment.id"
                 :comment="comment"
                 :index="index"
                  :url="props.url"
    />
</template>


<script setup>

import {onMounted, ref} from "vue";
import {useCommentStore} from "@/stores/comment";
import CommentUnit from "@/components/comment/CommentUnit.vue";
import {addComment, getCommentsForArticle} from "@/api/comment";
import {useMemberStore} from "@/stores/member";
import SummaryOfChatGPT from "@/components/comment/SummaryOfChatGPT.vue";
const write = ref("");

const newComment = ref({
  content: write.value, //얘랑
  userId: "",
  commentNo: "", //얜 필수
});

const commentStore = useCommentStore();
const memberStore = useMemberStore();

const props = defineProps(["url","no"])
console.log(props.no);





function clickComment() {

  newComment.value = {
    content: write.value,
    userId: memberStore.userInfo.userId,
  };
  
  if(props.url === "comments"){
    newComment.value.articleNo = props.no;
  }else{
    newComment.value.sharedTravelRouteNo = props.no;
  }
  
  

  commentStore.comments.push(newComment);
  console.log("------ add" + commentStore.comments);
  addComment(
      props.url,
      newComment.value,
      (response) => {
        console.log("Comment added successfully:", response);
        fetchComments();
      },
      (error) => {
        console.error("Error adding comment:", error);
      }
  );
  write.value = "";
}

const fetchComments = async () => {

  console.log(props.url);
  console.log(props.no);
  try {
    const { data } = await new Promise((resolve, reject) => {
      getCommentsForArticle(
          props.url,
          props.no,
          (response) => resolve(response),
          (error) => reject(error)
      );
    });

    console.log(data);
    commentStore.comments = data;
  } catch (error) {
    console.error(error);
  }
  console.log(commentStore.comments);


};

onMounted(async () => {

  await fetchComments();

})


</script>

<style scoped>
.btn-class{
  margin-bottom: 6px;
}
</style>
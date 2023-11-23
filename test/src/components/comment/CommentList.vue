<template>
  <div>
    <CommentUnit v-for="(comment,index) in commentStore.comments"
                 :key="comment.id"
                 :comment="comment"
                 :index="index"

    />

  </div>
</template>


<script setup>
import CommentUnit from "@/components/comment/CommentUnit.vue";
import {useCommentStore} from "@/stores/comment";
const commentStore = useCommentStore();

// const router = useRouter();



// const updatedComments = computed(() => {
//   // 수정된 댓글을 포함하는 새로운 배열 반환
//   return props.comments.map(comment => {
//     // 현재 수정 중인 댓글인지 확인
//     if (comment.commentNo === newComment.value.commentNo) {
//       // 수정된 댓글 반환
//       return { ...comment, content: newComment.value.content };
//     }
//     // 원래 댓글 반환
//     return comment;
//   });
// });






// const fetchComments = async () => {
//   try {
//     const { data } = await new Promise((resolve, reject) => {
//       getCommentsForArticle(
//           newComment.value.articleNo,
//           (response) => resolve(response),
//           (error) => reject(error)
//       );
//     });
//
//     newComment.value = data;
//   } catch (error) {
//     console.error(error);
//   }
// };




</script>

<style scoped>
</style>
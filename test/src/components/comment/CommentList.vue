<template>

  <div v-for="(comment,index) in commentStore.comments" :key="comment.id" class="comment-item">
    <div class="comment-card">
      <div class="comment-header">
        <span class="user-id">{{ maskUserId(comment.userId) }}</span>
        <span class="edit-delete-icons" v-if="checked(comment.userId)">
          <span class="edit-icon" @click="editComment(comment)">✎</span>
          <span class="delete-icon" @click="deleteButton(comment,index)">❌</span>
        </span>
      </div>
      <div class="comment-divider"></div>
      <div v-if="!isChanging" class="comment-content">
        {{ comment.content }}
      </div>
      <div v-if="isChanging" class="comment-content">
        <input type="text" class="form-control" placeholder="댓글을 입력하세요" v-model="write">
        <button class="btn btn-primary mt-2" @click="clickComment(comment)">댓글 입력</button>
      </div>
    </div>
    <div class="comment-divider"></div>

  <div>
    <CommentUnit v-for="(comment,index) in commentStore.comments"
                 :key="comment.id"
                 :comment="comment"
                 :index="index"

    />

  </div>
</template>


<script setup>

import {storeToRefs} from "pinia";
import {useMemberStore} from "@/stores/member";
import {ref} from "vue";
import {deleteComment, modifyComment} from "@/api/comment";
import {useCommentStore} from "@/stores/comment";

// const router = useRouter();
const memberStore = useMemberStore();
const isChanging = ref(false); // 수정 중인지 판단.
const write = ref("");

const {userInfo} = storeToRefs(memberStore);

const newComment = ref({
  content: write.value, //얘랑
  userId: "",
  articleNo: "",
  commentNo: "", //얜 필수
});

const commentStore = useCommentStore();

// const newComment = ref(props);
console.log(newComment.value);

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
<script setup>


import {deleteComment, modifyComment} from "@/api/comment";
import {useMemberStore} from "@/stores/member";
import {ref} from "vue";
import {useCommentStore} from "@/stores/comment";

let props = defineProps(['comment', 'index']);
const memberStore = useMemberStore();
const isChanging = ref(false); // 수정 중인지 판단.
const write = ref("");
const commentStore = useCommentStore();

const maskUserId = (userId) => {
  // 앞의 두 글자를 남기고 나머지 부분을 *로 바꾸어 반환
  return userId ? userId.slice(0, 2) + '*'.repeat(userId.length - 2) : '';
};


const checked = (id) => {
  if (id === memberStore.userInfo.userId) { // comment id 와 현재 로그인 사용자가 같다면
    return true;
  }
}



function clickComment(comment) {
  comment.content = write.value;
  console.log(comment.commentNo)

  modifyComment(
      comment,
      (response) => {
        console.log("Comment modify successfully:", response);
        isChanging.value = !isChanging.value;
      },
      (error) => {
        console.error("Error modify comment:", error);
      }
  );


}


const editComment = (comment) => { //userId, commentNo
  // 수정 로직을 여기에 추가
  console.log(comment);
  isChanging.value = !isChanging.value;
  // 수정하로 가기
  write.value = comment.content;
  console.log("editComment:" + write.value);
};

const deleteButton =  (comment,index) => {
  // 삭제 로직을 여기에 추가
  console.log('Delete comment with ID:', comment.commentNo);
  deleteComment(
      comment.commentNo,
      () => {
        console.log("------ delete before" + commentStore.comments.length)
        if (index !== -1) {
          commentStore.comments.splice(index, 1);
        }
        console.log("------ delete" + commentStore.comments.length)
      },
      (error) => {
        console.error("Error delete comment:", error);
      }
  );
  // 로컬에서 삭제된 댓글을 갱신

  // updatedComments.value = updatedComments.value.filter(comment => comment.commentNo !== commentNo);
};

</script>
<template>

  <div class="comment-card">
    <div class="comment-header">
      <!--        {{comment}}-->
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

</template>


<style scoped>
.comment-item {
  margin-bottom: 10px;
}

.comment-card {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 16px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  position: relative;
}

.comment-header {
  font-size: 14px;
  font-weight: bold;
  color: #333;
  display: flex;
  justify-content: space-between;
  align-items: center; /* 추가된 라인 */
}

.user-id {
  flex-grow: 1; /* 추가된 라인 */
}

.comment-divider {
  border-top: 1px solid #ddd;
  margin: 10px 0;
}


.comment-content {
  margin-top: 8px;
  font-size: 16px;
  color: #555;
}

.comment-divider {
  border-top: 1px solid #ddd;
  margin: 10px 0;
}

.edit-delete-icons {
  display: flex;
  align-items: center; /* 추가된 라인 */
}

.edit-icon,
.delete-icon {
  cursor: pointer;
  margin-left: 10px;
  color: #3498db;
}

.delete-icon {
  color: #e74c3c;
}
</style>
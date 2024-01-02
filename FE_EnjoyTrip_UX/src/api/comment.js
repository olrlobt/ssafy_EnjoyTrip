import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance


function addComment(url,comment, success, fail) {
    console.log(comment)

    local.post(`${url}/add`, JSON.stringify(comment))
        .then(success)
        .catch(fail);
}

function getCommentsForArticle(url,articleNo, success, fail) {
    console.log("url : " + url)

    local.get(`${url}/get/${articleNo}`)
        .then(success)
        .catch(fail);
}

function modifyComment(url,comment, success, fail){
    local.put(`${url}/modify`, JSON.stringify(comment))
        .then(success)
        .catch(fail);
}

function deleteComment(url,commentNo, success, fail) {
    console.log("deleteComment:", commentNo);
    local.delete(`${url}/delete/${commentNo}`)
        .then(success)
        .catch(fail);
}
export {
  addComment, getCommentsForArticle, modifyComment, deleteComment,
};

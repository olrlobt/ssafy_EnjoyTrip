import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/comments";

function addComment(comment, success, fail) {
    local.post(`${url}/add`, JSON.stringify(comment))
        .then(success)
        .catch(fail);
}

function getCommentsForArticle(articleNo, success, fail) {
    local.get(`${url}/get/${articleNo}`)
        .then(success)
        .catch(fail);
}
export {
  addComment, getCommentsForArticle,
};

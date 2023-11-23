import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

// const url = "http://70.12.60.179:80/article"
const url = "/article"

function listArticle(param, success, fail) {
  console.log("param", param)
  local.get(`${url}/${param.boardType}`, { params: param }).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
  local.get(`${url}/notice/view/${articleno}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  console.log("article registArticle.js: ", article.type)
  local.post(`${url}/${article.type}/write`, JSON.stringify(article)).then(success).catch(fail);
}

function getModifyArticle(articleno, success, fail) {
  local.get(`${url}/notice/modify/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  local.put(`${url}/notice/modify`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
  local.delete(`${url}/notice/delete/${articleno}`).then(success).catch(fail);
}

export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
};

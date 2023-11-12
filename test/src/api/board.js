import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/article"

function listArticle(param, success, fail) {
  local.get(`${url}/${param.boardType}`, { params: param }).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
  local.get(`${url}/view/${articleno}`).then(success).catch(fail);
}

function registArticle(boardType, article, success, fail) {
  console.log("boardjs article", article, boardType);

  local.post(`${url}/${boardType}/write`, JSON.stringify(article)).then(success).catch(fail);
}

function getModifyArticle(articleno, success, fail) {
  local.get(`${url}/modify/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  local.put(`${url}/modify`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
  local.delete(`${url}/delete/${articleno}`).then(success).catch(fail);
}



export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
};

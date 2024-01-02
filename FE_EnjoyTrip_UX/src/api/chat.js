import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/chat"; // ChatGPT API 엔드포인트 주소에 맞게 수정하세요.

function chatWithOpenAI(comments, success, fail) {
    console.log("chatWithOpenAI여기까지는 오나요" + comments);
    local.post(`${url}`, JSON.stringify(comments))
        .then(success)
        .catch(fail);
}

function getContentWithOpenAI(title, success, fail){
    console.log("getContentWithOpenAI : " + title);
    local.post(`${url}/content`,JSON.stringify(title))
        .then(success)
        .catch(fail);

}

export { chatWithOpenAI, getContentWithOpenAI };

import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/user"

function joinUser(member, success, fail) {
    console.log("userjoin.js", member);
    local.post(`${url}/join`, JSON.stringify(member)).then(success).catch(fail);
}

export {
    joinUser,
  };
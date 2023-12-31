import { localAxios } from "@/util/http-commons";

const local = localAxios();

// const url = "http://70.12.60.179:80/user"
const url = "/user"

function joinUser(member, success, fail) {
    local.post(`${url}/join`, JSON.stringify(member)).then(success).catch(fail);
}

async function userConfirm(param, success, fail) {
    await local.post(`${url}/login`, param).then(success).catch(fail);
    console.log("userConfirm ok");
  }
  
  async function findById(userid, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`${url}/info/${userid}`).then(success).catch(fail);
  }
  
  async function tokenRegeneration(user, success, fail) {
    local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
    await local.post(`${url}/refresh`, user).then(success).catch(fail);
  }
  
  async function logout(userid, success, fail) {
    await local.get(`${url}/logout/${userid}`).then(success).catch(fail);
}
  
function modifyUser(member, success, fail) { 
  local.put(`${url}/modify`, JSON.stringify(member)).then(success).catch(fail);
}

export {
    joinUser,
    userConfirm,
    findById,
    tokenRegeneration,
    logout,
    modifyUser,
  };
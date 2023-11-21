import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/share"

function saveTravelRoute(travelRouteDto, success, fail) {
    local.post(`${url}/write`, JSON.stringify(travelRouteDto)).then(success).catch(fail);
}

async function userConfirm(param, success, fail) {
    await local.post(`${url}/login`, param).then(success).catch(fail);
}


export {
    saveTravelRoute,
};
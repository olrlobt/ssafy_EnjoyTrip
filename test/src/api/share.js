import { localAxios } from "@/util/http-commons";

const local = localAxios();

// const url = "http://70.12.60.179:80/share"
const url = "/share"

function saveTravelRoute(travelRouteDto, success, fail) {
    local.post(`${url}/write`, travelRouteDto).then(success).catch(fail);
}

async function getTravelRoute(userId, success, fail) {
    await local.get(`${url}/list/${userId}`).then(success).catch(fail);
}

function deleteTravelRoute(travelRouteNo, success, fail) {
    local.delete(`${url}/delete/${travelRouteNo}`).then(success).catch(fail);
}

function shareTravelRoute(sharedTravelRouteDto, success, fail) {
    local.post(`${url}/writeShared`, sharedTravelRouteDto).then(success).catch(fail);
}

function getSharedTravelRoute(success, fail) {
    local.get(`${url}/listSharedTravelRoute`).then(success).catch(fail);
}


export {
    saveTravelRoute,
    getTravelRoute,
    deleteTravelRoute,
    shareTravelRoute,
    getSharedTravelRoute,

};
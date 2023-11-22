import { localAxios } from "@/util/http-commons";

const local = localAxios();

// const url = "http://70.12.60.179:80/share"
const url = "/share"

function saveTravelRoute(travelRouteDto, success, fail) {
    console.log("saveTravelRoute" + travelRouteDto);
    console.log(travelRouteDto);
    local.post(`${url}/write`, travelRouteDto).then(success).catch(fail);
}

async function getTravelRoute(travelRouteDto, success, fail) {
    await local.get(`${url}/list`, travelRouteDto).then(success).catch(fail);
}


export {
    saveTravelRoute,
    getTravelRoute,
};
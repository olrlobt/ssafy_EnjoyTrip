<script setup>
import sido from "@/assets/json/sido.json";
import sig from "@/assets/json/sig.json";
import KakaoMarker from "@/components/map/KakaoMarkerSearch.vue"
import KakaoRoute from "@/components/map/KakaoSideList.vue";
import KakaoMarkerPopup from "@/components/map/KakaoDetailPopup.vue"
import {ref, onMounted} from "vue";


const polygons = ref([]);
const detailMode = ref(false);
const {VITE_MAP_SRC} = import.meta.env;

const areas = ref([]);
const map = ref(null);
const customOverlay = ref(null);
const mapLoaded = ref(false);
const clickMarker = ref(null);


onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    /* global kakao */
    script.onload = () => kakao.maps.load(initMap);
    script.src = VITE_MAP_SRC;
    document.head.appendChild(script);
  }
})

function initMap() {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(35.566826, 128.0786567),
    level: 13,
  };

  map.value = new kakao.maps.Map(container, options);
  customOverlay.value = new kakao.maps.CustomOverlay({});
  kakao.maps.event.addListener(map.value, 'zoom_changed', handleZoomChange);
  readJson(sido);
  mapLoaded.value = true;
}

function handleZoomChange() {
  let level = map.value.getLevel();

  if (!detailMode.value && level <= 10) {
    detailMode.value = true;
    removePolygon();
    readJson(sig)
  } else if (detailMode.value && level > 10) {
    detailMode.value = false;
    removePolygon();
    readJson(sido)
  }
}

function removePolygon() {
  polygons.value.forEach(polygon => polygon.setMap(null));
  areas.value = [];
  polygons.value = [];
}

function readJson(json) {
  const units = json.features;
  units.forEach((unit, index) => {
    let coordinates = unit.geometry.coordinates;
    let name = unit.properties.SIG_KOR_NM;
    let cd_location = unit.properties.SIG_CD;

    let ob = {
      name: name,
      path: [],
      location: cd_location
    };

    coordinates[0].forEach((coordinate) => {
      ob.path.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));
    });

    areas.value[index] = ob;
  });
  // 지도에 영역데이터를 폴리곤으로 표시합니다
  for (var i = 0; i < areas.value.length; i++) {
    displayArea(areas.value[i]);
  }
}

function displayArea(area) {
  var polygon = new kakao.maps.Polygon({
    map: map.value, // 다각형을 표시할 지도 객체
    path: area.path,
    strokeWeight: 2,
    strokeColor: '#004c80',
    strokeOpacity: 0.8,
    fillColor: '#fff',
    fillOpacity: 0.7
  });

  polygons.value.push(polygon);

  kakao.maps.event.addListener(polygon, 'mouseover', (mouseEvent) => {
    polygon.setOptions({fillColor: '#09f'});

    customOverlay.value.setContent('<div class="area">' + area.name + '</div>');
    customOverlay.value.setPosition(mouseEvent.latLng);
    customOverlay.value.setMap(map.value);
  });
  kakao.maps.event.addListener(polygon, 'mousemove', (mouseEvent) => {
    customOverlay.value.setPosition(mouseEvent.latLng);
  });
  kakao.maps.event.addListener(polygon, 'mouseout', () => {
    polygon.setOptions({fillColor: '#fff'});
    customOverlay.value.setMap(null);
  });
  kakao.maps.event.addListener(polygon, 'click', (mouseEvent) => {
    if (!detailMode.value) {
      map.value.setLevel(10);
      map.value.panTo(mouseEvent.latLng);
    } else {

      if(clickMarker.value){
        clickMarker.value.callAPIWithRegionCode(area.location);
      }
    }
  });
}
</script>

<template>

    <div id="map" style="width: 100%; height: 100vh;">
        <KakaoMarker v-if="mapLoaded" :map="map" ref="clickMarker"/>
        <KakaoRoute/>
        <KakaoMarkerPopup/>
    </div>

</template>

<style>
@import "@/assets/css/map.css";
</style>

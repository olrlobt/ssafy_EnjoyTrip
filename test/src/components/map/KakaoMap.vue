<script>
import sido from "@/assets/json/sido.json";
import sig from "@/assets/json/sig.json";
import {ref} from "vue";

let polygons = [];

let detailMode = ref(false);
const { VITE_MAP_SRC } = import.meta.env;

export default {
  name: "KakaoMap",
  data() {
    return {
      areas : [],
      markers: [],
      infowindow: null,
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = VITE_MAP_SRC;
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567),
        level: 12,
      };
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(container, options);
      this.customOverlay = new kakao.maps.CustomOverlay({});
      kakao.maps.event.addListener(this.map, 'zoom_changed', this.handleZoomChange);
      this.readJson(sido);
    },
    handleZoomChange(){
      let level = this.map.getLevel();

      if (!detailMode.value && level <= 10) {
        detailMode.value = true;
        this.removePolygon();
        this.readJson(sig)
      } else if (detailMode.value && level > 10) {
        detailMode.value = false;
        this.removePolygon();
        this.readJson(sido)
      }
    },
    removePolygon() {
        polygons.forEach(polygon => polygon.setMap(null));
        this.areas = [];
        polygons = [];
    },
    readJson(json) {
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

        this.areas[index] = ob;
      });
      // 지도에 영역데이터를 폴리곤으로 표시합니다
      for (var i = 0; i < this.areas.length; i++) {
        this.displayArea(this.areas[i]);
      }
    },
    displayArea(area) {
      var polygon = new kakao.maps.Polygon({
        map: this.map, // 다각형을 표시할 지도 객체
        path: area.path,
        strokeWeight: 2,
        strokeColor: '#004c80',
        strokeOpacity: 0.8,
        fillColor: '#fff',
        fillOpacity: 0.7
      });

      polygons.push(polygon);

      kakao.maps.event.addListener(polygon, 'mouseover', (mouseEvent) => {
        polygon.setOptions({fillColor: '#09f'});

        this.customOverlay.setContent('<div class="area">' + area.name + '</div>');
        this.customOverlay.setPosition(mouseEvent.latLng);
        this.customOverlay.setMap(this.map);
      });
      kakao.maps.event.addListener(polygon, 'mousemove', (mouseEvent) => {
        this.customOverlay.setPosition(mouseEvent.latLng);
      });
      kakao.maps.event.addListener(polygon, 'mouseout', () =>  {
        polygon.setOptions({fillColor: '#fff'});
        this.customOverlay.setMap(null);
      });
      kakao.maps.event.addListener(polygon, 'click', (mouseEvent) => {
        if (!detailMode.value) {
          this.map.setLevel(10);
          this.map.panTo(mouseEvent.latLng);
        } else {

          // callFunctionWithRegionCode(area.location);
        }
      });
    },


  },
};
</script>

<template>
  <div class="container-map">
    <div class="sidebar">
      <h3>여행 경로</h3>
      <ul id="travelRouteList">
        <!-- 여기에 장소가 추가됩니다 -->
      </ul>

      <div class="button-group">

        <button id="shortRoute_start" class="beautiful-button">시작지 최단경로 보기</button>
        <button id="shortRoute_return" class="beautiful-button">순회 최단경로 보기</button>
        <button id="shortRoute" class="beautiful-button">여행 최단경로 보기</button>
      </div>

      <button id="createRoute" class="route-button">경로 생성</button>
    </div>

    <div id="map" style="width: calc(100% - 250px); height: 1200px;"></div>
  </div>
</template>

<style scoped>
@import "@/assets/css/map.css";
</style>

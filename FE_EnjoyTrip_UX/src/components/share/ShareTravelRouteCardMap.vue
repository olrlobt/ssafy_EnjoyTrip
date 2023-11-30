<script setup>
import {onMounted, ref} from "vue";
const {VITE_MAP_SRC} = import.meta.env;

let props = defineProps(['markers']);

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
});

const map = ref(null);
const mapCard = ref(null);

function initMap() {
  const options = {
    center: new kakao.maps.LatLng(35.566826, 128.0786567),
    level: 6,
    draggable: false,
    disableDoubleClick : false,
    disableDoubleClickZoom : false,
    keyboardShortcuts : false,
  };

   map.value = new kakao.maps.Map(mapCard.value, options);
   console.log(props.markers)
  setMapBounds();
}

function setMapBounds() {
  let bounds = new kakao.maps.LatLngBounds();

  if(props.markers){
    props.markers.forEach(marker => {

      let markerPosition = new kakao.maps.LatLng(marker.mapy, marker.mapx);

      new kakao.maps.Marker({
        position: markerPosition,
        title: marker.title,
        map: map.value
      });
      bounds.extend(new kakao.maps.LatLng(marker.mapy, marker.mapx));
    });

    map.value.setBounds(bounds);
    drawTravelLine();
  }
}



function drawTravelLine() {
  const linePath = [];

  props.markers.forEach(item => {
    linePath.push(new kakao.maps.LatLng(item.mapy, item.mapx));
  });

  const polylineOptions = {
    path: linePath,
    strokeWeight: 3,
    strokeColor: '#db4040',
    strokeOpacity: 0.7,
    strokeStyle: 'solid',
    map: map.value
  };

  new kakao.maps.Polyline(polylineOptions);
}


</script>
<template>

  <div id="mapCard" ref="mapCard">
  </div>

</template>
<style>


</style>
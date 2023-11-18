<script setup>
import KakaoDetailPopup from "@/components/map/KakaoDetailPopup.vue";
import { ref, onMounted } from 'vue';


const position = ref({ x: 130, y: 30 , dragging: false});
const minimizedPosition = ref({ x: 0, y: 0 });


onMounted(() => {
  const popupBoxEl = document.querySelector('.popup-box');
  const popupBoxRect = popupBoxEl.getBoundingClientRect();
  hideMarkerPopup();
  const minimizeHandleEl = document.querySelector('.minimize-handle');
  if (minimizeHandleEl) {
    const rect = minimizeHandleEl.getBoundingClientRect();
    minimizedPosition.value = {
      x: rect.left - popupBoxRect.left,
      y: rect.top - popupBoxRect.top
    };
  }
});

const getPopupBoxStyle = () => {
  if (isMinimized.value) {
    return {
      transform: `translate(${minimizedPosition.value.x}px, ${minimizedPosition.value.y}px) scale(0)`,
      transition: 'transform 0.3s',
      position: 'absolute',
      right: `${position.value.x}px`,
      top: `${position.value.y}px`,
      cursor: position.value.dragging ? 'grabbing' : 'grab'
    };
  } else {
    return {
      transform: 'translate(0, 0) scale(1)',
      transition: 'transform 0.3s',
      position: 'absolute',
      right: `${position.value.x}px`,
      top: `${position.value.y}px`,
      cursor: position.value.dragging ? 'grabbing' : 'grab'
    };
  }
};


let startX, startY;


const startDrag = (event) => {
  startX = event.clientX;
  startY = event.clientY;
  position.value.dragging = true;

  document.addEventListener('mousemove', onDrag);
  document.addEventListener('mouseup', stopDrag);
};

const onDrag = (event) => {

  if (position.value.dragging) {
    // 드래그 중 마우스의 현재 위치와 시작 위치의 차이를 계산
    const dx = event.clientX - startX;
    const dy = event.clientY - startY;

    // 드래그 대상의 새 위치 계산
    position.value.x -= dx;
    position.value.y += dy;
    minimizedPosition.value.x -= dx;
    minimizedPosition.value.y -= dy;
    // 다음 이벤트를 위해 현재 위치를 시작 위치로 업데이트
    startX = event.clientX;
    startY = event.clientY;
  }
};

const stopDrag = () => {
  position.value.dragging = false;
  document.removeEventListener('mousemove', onDrag);
  document.removeEventListener('mouseup', stopDrag);
};

const isMinimized = ref(true);

function hideMarkerPopup() {
  isMinimized.value = !isMinimized.value;
}

</script>


<template>
  <div class="popup-box ">
    <KakaoDetailPopup
        @mousedown="startDrag"
        :hideMarkerPopup="hideMarkerPopup"
        :style="getPopupBoxStyle()"
    />
    <div class="minimize-handle"  v-if="isMinimized" @click="hideMarkerPopup"></div>
  </div>
</template>


<style>

.popup-box{
  position: fixed;
  right: 30px;
  top: 30px;
  width: 30%;
  max-width: 700px;
  max-height: 50%;
  height: 50%;
  z-index: 100;
}


.minimize-handle {
  position: fixed;
  right: 0; /* 오른쪽 끝에 맞춤 */
  top: 30%; /* 상단에 맞춤 */
  width: 20px;
  height: 100px;
  background-color: #e8eaee;
  border-radius: 10px 0px 0px 10px; /* 오른쪽 상단 모서리 둥글게 */
  box-shadow: 0 2px 4px rgba(0,0,0,0.5);
  cursor: pointer;
  z-index: 100;
}

.minimize-handle::before {
  content: '';
  display: block;
  width: 0;
  height: 0;
  border-top: 10px solid transparent;
  border-bottom: 10px solid transparent;
  border-right: 10px solid #b6b7bb;
  position: absolute;
  right: 20%; /* 화살표 위치 조정 */
  top: 50%;
  transform: translateY(-50%);
}

@keyframes minimize {
  from {
    transform: translate(0, 0) scale(1);
  }
  to {
    transform: translate(calc(100% - 30px), -50%) scale(0);
  }
}

@keyframes maximize {
  from {
    transform: translate(calc(100% - 30px), -50%) scale(0);
  }
  to {
    transform: translate(0, 0) scale(1);
  }
}

.minimized {
  animation: minimize 0.3s forwards;
}

.maximized {
  animation: maximize 0.3s forwards;
}


</style>

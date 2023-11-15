import { ref } from "vue";
import { defineStore } from "pinia";

export const useMenuStore = defineStore("menuStore", () => {
  const menuList = ref([
    { name: "회원가입", show: true, routeName: "join" },
    { name: "로그인", show: true, routeName: "login" },
    { name: "내정보", show: false, routeName: "mypage" },
    { name: "로그아웃", show: false, routeName: "logout" },
  ]);

  const changeMenuState = () => {
    menuList.value = menuList.value.map((item) => ({ ...item, show: !item.show }));
  };
  return {
    menuList,
    changeMenuState,
  };
});

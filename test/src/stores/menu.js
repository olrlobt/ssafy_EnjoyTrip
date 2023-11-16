import { ref } from "vue";
import { defineStore } from "pinia";
import { useMemberStore } from "@/stores/member";


export const useMenuStore = defineStore("menuStore", () => {
  
  // const store = useMemberStore();
  // const { userInfo } = storeToRefs(store);

  const menuList = ref([
    { name: "로그인/회원가입", show: true, routeName: "login" },
    { name: "내정보", show: false, routeName: "mypage" },
    { name: "로그아웃", show: false, routeName: "logout" },
  ]);

  // const hasUser = userInfo;

  // const changeMenu = () => { 
  // if (hasUser) { 
  //   console.log("들어오나");
  //   changeMenuState();
  //   }
  // }
  
  const changeMenuState = () => {
    menuList.value = menuList.value.map((item) => ({ ...item, show: !item.show }));
  };
  return {
    menuList,
    changeMenuState,
    // changeMenu,
  };
});

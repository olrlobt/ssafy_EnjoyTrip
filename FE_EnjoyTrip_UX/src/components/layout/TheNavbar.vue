<script setup>
</script>

<template>
    <!--::header part start::-->
   <header class="main_menu">
        <div class="sub_menu">
            <div class="container">
                <div class="row">
                    <div class="col-lg-6 col-sm-12 col-md-6">
                    </div>
                    <div class="col-lg-6 col-sm-12 col-md-6">
                        <div class="sub_menu_social_icon">
                            <a href="#"><i class="flaticon-facebook"></i></a>
                            <a href="#"><i class="flaticon-twitter"></i></a>
                            <a href="#"><i class="flaticon-skype"></i></a>
                            <a href="#"><i class="flaticon-instagram"></i></a>
                            <div class="header_nav_menu">
                                <div id="header_nav_menuitem_off">
                                  <div class="header_nav_menuitem">
                                    <RouterLink to="/login"><span>로그인</span></RouterLink>
                                    <RouterLink to="/join"><span>회원가입</span></RouterLink>
                                  </div>
                                </div>
                      
                                <!-- 로그인 후 나오게 -->
                                <div id="header_nav_menuitem_on" style="display: none">
                                  <div class="header_nav_menuitem">
                                    <a class="nav_link" href="#" onclick="javascript:logout();"><span>로그아웃</span></a>
                                    <a class="nav_link" href="#" onclick="javascript:loadMyPage()">마이페이지</a>
                                  </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="main_menu_iner">
            <div class="container">
                <div class="row align-items-center ">
                    <div class="col-lg-12">
                        <nav class="navbar navbar-expand-lg navbar-light justify-content-between">
                            <RouterLink to="/" class="navbar-brand"> <img src="@/assets/img/logo.png" alt="logo" style="width: 150px"> </RouterLink>
                            <button class="navbar-toggler" type="button" data-toggle="collapse"
                                data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                                aria-expanded="false" aria-label="Toggle navigation">
                                <span class="navbar-toggler-icon"></span>
                            </button>

                            <div class="collapse navbar-collapse main-menu-item justify-content-center"
                                id="navbarSupportedContent">
                                <ul class="navbar-nav">
                                    <li class="nav-item">
                                        <RouterLink to="/map" class="nav-link">여행 떠나기 </RouterLink>
                                    </li>

                                    <li class="nav-item">
                                        <RouterLink to="/article/travel" class="nav-link">여행 정보 공유</RouterLink>
                                    </li>
                                    <li class="nav-item">
                                        <RouterLink to="/article/hot" class="nav-link">핫플레이스</RouterLink>
                                    </li>
                                    <li class="nav-item">
                                        <RouterLink to="/article/notice" class="nav-link">공지사항</RouterLink>
                                    </li>

                                </ul>
                            </div>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </header>
    <!-- Header part end-->
</template>

<style scoped>

</style>
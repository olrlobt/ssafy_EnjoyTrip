<template>
  <nav class="site-nav">
    <div class="container">
      <div class="site-navigation">
        <a href="../views/index.html" class="logo m-0"><img src="@/assets/img/logo.png" width="75px"><span class="text-primary">.</span></a>
        <ul class="js-clone-nav d-none d-lg-inline-block text-left site-menu float-right">
          <li class="active"><a href="../views/index.html">Home</a></li>

          <li><a href="services.html">Map</a></li>

          <li class="has-children">
            <a href="#">Share UX</a>
            <ul class="dropdown">
              <li><a href="#">My Route</a></li>
              <li><a href="#">My Likes</a></li>
            </ul>
          </li>

          <li class="has-children menu-item">
            <a href="#">CS</a>
            <ul class="dropdown">
              <li><a href="#">Notice</a></li>
              <li><a href="#">Q&A</a></li>
            </ul>
          </li>
          <li><a href="#">Login / Sign up</a></li>
          <!--          <li class="has-children">-->
<!--            <a href="#">Share UX</a>-->
<!--            <ul class="dropdown">-->
<!--              <li><a href="elements.html">Elements</a></li>-->
<!--              <li><a href="#">My route</a></li>-->
<!--              <li class="has-children">-->
<!--                <a href="#">Menu Two</a>-->
<!--                <ul class="dropdown">-->
<!--                  <li><a href="#">Sub Menu One</a></li>-->
<!--                  <li><a href="#">Sub Menu Two</a></li>-->
<!--                  <li><a href="#">Sub Menu Three</a></li>-->
<!--                </ul>-->
<!--              </li>-->
<!--              <li><a href="#">Menu Three</a></li>-->
<!--            </ul>-->
<!--          </li>-->



        </ul>
        <a href="#" class="burger ml-auto float-right site-menu-toggle js-menu-toggle d-inline-block d-lg-none light" data-toggle="collapse" data-target="#main-navbar">
          <span></span>
        </a>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'Navbar',
  // 여기에 필요한 데이터, 메서드, 컴퓨티드 프로퍼티 등을 추가하세요
};
</script>

<style scoped>
/* 네비게이션 바에 대한 CSS 스타일 */

.menu-item:not(:last-child):after {
  content: "|"; /* 구분자 */
  color: #cce0dc; /* 구분자 색상 */
  margin-left: 15px; /* 왼쪽 여백 */
}

</style>

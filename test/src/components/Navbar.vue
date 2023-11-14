<template>
  <nav class="site-nav">
    <div class="container">
      <div class="site-navigation">
        <a href="../views/index.html" class="logo m-0">Tour <span class="text-primary">.</span></a>
        <ul class="js-clone-nav d-none d-lg-inline-block text-left site-menu float-right">
          <li class="active"><a href="../views/index.html">Home</a></li>
          <li class="has-children">
            <a href="#">Dropdown</a>
            <ul class="dropdown">
              <li><a href="elements.html">Elements</a></li>
              <li><a href="#">Menu One</a></li>
              <li class="has-children">
                <a href="#">Menu Two</a>
                <ul class="dropdown">
                  <li><a href="#">Sub Menu One</a></li>
                  <li><a href="#">Sub Menu Two</a></li>
                  <li><a href="#">Sub Menu Three</a></li>
                </ul>
              </li>
              <li><a href="#">Menu Three</a></li>
            </ul>
          </li>
          <li><a href="services.html">Services</a></li>
          <li><a href="about.html">About</a></li>
          <li><a href="contact.html">Contact Us</a></li>
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
</style>

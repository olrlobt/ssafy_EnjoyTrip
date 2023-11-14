<template>
  <div class="hero">
    <div class="container">
      <div class="row align-items-center">
        <div class="col-lg-7">
          <div class="intro-wrap">
            <h1 class="mb-5">
              <span class="d-block">Let's Enjoy Your</span> Trip In <span class="typed-words"></span>
            </h1>

            <div class="row">
              <div class="col-12">
                <form class="form">
                  <div class="row mb-2">
                    <div class="col-sm-12 col-md-6 mb-3 mb-lg-0 col-lg-4">
                      <select name="" id="" class="form-control custom-select">
                        <option value="">Destination</option>
                        <option value="">Peru</option>
                        <option value="">Japan</option>
                        <option value="">Thailand</option>
                        <option value="">Brazil</option>
                        <option value="">United States</option>
                        <option value="">Israel</option>
                        <option value="">China</option>
                        <option value="">Russia</option>
                      </select>
                    </div>
                    <div class="col-sm-12 col-md-6 mb-3 mb-lg-0 col-lg-5">
                      <input type="text" class="form-control" name="daterange">
                    </div>
                    <div class="col-sm-12 col-md-6 mb-3 mb-lg-0 col-lg-3">
                      <input type="text" class="form-control" placeholder="# of People">
                    </div>
                  </div>
                  <div class="row align-items-center">
                    <div class="col-sm-12 col-md-6 mb-3 mb-lg-0 col-lg-4">
                      <input type="submit" class="btn btn-primary btn-block" value="Search">
                    </div>
                    <div class="col-lg-8">
                      <label class="control control--checkbox mt-3">
                        <span class="caption">Save this search</span>
                        <input type="checkbox" checked="checked" />
                        <div class="control__indicator"></div>
                      </label>
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-5">
          <div class="slides">
            <img src="@/assets/images/hero-slider-1.jpg" alt="Image" class="img-fluid active">
            <img src="@/assets/images/hero-slider-2.jpg" alt="Image" class="img-fluid">
            <img src="@/assets/images/hero-slider-3.jpg" alt="Image" class="img-fluid">
            <img src="@/assets/images/hero-slider-4.jpg" alt="Image" class="img-fluid">
            <img src="@/assets/images/hero-slider-5.jpg" alt="Image" class="img-fluid">
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HeroSection',
  // 필요한 데이터, 메서드, 컴퓨티드 프로퍼티 등을 추가하세요
};
</script>

<style scoped>
/* 히어로 섹션에 대한 CSS 스타일 */


.typed-cursor{
  opacity: 1;
}
.typed-cursor.typed-cursor--blink{
  animation: typedjsBlink 0.7s infinite;
  -webkit-animation: typedjsBlink 0.7s infinite;
  animation: typedjsBlink 0.7s infinite;
}
@keyframes typedjsBlink{
  50% { opacity: 0.0; }
}
@-webkit-keyframes typedjsBlink{
  0% { opacity: 1; }
  50% { opacity: 0.0; }
  100% { opacity: 1; }
}

</style>


<script setup>

</script>

<template>
  <div class="hero">
    <div class="container">
      <div class="row align-items-center">
        <div class="col-lg-7">
          <div class="intro-wrap">
            <h1 class="mb-5">
              <span class="d-block">UX</span>: <span>너와</span>의 경험<span class="typed-words"></span>
            </h1>

            <div class="row">
              <div class="col-12">
                <form class="form">
                  <div class="row mb-2">
                    <div class="col-sm-12 col-md-6 mb-3 mb-lg-0 col-lg-4">
                      <select name="" id="" class="form-control custom-select">
                        <option value="12" selected>Select Travel Type</option>
                        <option value="12">관광지</option>
                        <option value="14">문화시설</option>
                        <option value="15">축제공연행사</option>
                        <option value="25">여행코스</option>
                        <option value="28">레포츠</option>
                        <option value="32">숙박</option>
                        <option value="38">쇼핑</option>
                        <option value="39">음식점</option>
                      </select>
                    </div>
                    <div class="col-sm-12 col-md-6 mb-3 mb-lg-0 col-lg-5">
                      <input type="text" class="form-control" name="daterange">
                    </div>
                    <div class="col-sm-12 col-md-6 mb-3 mb-lg-0 col-lg-3">
                      <input type="submit" class="btn btn-primary btn-block" value="Search">

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


<style scoped>

</style>

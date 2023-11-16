<script>
// export default {
//     props: {
//         type: {     
//             type: String,
//             default: 'default type' 
//         }
//     }
// }

export default {
    props: ['title', 'contents'],
    setup() {
      return {}
    }
  }
</script>


<template>
    <div class="hero">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-lg-6 mx-auto text-center">
            <div class="intro-wrap">
              <h1 class="mb-0">{{title}}</h1>
              <p class="text-white ">{{ contents }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    
</template>

<style></style>
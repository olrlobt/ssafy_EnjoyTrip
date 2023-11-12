<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<head>
<link rel="icon" href="assets/img/favicon.png">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/animate.css">
<link rel="stylesheet" type="text/css" href="css/owl.carousel.min.css">
<link rel="stylesheet" type="text/css" href="css/magnific-popup.css">
<link rel="stylesheet" type="text/css" href="css/gijgo.min.css">
<link rel="stylesheet" type="text/css" href="css/nice-select.css">
<link rel="stylesheet" type="text/css" href="css/slick.css">
<link rel="stylesheet" type="text/css" href="css/style.css">

<!-- magnific CSS -->
<link rel="stylesheet" href="css/magnific-popup.css">
<link rel="stylesheet" href="css/gijgo.min.css">
<!-- niceselect CSS -->
<link rel="stylesheet" href="css/nice-select.css">
<!-- slick CSS -->
<link rel="stylesheet" href="css/slick.css">
<!-- style CSS -->
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/style.css">
</head>

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
                                    <a class="nav_link" href="/user/login"><span>로그인</span></a>
                                    <a class="nav_link" href="/user/join"><span>회원가입</span></a>
                                  </div>
                                </div>
                      
                                <!-- 로그인 후 나오게 -->
                                <div id="header_nav_menuitem_on" style="display: none">
                                  <div class="header_nav_menuitem">
                                    <a class="nav_link" href="/logout"><span>로그아웃</span></a>
                                    <a class="nav_link"  href="#" id="mypage"><span>마이페이지</span></a>
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
                            <a class="navbar-brand" href="/"> <img src="/img/logo.png" alt="logo" style="width: 150px"> </a>
                            <button class="navbar-toggler" type="button" data-toggle="collapse"
                                data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                                aria-expanded="false" aria-label="Toggle navigation">
                                <span class="navbar-toggler-icon"></span>
                            </button>

                            <div class="collapse navbar-collapse main-menu-item justify-content-center"
                                id="navbarSupportedContent">
                                <ul class="navbar-nav">
                                    <li class="nav-item">
                                        <a class="nav-link" href="/map">여행 떠나기 </a>
                                    </li>

                                    <li class="nav-item">
                                        <a class="nav-link" href="article/travel">여행 정보 공유</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="article/hot">핫플레이스</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="article/notice">공지사항</a>
                                    </li>
                                </ul>
                            </div>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    <script>
        if (${not empty userinfo}) {
            // 로그인한 경우
            document.getElementById("header_nav_menuitem_off").setAttribute('style', 'display: none');
            document.getElementById("header_nav_menuitem_on").setAttribute('style', 'display: block');
        }
        else {
            // 로그인하지 않은 경우
            document.getElementById("header_nav_menuitem_off").setAttribute('style', 'display: block');
            document.getElementById("header_nav_menuitem_on").setAttribute('style', 'display: none');
        }
    </script>
    <script>
        document.getElementById("mypage").addEventListener("click", function(event) {
            event.preventDefault(); // 기본 동작(링크 이동)을 막습니다.

            Swal.fire({
                title: '내 정보',
                html: `
                    <div style="text-align: center;">
                    <p><strong>이름:</strong>${userinfo.userName}</p>
                    <p><strong>아이디:</strong> ${userinfo.userId}</p>
                    <p><strong>이메일:</strong> ${userinfo.emailId}@${userinfo.emailDomain}</p>
                    </div>
                `,
                focusConfirm: false,
                showCloseButton: true,
                showCancelButton: true,
                showDenyButton: true,
                confirmButtonColor: '#3085d6',
                denyButtonColor: '#3085d6',
                denyButtonText: `회원 탈퇴`,
                confirmButtonText: '수정',
                cancelButtonText: '취소',
                showLoaderOnConfirm: true}).then((result) => {
                if (result.isConfirmed) {
                    // 수정
                    location.href = "user/modify.jsp";
                } else if (result.isDenied) {
                    // 회원 탈퇴
                    withdraw("${root}");
                }
            });
        });
    </script>
    </header>
    <!-- Header part end-->



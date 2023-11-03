<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<c:set var="root" value="${pageContext.request.contextPath}"></c:set>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>여행가자</title>
    <base href="/" />
    <link rel="icon" href="assets/img/favicon.png">
    <link rel="stylesheet" type="text/css" href="${root}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${root}/assets/css/animate.css">
    <link rel="stylesheet" type="text/css" href="${root}/assets/css/owl.carousel.min.css">
    <link rel="stylesheet" type="text/css" href="${root}/assets/css/magnific-popup.css">
    <link rel="stylesheet" type="text/css" href="${root}/assets/css/gijgo.min.css">
    <link rel="stylesheet" type="text/css" href="${root}/assets/css/nice-select.css">
    <link rel="stylesheet" type="text/css" href="${root}/assets/css/slick.css">
    <link rel="stylesheet" type="text/css" href="${root}/assets/css/style.css">

    <!-- magnific CSS -->
    <link rel="stylesheet" href="${root}/assets/css/magnific-popup.css">
    <link rel="stylesheet" href="${root}/assets/css/gijgo.min.css">
    <!-- niceselect CSS -->
    <link rel="stylesheet" href="${root}/assets/css/nice-select.css">
    <!-- slick CSS -->
    <link rel="stylesheet" href="${root}/assets/css/slick.css">
    <!-- style CSS -->
    <link rel="stylesheet" href="${root}/assets/css/style.css">
    <link rel="stylesheet" href="${root}/assets/css/style.css">
    <script type="text/javascript" src="${root }/assets/js/join.js"></script>
    <script type="text/javascript" src="${root}/assets/js/member.js"></script>
    <script type="text/javascript" src="${root}/assets/js/message.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>

<%@ include file="./common/header.jsp" %>
<!-- banner part start-->
<section class="banner_part">
    <div class="container">
        <div class="row align-items-center justify-content-center">
            <div class="col-lg-10">
                <div class="banner_text text-center">
                    <div class="banner_text_iner">
                        <h1> 여행해 주영</h1>
                        <c:out value="${pageContext.request.contextPath}" />
                        <p>주영이와 함께 여행을 해주영 !</p>
                        <a href="map/map.jsp" class="btn_1">지금 바로 여행 준비하러 가기 Click</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- banner part start-->


<div id="footer"></div>

<!-- jquery plugins here-->
<script type="text/javascript" src="assets/js/jquery-1.12.1.min.js"></script>
<!-- popper js -->
<script type="text/javascript" src="assets/js/popper.min.js"></script>
<!-- bootstrap js -->
<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
<!-- magnific js -->
<script type="text/javascript" src="assets/js/jquery.magnific-popup.js"></script>
<!-- swiper js -->
<script type="text/javascript" src="assets/js/owl.carousel.min.js"></script>
<!-- masonry js -->
<script type="text/javascript" src="assets/js/masonry.pkgd.js"></script>
<!-- masonry js -->
<script type="text/javascript" src="assets/js/jquery.nice-select.min.js"></script>
<script type="text/javascript" src="assets/js/gijgo.min.js"></script>
<!-- contact js -->
<script type="text/javascript" src="assets/js/jquery.ajaxchimp.min.js"></script>
<script type="text/javascript" src="assets/js/jquery.form.js"></script>
<script type="text/javascript" src="assets/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="assets/js/mail-script.js"></script>
<script type="text/javascript" src="assets/js/contact.js"></script>
<!-- custom js -->
<script type="text/javascript" src="assets/js/custom.js"></script>
<script type="text/javascript" src="assets/js/join.js"></script>
<script type="text/javascript" src="assets/js/member.js"></script>

<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>window.onload = failMsg("${root}", "${msg}");</script>
<script>window.onload = infoMsg("${root}", "${info}");</script>
</body>
</html>

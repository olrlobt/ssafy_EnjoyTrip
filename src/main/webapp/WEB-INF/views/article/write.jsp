<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<c:set var="root" value="${pageContext.request.contextPath}"></c:set>
<c:set var="type" value="${requestScope.type}" />

<c:choose>
    <c:when test="${type == 'travel'}">
        <c:set var="name" value="여행"/>
    </c:when>
    <c:when test="${type == 'hot'}">
        <c:set var="name" value="핫플레이스"/>
    </c:when>
    <c:when test="${type == 'notice'}">
        <c:set var="name" value="공지사항"/>
    </c:when>
</c:choose>

<!DOCTYPE html>
<html lang="ko">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>여행가자</title>
    <link rel="icon" href="${root}/assets/img/favicon.png">
    <link rel="stylesheet" href="${root}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="${root}/assets/css/animate.css">
    <link rel="stylesheet" href="${root}/assets/css/owl.carousel.min.css">
    <link rel="stylesheet" href="fontawesome/${root}/assets/css/all.min.css">
    <link rel="stylesheet" href="${root}/assets/css/magnific-popup.css">
    <link rel="stylesheet" href="${root}/assets/css/gijgo.min.css">
    <link rel="stylesheet" href="${root}/assets/css/nice-select.css">
    <link rel="stylesheet" href="${root}/assets/css/slick.css">
    <link rel="stylesheet" href="${root}/assets/css/style.css">
    <script type="text/javascript" src="${root}/assets/js/join.js"></script>
    <script type="text/javascript" src="${root}/assets/js/member.js"></script>
    <script type="text/javascript" src="${root}/assets/js/article.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>

    <!-- header -->
    <%@ include file="/common/header.jsp" %>

    <div class="container">
        <header id="article-form-header" class="py-5 text-center">
            <h1> ${name} 게시글 작성</h1>
        </header>

        <form id="article-form" action="" method="post" accept-charset="UTF-8">
            <div class="row mb-3 justify-content-md-center">
                <label for="title" class="col-sm-2 col-lg-1 col-form-label text-sm-end">제목</label>
                <div class="col-sm-8 col-lg-9">
                    <input type="text" class="form-control" id="title" name="title" required>
                </div>
            </div>
            <div class="row mb-3 justify-content-md-center">
                <label for="content" class="col-sm-2 col-lg-1 col-form-label text-sm-end">본문</label>
                <div class="col-sm-8 col-lg-9">
                    <textarea class="form-control" id="content" name="content" rows="5" required></textarea>
                </div>
            </div>

            <div class="row mb-5 justify-content-md-center">
                <div class="col-sm-10 d-grid gap-2 d-sm-flex justify-content-sm-end">
                    <button type="button" class="btn btn-primary" id="submit-button">저장</button>
                    <a href="${root }/article?action=list&type=${type}" class="btn btn-secondary">취소</a>
                </div>
            </div>
        </form>
    </div>

    <!-- footer part start-->
    <div id="footer"></div>    
    <!-- footer part end-->
    <!-- jquery plugins here-->
    <script src="${root}/assets/js/jquery-1.12.1.min.js"></script>
    <!-- popper js -->
    <script src="${root}/assets/js/popper.min.js"></script>
    <!-- bootstrap js -->
    <script src="${root}/assets/js/bootstrap.min.js"></script>
    <!-- magnific js -->
    <script src="${root}/assets/js/jquery.magnific-popup.js"></script>
    <!-- swiper js -->
    <script src="${root}/assets/js/owl.carousel.min.js"></script>
    <!-- masonry js -->
    <script src="${root}/assets/js/masonry.pkgd.js"></script>
    <!-- masonry js -->
    <script src="${root}/assets/js/jquery.nice-select.min.js"></script>
    <script src="${root}/assets/js/gijgo.min.js"></script>
    <!-- contact js -->
    <script src="${root}/assets/js/jquery.ajaxchimp.min.js"></script>
    <script src="${root}/assets/js/jquery.form.js"></script>
    <script src="${root}/assets/js/jquery.validate.min.js"></script>
    <script src="${root}/assets/js/mail-script.js"></script>
    <script src="${root}/assets/js/contact.js"></script>
    <!-- custom js -->
    <script src="${root}/assets/js/custom.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>

    <script>
        document.querySelector("#submit-button").addEventListener("click", function () {
            if (!document.querySelector("#title").value) {
                alert("제목 입력!!");
                return;
            } else if (!document.querySelector("#content").value) {
                alert("내용 입력!!");
                return;
            } else {
                let form = document.querySelector("#article-form");
                form.setAttribute("action", "${root }/article?action=write&type=${type}");
                form.submit();
            }
        });


    </script>

</body>
</html>
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


<!doctype html>
<html lang="en">

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


<%@ include file="/common/header.jsp" %>


<!-- main start -->
<section class="top-article">
    <div class="container">
        <div class="row">
            <div class="card card-margin search-form">
                <div class="card-body p-0">
                    <form id="search-form">
                        <div class="row">
                            <div class="col-12">
                                <div class="row no-gutters">
                                    <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                        <label for="search-type" hidden>검색 유형</label>
                                        <select class="form-control" id="search-type" name="searchType">
                                            <option>제목</option>
                                            <option>내용</option>
                                            <option>아이디</option>
                                        </select>
                                    </div>
                                    <div class="col-lg-8 col-md-6 col-sm-12 p-0">
                                        <label for="search-value" hidden>검색어</label>
                                        <input type="text" placeholder="검색어..." class="form-control" id="search-value" name="searchValue">
                                    </div>
                                    <div class="col-lg-1 col-md-3 col-sm-12 p-0">
                                        <button type="submit" class="btn btn-base">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search">
                                                <circle cx="11" cy="11" r="8"></circle>
                                                <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                                            </svg>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="row">
            <table class="table" id="article-table">
                <thead>
                <tr>
                    <th class="id"><a>글 번호</a></th>
                    <th class="title col-6"><a>제목</a></th>
                    <th class="user-id"><a>작성자</a></th>
                    <th class="created-at"><a>작성일</a></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="board" items="${requestScope.articles}">
                    <tr>
                        <td>${board.articleNo}</td>
                        <td><a href="${root }/article?action=view&article_no=${board.articleNo}&type=${type}">${board.subject}</a></td>
                        <td>${board.userId}</td>
                        <td>${board.registerTime}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div>
            <div class="row">
                <div class="d-flex justify-content-end gap-2">
                    <a class="btn btn-primary me-2" role="button" id="write-article" href="${root }/article?action=writeForm&type=${type}"> ${name} 글쓰기</a>
                </div>
            </div>


            <div class="row">
                <nav id="pagination" aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="${root }/article?action=list&currentPage=${currentPage - 1}&type=${type}">Previous</a>
                            </li>
                        </c:if>
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item <c:if test="${i == currentPage}">active</c:if>">
                                <a class="page-link" href="${root }/article?action=list&currentPage=${i}&type=${type}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="${root }/article?action=list&currentPage=${currentPage + 1}&type=${type}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</section>
<!-- main end -->

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

<script>



</script>



</body>

</html>
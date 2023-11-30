<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath }"></c:set>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
            crossorigin="anonymous"
    />
    <link href="${root}/assets/css/app.css" rel="stylesheet"/>
    <script type="text/javascript" src="${root}/assets/js/member.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <title>여행가자</title>
</head>
<%@ include file="../common/header.jsp" %>
<body>
<c:if test="${not empty userinfo }">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-8 col-md-10 col-sm-12">
                <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                    <mark class="orange">회원정보 수정</mark>
                </h2>
            </div>
            <div class="col-lg-8 col-md-10 col-sm-12">
                <form id="form-modify" method="POST" action="">
                    <input type="hidden" name="action" value="modify">
                    <div class="mb-3">
                        <label for="username" class="form-label">이름 : </label>
                        <input
                                type="text"
                                class="form-control"
                                id="username"
                                name="username"
                                placeholder="이름..."
                                value="${userinfo.userName }"
                        />
                    </div>
                    <div class="mb-3">
                        <label for="userid" class="form-label">아이디 : </label>
                        <input
                                type="text"
                                class="form-control"
                                id="userid"
                                name="userid"
                                placeholder="아이디..."
                                value="${userinfo.userId}"
                        />
                    </div>
                    <div id="result-view" class="mb-3"></div>
                    <div class="mb-3">
                        <label for="emailid" class="form-label">이메일 : </label>
                        <div class="input-group">
                            <input
                                    type="text"
                                    class="form-control"
                                    id="emailid"
                                    name="emailid"
                                    placeholder="이메일아이디"
                                    value="${userinfo.emailId}"
                            />
                            <span class="input-group-text">@</span>
                            <select
                                    class="form-select"
                                    id="emaildomain"
                                    name="emaildomain"
                                    aria-label="이메일 도메인 선택"
                            >
                                <option selected>선택</option>
                                <option value="ssafy.com">싸피</option>
                                <option value="google.com">구글</option>
                                <option value="naver.com">네이버</option>
                                <option value="kakao.com">카카오</option>
                            </select>
                        </div>
                    </div>
                    <!--
                    <div class="mb-3">
                      <label for="sido" class="form-label">지역 : </label>
                      <div class="input-group">
                        <select class="form-select me-1" id="sido" aria-label="시도">
                          <option selected>시도선택</option>
                          <option value="1100000000">서울특별시</option>
                          <option value="2200000000">경기도</option>
                          <option value="3300000000">강원도</option>
                          <option value="4400000000">충청도</option>
                        </select>
                        <select class="form-select" id="gugun" aria-label="구군">
                          <option selected>구군선택</option>
                          <option value="1111000000">종로구</option>
                          <option value="1112000000">중구</option>
                          <option value="1113000000">서초구</option>
                          <option value="1114000000">강남구</option>
                        </select>
                      </div>
                    </div>
                     -->
                    <div class="col-auto text-center">
                        <button type="button" id="btn-modify" class="btn btn-outline-primary mb-3">
                            회원정보 수정
                        </button>
                        <button type="button" id="btn-reset-password" class="btn btn-outline-success mb-3">비밀번호 수정
                        </button>
                        <button type="button" id="btn-mv-home" class="btn btn-outline-success mb-3">홈으로</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</c:if>
<c:if test="${empty userinfo }">
    <script>
        mvlogin("${root}");
    </script>
</c:if>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"
></script>
<script>
    // 비동기 id check
    let isUseId = false;
    let input = document.querySelector("#userid");
    let resultDiv = document.querySelector("#result-view");
    input.addEventListener("keyup", function () {
        let checkid = input.value;
        let len = checkid.length;
        if (len < 4 || len > 16) {
            isUseId = false;
            resultDiv.setAttribute("class", "mb-3 fw-bold text-dark");
            resultDiv.innerHTML = "아이디는 4자이상 16자이하입니다.";
        } else if (input == ${userinfo.userId}) {
        } else {
            let url = "${root}/user?action=idcheck&checkid=" + checkid;

            // CSV
            fetch(url)
                .then((response) => response.text())
                .then((data) => resultViewText(data));
        }
    });

    function resultViewText(data) {
        let val = data.split(",");
        let id = val[0];
        let cnt = val[1];
        if (cnt == 0) {
            isUseId = true;
            resultDiv.setAttribute("class", "mb-3 text-success");
            resultDiv.innerHTML = "<span class='fw-bold'>" + id + "</span>은 사용할 수 있습니다.";
        } else {
            isUseId = false;
            resultDiv.setAttribute("class", "mb-3 text-danger");
            resultDiv.innerHTML = "<span class='fw-bold'>" + id + "</span>은 사용할 수 없습니다.";
        }
    }

    document.querySelector("#btn-mv-home").addEventListener("click", function () {
        location.href = "${root}/index.jsp";
    });

</script>
<script>
    modify("${root }/user");
    resetPassword("${root}/user");
</script>
</body>
</html>

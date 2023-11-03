<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath }"></c:set>

<c:if test="${not empty article }">
    <!DOCTYPE html>
    <html lang="ko">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link
                href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
                rel="stylesheet"
                integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
                crossorigin="anonymous"
        />
        <link href="${root }/assets/css/app.css" rel="stylesheet" />
        <title>SSAFY</title>


        <style>
            .avatar {
                width: 40px;
                height: 40px;
            }
            .divider {
                border-bottom: 1px solid #eaeaea;
            }
            .sky {
                background-color: #cce5ff;
                padding: .2em .6em .3em;
            }
        </style>
    </head>
    <body>

    <%@ include file="/common/header.jsp" %>

    <div class="container my-5">
        <div class="row justify-content-center">

            <div class="col-lg-8 col-md-10 col-sm-12">
                <div class="card shadow p-4 mb-4 bg-white">


                    <div class="my-2">
                        <h2 class="text-secondary">${article.articleNo}. ${article.subject}</h2>
                    </div>

                    <div class="row mb-3">
                        <div class="col-md-8 d-flex align-items-center">
                            <img class="avatar me-2 bg-light p-2" src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg" />
                            <div>
                                <p class="mb-0 fw-bold">${article.userId}</p>
                                <p class="text-secondary fw-light mb-0">${article.registerTime} 조회 : ${article.hit}</p>
                            </div>
                        </div>
                    </div>

                    <div class="divider mb-3"></div>
                    <div class="text-secondary mb-3">
                            ${article.content}
                    </div>
                    <div class="divider mt-3 mb-3"></div>
                    <div class="d-flex justify-content-end">
                        <button type="button" id="btn-list" class="btn btn-outline-primary mb-3">글목록</button>
                        <button type="button" id="btn-mv-modify" class="btn btn-outline-success mb-3 ms-1">글수정</button>
                        <button type="button" id="btn-delete" class="btn btn-outline-danger mb-3 ms-1">글삭제</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
    <script>
        document.querySelector("#btn-list").addEventListener("click", function () {
            location.href = "${root }/article?action=list&type=${type}";
        });
        document.querySelector("#btn-mv-modify").addEventListener("click", function () {
            location.href = "${root }/article?action=mvModify&article_no=${article.articleNo}&type=${type}";
        });
        document.querySelector("#btn-delete").addEventListener("click", function () {
            location.href = "${root }/article?action=delete&article_no=${article.articleNo}&type=${type}";
        });
    </script>

    </body>

    </html>
</c:if>

<c:if test="${empty article }">
    <script>
        alert("글이 삭제되었거나 부적절한 URL 접근입니다.");
        location.href = "/board/board/list.jsp";
    </script>
</c:if>

function signup(path) {
    document.querySelector("#btn-join").addEventListener("click", function () {
        if (!document.querySelector("#username").value ||
            !document.querySelector("#userid").value ||
            !document.querySelector("#userpwd").value) {
            Swal.fire({
                title: '입력이 부족합니다.',
                text: "이름, 아이디, 비밀번호는 필수입니다.",
                icon: 'error',
                focusConfirm: false,
                confirmButtonText: '확인',
                allowOutsideClick: false,
                showLoaderOnConfirm: true
            });
            return;
        } else if (document.querySelector("#userpwd").value != document.querySelector("#pwdcheck").value) {
            Swal.fire({
                title: '비밀번호 확인',
                text: "비밀번호가 일치하지 않습니다.",
                icon: 'error',
                focusConfirm: false,
                confirmButtonText: '확인',
                allowOutsideClick: false,
                showLoaderOnConfirm: true
            });
            return;
        } else {
            let form = document.querySelector("#form-join");
            form.setAttribute("action", path);
            form.submit();
        }
    });
}

function mvJoin(root) {
    document.querySelector("#btn-mv-join").addEventListener("click", function () {
        location.href = root + "/user/join.jsp";
    });
}



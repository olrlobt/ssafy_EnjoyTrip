function login(path) {
    // 로그인
    document.querySelector("#btn-login").addEventListener("click", function () {
        if (!document.querySelector("#userid").value || !document.querySelector("#userpwd").value) {
            Swal.fire({
                title: '로그인 실패',
                html: `아이디, 비밀번호를 입력해주세요.`,
                icon: 'error',
                focusConfirm: false,
                showCloseButton: true,
                confirmButtonColor: '#3085d6',
            });
            return;
        } else {
            let form = document.querySelector("#form-login");
            form.setAttribute("action", path);  // 서블릿 요청 action
            form.submit();
        }
    });
}

function modify(path) {
    document.querySelector("#btn-modify").addEventListener("click", function () {
        if (!document.querySelector("#username").value ||
            !document.querySelector("#userid").value ||
            (document.querySelector("#emailid").value &&
            document.querySelector("#emaildomain").value == "선택")) {
            Swal.fire({
                title: '입력이 부족합니다',
                html: `빈 칸으로 수정할 수 없습니다.`,
                icon: 'error',
                focusConfirm: false,
                showCloseButton: true,
                confirmButtonColor: '#3085d6'
            });
            return;
        } else {
            let form = document.querySelector("#form-modify");
            form.setAttribute("action", path);
            form.submit();
        }
    });
}

function mvlogin(root) {
    Swal.fire({
        title: '로그인이 필요합니다.',
        html: `로그인이 필요한 서비스입니다.`,
        icon: 'error',
        focusConfirm: false,
        showCloseButton: true,
        confirmButtonColor: '#3085d6',
    }).then(() => {
            location.href = root + "/user?action=mvlogin";
        });
}

function withdraw(root) {
    Swal.fire({
        title: '회원 탈퇴하기',
        text: '회원 탈퇴를 진행하시겠습니까?',
        icon: 'info',
        focusConfirm: false,
        confirmButtonText: '탈퇴하기',
        showCloseButton: true,
        showCancelButton: true,
        cancelButtonText: `취소`,
        allowOutsideClick: false,
        showLoaderOnConfirm: true}).then(
            (result) => {
                if(result.isConfirmed) {
                    let url = root + "/user?action=withdraw";
                    // CSV
                    fetch(url)
                        .then(() => Swal.fire({
                            title: '회원 탈퇴성공',
                            text: "이용해주셔서 감사합니다.",
                            icon: 'info',
                            focusConfirm: false,
                            confirmButtonText: '확인',
                            allowOutsideClick: false,
                            showLoaderOnConfirm: true}).then(() => {
                                location.href = root + "/index.jsp";
                            }));
                }
            }
        );
}

function resetPassword(path) {
    // 비밀번호 재설정
    document.querySelector("#btn-reset-password").addEventListener("click", function () {
        Swal.fire({
            title: '비밀번호 재설정',
            html: `
          <form id="form-reset-password" method="POST" action="">
            <input type="hidden" name="action" value="modifyPass">
            <input type="password" id="userpwd" name="userpwd" class="swal2-input" placeholder="변경할 비밀번호를 입력해주세요" required>
          </form>`,
            focusConfirm: false,
            confirmButtonText: '확인',
            showCloseButton: true,
            allowOutsideClick: false,
            showLoaderOnConfirm: true
        }).then((result) => {
            if (result.isConfirmed) {
                let form = document.querySelector("#form-reset-password");
                form.setAttribute("action", path);  // 서블릿 요청 action
                form.submit();
            }
        });
    });
}
function save() {
    Swal.fire({
        title: '비밀번호 찾기',
        html:
            '<input id="username" class="swal2-input" placeholder="이름을 입력해주세요" required>' +
            '<input id="id" class="swal2-input" placeholder="아이디를 입력해주세요" required>',
        focusConfirm: false,
        confirmButtonText: '확인',
        showCloseButton: true,
        showCancelButton: true,
        cancelButtonText: `이전`,
        allowOutsideClick: false,
        showLoaderOnConfirm: true
    }).then((result) => {
        if (result.isConfirmed) {
            var username = document.getElementById('username').value;
            var id = document.getElementById('id').value;

            var info = localStorage.getItem('user');
            if (info) {
                var user = JSON.parse(info);
                if (user.id === id && user.user_name === username) {
                    return resetPassword();
                } else {
                    Swal.fire({
                        title: '비밀번호 찾기 실패',
                        html: `이름 또는 아이디가 일치하지 않습니다.`,
                        icon: 'error',
                        focusConfirm: false,
                        showCloseButton: true,
                        confirmButtonColor: '#3085d6',
                        preConfirm: () => {
                            return findPassword();
                        }
                    });
                }
            } else {
                Swal.fire('회원이 아닙니다', '메인 페이지에서 회원가입을 먼저 해주세요', 'error');
            }
        } else if (result.isDismissed) {
            if (result.dismiss === Swal.DismissReason.close || result.dismiss === Swal.DismissReason.esc) return; // 닫기 버튼
            return login();
        }
    });
}
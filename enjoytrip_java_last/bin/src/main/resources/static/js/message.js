function failMsg(root, msg) {
    if (msg) {
        Swal.fire({
            title: '잘못된 요청',
            html: msg,
            icon: 'error',
            focusConfirm: false,
            showCloseButton: true,
            confirmButtonColor: '#3085d6'
        }).then(() => {
            let url = root + "/message?action=home";
            // CSV
            fetch(url)
                .then(() => location.href = root);
        });
    }
}

function infoMsg(root, msg) {
    if (msg) {
        Swal.fire({
            title: '성공',
            html: msg,
            icon: 'info',
            focusConfirm: false,
            showCloseButton: true,
            confirmButtonColor: '#3085d6'
        }).then(() => {
            let url = root + "/message?action=home";
            // CSV
            fetch(url)
                .then(() => location.href = root);
        });
    }
}

function failLogin(root, msg) {
    // 로그인 실패 시 alert
    if (msg) {
        Swal.fire({
            title: '로그인 실패',
            html: msg,
            icon: 'error',
            focusConfirm: false,
            showCloseButton: true,
            confirmButtonColor: '#3085d6'
        }).then(() => {
            let url = root + "/message?action=login";
            // CSV
            fetch(url)
                .then(() => location.href = root + "/user/login.jsp");
        });
    }
}
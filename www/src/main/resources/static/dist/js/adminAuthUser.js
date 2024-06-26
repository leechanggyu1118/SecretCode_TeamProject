console.log("hihi");

document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.authBtn').forEach(button => {
        button.addEventListener('click', () => {
            const row = button.closest('tr');
            const email = row.getAttribute('data-email');
            const action = row.querySelector('.roleAction').value;

            if (action === 'grant') {
                if (confirm("관리자 권한을 부여하시겠습니까?")) {
                    grantRoleToServer(email).then(result => {
                        console.log(result);
                        if (result === '1') {
                            alert("관리자 권한이 부여되었습니다");
                            window.location.reload();
                        } else if (result === 'YES_ROLE') {
                            alert("이미 권한이 부여된 계정입니다.");
                        }
                    }).catch(err => {
                        console.log(err);
                    });
                }
            } else if (action === 'revoke') {
                if (confirm("관리자 권한을 정말 회수하시겠습니까?")) {
                    revokeRoleFromServer(email).then(result => {
                        console.log(result);
                        if (result === '1') {
                            alert("관리자 권한이 회수되었습니다");
                            window.location.reload();
                        } else if (result === 'NO_ROLE') {
                            alert("회수할 관리자 권한이 없습니다.");
                        }
                    }).catch(err => {
                        console.log(err);
                    });
                }
            }
        });
    });
});

async function grantRoleToServer(email) {
    try {
        const url = `/adminRegister/auth/grant/${email}`;
        const config = {
            method: "POST",
            headers: {
                "Content-Type": "application/json; charset=utf-8"
            }
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

async function revokeRoleFromServer(email) {
    try {
        const url = `/adminRegister/auth/revoke/${email}`;
        const config = {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json; charset=utf-8"
            }
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

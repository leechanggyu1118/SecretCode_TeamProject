console.log('hi');

document.querySelectorAll('.delBtn').forEach(button => {
    button.addEventListener('click', (e) => {
        const memberRow = e.target.closest('tr');
        const memberEmail = memberRow.querySelector('td[data-title="Email"]').innerText;

        if (memberEmail) {
            if (confirm('계정을 탈퇴시키겠습니까?')) {
                deleteUserToServer(memberEmail).then(result => {
                    if (result === '1') {
                        alert('탈퇴가 완료되었습니다.');
                        location.reload();
                    } else {
                        alert('탈퇴 실패');
                    }
                });
            }
        }
    });
});

async function deleteUserToServer(email) {
    try {
        const url = `http://localhost:8888/adminRegister/delete/${email}`;
        const config = {
            method: 'delete',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            }
        };

        const response = await fetch(url, config);
        return await response.text();
    } catch (error) {
        console.error('Error:', error);
    }
}

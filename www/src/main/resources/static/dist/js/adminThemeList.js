console.log("들어왔따.");

document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.deleteBtn').forEach(button => {
        button.addEventListener('click', (e) => {

            const themeNum = button.getAttribute('data-themenum');

            const n = e.target.dataset.themenum;
            console.log(themeNum);
            console.log(n);

                if (confirm('테마를 삭제시키겠습니까?')) {
                    deleteThemeToServer(themeNum).then(result => {
                        console.log(result);
                        if (result == "1") {
                            alert('삭제가 완료되었습니다.');
                            console.log(result);
                            location.reload();
                        } else {
                            alert('테마 삭제 실패');
                            console.log(result);
                            
                        }
                    });
                }


            
        });
    });
});



async function deleteThemeToServer(themeNum) {
    try {
        const url = `http://localhost:8888/adminRegister/deleteTheme/${themeNum}`;
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
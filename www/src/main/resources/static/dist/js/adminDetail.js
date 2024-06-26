console.log("hihihi");

document.getElementById('deleteBtn').addEventListener('click', () => {
    const qnaId = document.getElementById('qnaId').value;

    if (confirm("정말 게시글을 삭제하시겠습니까?")) {
        deleteQnaFromServer(qnaId).then(result => {
            console.log(result);
            if (result === '1') {
                alert("삭제되었습니다");
                window.location.href = "/adminRegister/adminBoard";
            }
        }).catch(err => {
            console.log("에러:", err);
        });
    }
});

async function deleteQnaFromServer(bno) {
    try {
        const url = `/admin/${bno}`;
        const config = {
            method: "delete",
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

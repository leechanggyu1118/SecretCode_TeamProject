document.addEventListener('DOMContentLoaded', () => {
    console.log("boardDetailComment in");

    const bnoVal = document.getElementById('qnaId').value;
    console.log(bnoVal);

    document.getElementById('cmtAddBtn').addEventListener('click', () => {
        const cmtText = document.getElementById('cmtText').value;
        const cmtWriter = '관리자';

        if (!cmtText) {
            alert("댓글을 입력해주세요.");
            document.getElementById('cmtText').focus();
            return false;
        }

        const cmtData = {
            bno: bnoVal,
            writer: cmtWriter,
            content: cmtText
        };

        console.log(cmtData);

        postCommentToServer(cmtData).then(result => {
            console.log(result);
            if (result === '1') {
                alert('댓글 등록 성공!!');
                window.location.reload();
            }
        });
    });

    async function postCommentToServer(cmtData) {
        try {
            const url = "/adminComment/post";
            const config = {
                method: "post",
                headers: {
                    "content-type": "application/json; charset=utf-8"
                },
                body: JSON.stringify(cmtData)
            };

            const resp = await fetch(url, config);
            const result = await resp.text();
            return result;
        } catch (error) {
            console.log(error);
        }
    }

    function spreadCommentList(bno) {
        getCommentListFromServer(bno).then(result => {
            console.log(result);
            const div = document.getElementById('commentList');
            if (result.length > 0) {
                div.innerHTML = "";
                result.forEach(comment => {
                    let add = `<div class="comment-item">`;
                    add += `<p>no.${comment.cno} / ${comment.writer} / ${comment.regAt}</p>`;
                    add += `<p>${comment.content}</p>`;
                    add += `<input type="text" class="form-control cmtText" value="${comment.content}">`;
                    add += `<button type="button" data-cno="${comment.cno}" class="btn btn-outline-warning btn-sm cmtModBtn">수정</button>`;
                    add += `<button type="button" data-cno="${comment.cno}" class="btn btn-outline-danger btn-sm cmtDelBtn">삭제</button>`;
                    add += `</div>`;

                    div.innerHTML += add;
                    document.getElementById('cmtText').innerText = '';
                });
            } else {
                div.innerHTML = `<div class="comment-item"> Comment List Empty </div>`;
            }
        });
    }

    async function getCommentListFromServer(bno) {
        try {
            const resp = await fetch(`/adminComment/${bno}/comments`);
            const result = await resp.json();
            return result;
        } catch (error) {
            console.log(error);
        }
    }

    async function updateCommentToServer(cmtData) {
        try {
            const url = "/adminComment/modify";
            const config = {
                method: "put",
                headers: {
                    "content-type": "application/json; charset=utf-8"
                },
                body: JSON.stringify(cmtData)
            };

            const resp = await fetch(url, config);
            const result = await resp.text();
            return result;
        } catch (error) {
            console.log(error);
        }
    }

    async function removeCommentFromServer(cno, bno) {
        try {
            const url = `/adminComment/${cno}/${bno}`;
            const config = {
                method: "delete"
            };

            const resp = await fetch(url, config);
            const result = await resp.text();
            return result;
        } catch (error) {
            console.log(error);
        }
    }

    spreadCommentList(bnoVal);

    document.addEventListener('click', (e) => {
        console.log(e.target);

        if (e.target.classList.contains('cmtModBtn')) {
            const cnoVal = e.target.getAttribute('data-cno');
            const div = e.target.closest('div');
            const cmtText = div.querySelector('.cmtText').value;

            const cmtData = {
                cno: cnoVal,
                content: cmtText
            };

            updateCommentToServer(cmtData).then(result => {
                if (result === '1') {
                    alert('댓글 수정 성공!!');
                    spreadCommentList(bnoVal);
                }
            });
        }

        if (e.target.classList.contains('cmtDelBtn')) {
            const cnoVal = e.target.getAttribute('data-cno');
            removeCommentFromServer(cnoVal, bnoVal).then(result => {
                if (result === '1') {
                    alert('댓글 삭제 성공!!');
                    window.location.reload();
                }
            });
        }
    });
});

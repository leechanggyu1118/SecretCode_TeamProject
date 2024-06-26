
    function likeReview(button) {
        var bno = button.getAttribute('data-bno');
        if (!bno) {
            console.error('Bno is null');
            return;
        }
        // 좋아요 버튼을 비활성화합니다.
        button.disabled = true;
        // 해당 버튼의 형제 요소인 싫어요 버튼을 활성화합니다.
        button.nextElementSibling.disabled = false;

        console.log(`likeCountSpan_${bno}`); // 콘솔 출력

        fetch('/review/like/' + bno, {
            method: 'POST'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // 서버 응답에 따라 버튼 텍스트 업데이트
            var likeText = '👍';
            button.textContent = likeText;
            // 해당 리뷰의 공감수를 업데이트합니다.
            document.getElementById(`likeCountSpan_${bno}`).innerText = '공감수: ' + data.likes;
        })
        .catch(error => console.error('Error:', error));
    }

    function dislikeReview(button) {
        var bno = button.getAttribute('data-bno');
        if (!bno) {
            console.error('Bno is null');
            return;
        }
        // 싫어요 버튼을 비활성화합니다.
        button.disabled = true;
        // 해당 버튼의 형제 요소인 좋아요 버튼을 활성화합니다.
        button.previousElementSibling.disabled = false;

        console.log(`likeCountSpan_${bno}`); // 콘솔 출력

        fetch('/review/dislike/' + bno, {
            method: 'POST'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // 서버 응답에 따라 버튼 텍스트 업데이트
            var dislikeText = '👎';
            button.textContent = dislikeText;
            // 해당 리뷰의 공감수를 업데이트합니다.
            document.getElementById(`likeCountSpan_${bno}`).innerText = '공감수: ' + data.likes;
        })
        .catch(error => console.error('Error:', error));
    }
function confirmDelete() {
    // 삭제 전에 확인 알림창을 띄웁니다.
    let a = confirm('정말로 삭제하시겠습니까?');
    console.log(a);
    if (a) {
        // 사용자가 확인을 선택한 경우에만 true를 반환합니다.
        // 여기에 삭제를 처리하는 코드를 작성합니다.
        console.log('사용자가 확인을 선택했습니다.');
        // 여기에 삭제를 처리하는 로직을 추가할 수 있습니다.
    } else {
        // 사용자가 취소를 선택한 경우에 대한 처리를 할 수 있습니다.
        console.log('사용자가 취소를 선택했습니다.');
    }
}
function confirmUpdate() {
    // 수정 전에 확인 알림창을 띄웁니다.
    if (confirm('정말로 수정하시겠습니까?')) {
        // 사용자가 확인을 선택한 경우에만 true를 반환합니다.
        // 여기에 수정을 처리하는 코드를 작성합니다.
        console.log('사용자가 확인을 선택했습니다.');
        // 여기에 수정을 처리하는 로직을 추가할 수 있습니다.
    } else {

        // 사용자가 취소를 선택한 경우에 대한 처리를 할 수 있습니다.
        console.log('사용자가 취소를 선택했습니다.');
    }
}
  function submitForm() {
        document.getElementById('themeForm').submit();
    }

    document.addEventListener("DOMContentLoaded", function() {
        var editButtons = document.querySelectorAll('.ModBtn'); // 수정 버튼
        var titleInputs = document.querySelectorAll('.card-title input'); // 제목 입력 필드
        var contentInputs = document.querySelectorAll('.card-text textarea'); // 내용 입력 필드

        editButtons.forEach(function(button, index) {
            button.addEventListener('click', function(event) {
                // 해당 리뷰의 제목 입력 필드와 내용 입력 필드를 수정 가능하도록 설정
                titleInputs[index].readOnly = false;
                contentInputs[index].readOnly = false;
            });
        });
    });

    document.getElementById('review-write').addEventListener('click', function() {
            var isAuthenticated = this.getAttribute('data-authenticated');

            if (isAuthenticated === 'true') {
                // 인증된 경우 원하는 작업 수행
                 window.location.href = '/review/register';
                // 여기에 실제 리뷰 작성 폼을 열거나 다른 동작을 수행하는 코드를 추가할 수 있습니다.
            } else {
                // 미인증 사용자에게 로그인 요구
                var confirmLogin = confirm('리뷰를 작성하려면 로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?');

                if (confirmLogin) {
                    window.location.href = '/member/login_register'; // 로그인 페이지로 이동
                } else {
                  window.location.href = '/review/list';
                }
            }
        });








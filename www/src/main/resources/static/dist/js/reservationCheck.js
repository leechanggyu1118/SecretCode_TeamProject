
document.addEventListener('DOMContentLoaded', () => {
    document.addEventListener('click', (e) => {
        
        
        if (e.target.classList.contains('refund-button')) {
            // 클래스 이름으로 요소 가져오기
            const numElement = document.querySelector('.reservationTb #num');
            const uidElement = document.querySelector('.reservationTb #uid');
            const priceElement = document.querySelector('.reservationTb #price');
            const dateElement = document.querySelector('.reservationTb #date');
            const timeElement = document.querySelector('.reservationTb #time');

            // 요소에서 텍스트 또는 내용 가져오기
            const reservationNum = numElement.textContent.trim(); // 혹은 innerText
            const merchantUid = uidElement.textContent.trim(); // 혹은 innerText
            const reservationPrice = priceElement.textContent.trim(); // 혹은 innerText
            const reservationDate = dateElement.textContent.trim();
            const reservationTimeText = timeElement.textContent.trim(); // 예약 시간을 텍스트로 가져옴

            console.log(reservationNum);
            console.log(merchantUid);
            console.log(reservationPrice);
            console.log(reservationDate);
            console.log(reservationTimeText);


            let checkCancelRev = document.getElementById('checkCancelRev');

            // 현재 시간 구하기
            const now = new Date();
            const nowTime = now.getTime(); // 현재 시간을 밀리초로 변환

            // 예약 시간을 Date 객체로 변환
            const [year, month, day] = reservationDate.split('-');
            const [hour, minute] = reservationTimeText.split(':');
            const reservedTime = new Date(year, month - 1, day, hour, minute);
            const reservedTimeMillis = reservedTime.getTime(); // 예약 시간을 밀리초로 변환

            // 예약 시간과 현재 시간의 차이 계산 (밀리초)
            const timeDiffMillis = reservedTimeMillis - nowTime;

            // 차이를 시간 단위로 변환
            const timeDiffHours = timeDiffMillis / (1000 * 60 * 60);

            // 예약 취소 가능 여부 확인
            if (timeDiffHours <= 24) {
                alert('예약 취소는 예약일 24시간 전까지만 가능합니다.');

                checkCancelRev.innerHTML = "";
                checkCancelRev.innerHTML = `<p class="text-danger">예약 취소가 불가능 합니다.</p>`;



                return; // 예약 취소 불가능
            }

            checkCancelRev.innerHTML = "";
    
            //환불로직 시작
            if (confirm('예약취소가 가능한 시간입니다. 정말로 환불처리를 하시겠습니까?')) {



            fetch(`/api/payments/${merchantUid}?refundAmount=${reservationPrice}`, { method: 'DELETE' })
                        .then(response => {
                            if (response.ok) {
                                alert('환불 처리가 성공적으로 완료되었습니다.');
                                
                                hwanbool(reservationNum).then(result => {
                                    console.log(result);
                                        alert('예약 취소가 성공적으로 완료되었습니다');
                                        window.location.reload();
                                    });
        
                            } else {
                                alert('환불 처리 중 오류가 발생했습니다.');
                                return;
                            }
                        })
                        .catch(error => {
                            console.error('환불 처리 중 에러 발생:', error);
                            console.log(error);
                            alert('환불 처리 중 에러가 발생했습니다.');
                            return;
                        });



            }else{
                alert('예약을 취소에 실패하였습니다.');
            }


        }
    });

      //db삭제
      async function hwanbool(reservationNum) {
        try {
            const url = `/portOnePay/${reservationNum}`;
            const config = {
                method: "delete",
                headers: {
                    "content-type": "application/json; charset=utf-8"
                }
            };

            const resp = await fetch(url, config);
            const result = await resp.text();
            return result;
        } catch (error) {
            console.log(error);
        }
    }



});


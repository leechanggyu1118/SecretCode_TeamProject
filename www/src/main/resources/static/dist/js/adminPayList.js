console.log('hi');


document.addEventListener('DOMContentLoaded', () => {
    document.addEventListener('click', (e) => {
        
        
        if (e.target.classList.contains('hwanboolBtn')) {
            const row = e.target.closest('tr');
            const reservationNum = row.cells[0].textContent;
            const merchantUid = row.cells[1].textContent;
            const reservationPrice = row.cells[9].textContent;
            
            console.log(merchantUid);
            console.log(reservationPrice);

            if (confirm('정말로 환불처리를 하시겠습니까?')) {


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
                            alert(error);
                            alert('환불 처리 중 에러가 발생했습니다.');
                            return ;
                        });


           




            }else{
                alert('예약을 취소에 실패하였습니다.');
            }


        }
    });


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



// function cancelPayment(merchantUid) {
//     if (confirm('정말 환불처리를 하시겠습니까?')) {

//         const merchantUid = button.getAttribute('data-merchantUid');
//         const reservationPrice = button.getAttribute('data-reservationPrice');



//         fetch(`/api/payments/${merchantUid}?refundAmount=${reservationPrice}`, { method: 'DELETE' })
//             .then(response => {
//                 if (response.ok) {
//                     alert('환불 처리가 성공적으로 완료되었습니다.');

 
//             hwanbool(reservationNum).then(result => {
//                 console.log(result);
//                     alert('환불 처리가 성공적으로 완료되었습니다');
//                     window.location.reload();
//                 });
//                 alert('환불을 취소하였습니다.');
//                     window.location.reload();


                    
//                 } else {
//                     alert('환불 처리 중 오류가 발생했습니다.');
//                 }
//             })
//             .catch(error => {
//                 console.error('환불 처리 중 에러 발생:', error);
//                 alert('환불 처리 중 에러가 발생했습니다.');
//             });
//     }
// }


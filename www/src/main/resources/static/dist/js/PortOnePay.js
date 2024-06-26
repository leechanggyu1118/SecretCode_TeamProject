//전화번호 자동 하이픈 생성
const autoHyphen2 = (target) => {
    target.value = target.value
      .replace(/[^0-9]/g, '')
     .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
   }
   


var IMP = window.IMP;
IMP.init("imp40772235");


function calculatePrice() {
    var selectElement = document.getElementById("participants");
    var selectedValue = selectElement.value;
    console.log(selectedValue);
    var price = 0;

    // 인원수에 따라 가격 설정
    switch(selectedValue) {
        case "1":
            price = 25000;
            break;
        case "2":
            price = 46000;
            break;
        case "3":
            price = 60000;
            break;
        case "4":
            price = 72000;
            break;
        case "5":
            price = 85000;
            break;
        case "6":
            price = 90000;
            break;
        default:
            price = 25000;
    }

    // 가격을 화면에 표시
    document.getElementById("price").value = price;
}

document.getElementById("participants").addEventListener('change',()=>{
// 페이지 로드시 초기화
calculatePrice();
});

 //결제 후 예약 DB 작업 비동기 메서드
 async function reservationToServer(merchantUid, date, time, theme, name, phone, email, participants, price){
    try {
        const url = "/portOnePay/reservation/"+merchantUid+"/"+date+"/"+time+"/"+theme+"/"+name+"/"+phone+"/"+email+"/"+participants+"/"+price;
        const resp = await fetch(url);
        const result = await resp.text();

        return result;
    } catch (error) {
        console.log(error);
    }
  }


  //결제/예약 후 메일보내기 메서드
  async function reservationEmailToServer(email, merchantUid){
    try {
        const url = "/portOnePay/reservationEmail/"+email+"/"+merchantUid;
        const resp = await fetch(url);
        const result = await resp.text();

        return result;
    } catch (error) {
        console.log(error);
    }
  }

  function generateMerchantUid(date, time, theme) {
    var randomChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var length = 6;
    var randomString = '';
    for (var i = 0; i < length; i++) {
        randomString += randomChars.charAt(Math.floor(Math.random() * randomChars.length));
    }
    var timestamp = new Date().getTime();
    var merchantUid = date + time + theme + randomString + timestamp; // 시간 정보 추가
    return merchantUid;
}

function requestPay() {
    let date = document.getElementById('date').value; //예약일
    let time = document.getElementById('time').value; //시간
    let theme = document.getElementById('theme').value; //테마명
    let name = document.getElementById('name').value; //예약자
    let phone = document.getElementById('phone').value; //휴대폰번호
    let email = document.getElementById('email').value; //이메일
    let participants = document.getElementById('participants').value; //인원수
    let price = document.getElementById('price').value; //가격

        // 필수 입력 필드 검증
        if (!name || name.trim() === '') {
            alert("예약자 이름을 입력해 주세요.");
            return;
        }
    
        if (!phone || phone.trim() === '') {
            alert("휴대폰 번호를 입력해 주세요.");
            return;
        }
    
        if (!email || email.trim() === '') {
            alert("이메일 주소를 입력해 주세요.");
            return;
        }



    let merchantUid = generateMerchantUid(date, time, theme); // 고유한 주문번호 생성

    IMP.request_pay(
        {
            pg: "html5_inicis",		//KG이니시스 pg파라미터 값
            pay_method: "card",		//결제 방법
            merchant_uid: merchantUid,//주문번호
            name: theme,		//상품 명
            amount: price,			//금액
              buyer_email: email,
              buyer_name: name,
              buyer_tel: phone,
        },
        function (rsp) {
              //rsp.imp_uid 값으로 결제 단건조회 API를 호출하여 결제결과를 판단합니다.
            if (rsp.success) {

                    // 가맹점 서버 결제 API 성공시 로직
                    // let merchantUid = date+time+theme;

                    console.log("결제했다!!!");
                    alert("결제가 완료되었습니다!");

                    document.getElementById("payButton").style.display="none";

                    document.getElementById("logdingCheck").style.display="";


                    alert("잠시만 기다려주세요! 예약확인 메일을 보내는 중 입니다!");

                     
                    reservationToServer(merchantUid, date, time, theme, name, phone, email, participants, price).then(result =>{
                        console.log(result);

                        if(result == "1"){

                            reservationEmailToServer(email, merchantUid).then(result =>{

                                console.log(result);

                                if(result == "isOk"){
    
                                  alert("결제가 완료 되었습니다. 이메일을 확인해 주세요.");
                                  window.location.replace("/portOnePay/reservationCheck");

                                }

                            })        
                        }
                    })
            } else {
                alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
            }
        }
    );
}
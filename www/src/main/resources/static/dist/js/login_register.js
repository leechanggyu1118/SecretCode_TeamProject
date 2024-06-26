const signUpBtn = document.getElementById("signUp");
const signInBtn = document.getElementById("signIn");
const container = document.querySelector(".containerLR");
//이메일 유효성
const pattern = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-za-z0-9\-]+/;
//비밀번호 유효성
const regex_pwd = /^(?!((?:[A-Za-z]+)|(?:[~!@#$%^&*()_+=]+)|(?:[0-9]+))$)[A-Za-z\d~!@#$%^&*()_+=]{8,16}$/;


signUpBtn.addEventListener("click", () => {
  container.classList.add("right-panel-active");
});
signInBtn.addEventListener("click", () => {
  container.classList.remove("right-panel-active");
});

// 아이디 중복체크, 유효성 검사
document.getElementById('checkBtn').addEventListener('click',(e)=>{
    let email = document.getElementById("email").value;
    let check = document.getElementById('check');
    let checkBtn = document.getElementById('checkBtn');
    console.log(email);
    
    if(email.length == 0){ // myform.id.value == "" 이것도 가능
        alert("아이디가 누락되었습니다.");
        check.innerHTML = "";
        check.innerHTML = `<p class="text-danger">사용할 아이디를 입력해주세요</p>`;
        document.getElementById("email").focus(); // 포커스를 이동시켜 바로 아이디를 입력할 수 있게
        return false;
    }

    
        if(!emailValidChk(email)){
            
            alert("이메일 형식이 아닙니다.");
            check.innerHTML = "";
            check.innerHTML = `<p class="text-danger">이메일 형식을 사용해주세요. 예)example@test.com</p>`;
            document.getElementById("email").focus();
            return false;
        }
 

    emailToServer(email).then(result =>{
        console.log(result);
        if(result == 1){
            alert("중복된 아이디 입니다.");
            check.innerHTML = "";
            check.innerHTML = `<p class="text-danger">중복된 아이디 입니다.</p>`;
            document.getElementById("email").focus();
            return false;

        }else{
            check.innerHTML = "";
            check.innerHTML = `<p class="text-success">사용 가능 아이디 입니다.</p>`;
            checkBtn.style.display="none";

            document.getElementById("emailSendNumberBtn").style.display="";
           

        }
    })



});

//구글 이메일 인증번호 보내기
document.getElementById("emailSendNumberBtn").addEventListener("click",()=>{
    let emailSendCheck = document.getElementById('emailSendCheck');
    document.getElementById("emailCheckNumber").style.display="";
    document.getElementById("emailCheckNumberBtn").style.display="";
    check.innerHTML = "";
    emailSendCheck.innerHTML = `<p class="text-light">인증메일을 전송하였습니다.</p>`;
    let emailNumberCheckLine = document.getElementById("emailNumberCheckLine");
    document.getElementById("emailSendNumberBtn").style.display="none";

    
    postCommentToServer(document.getElementById("email").value).then(result =>{
        console.log(result);
               //구글 이메일 인증번호 확인
       document.getElementById("emailCheckNumberBtn").addEventListener("click",()=>{
        document.getElementById("emailCheckNumber").focus();

        console.log("인증번호 확인"+result);

        if(result == (document.getElementById('emailCheckNumber').value)){
            document.getElementById("emailNumberCheckLine").style.color = "green";
            emailNumberCheckLine.innerHTML = "";
            emailNumberCheckLine.innerHTML = `<p class="text-success ischeckEmailNum">인증번호 확인 완료되었습니다.</p>`;
            document.getElementById("emailCheckNumberBtn").style.display="none";

        }else{
            document.getElementById("emailNumberCheckLine").style.color = "red";
            emailNumberCheckLine.innerHTML = "";
            emailNumberCheckLine.innerHTML = `<p class="text-danger">인증번호를 다시 확인해주세요.</p>`;
        }

    });
 

        
    })


});



//구글 이메일 인증번호 보내기 비동기
async function postCommentToServer(email){
    try {
        const url = "/member/emailConfirm"
        const config = {
            method:"post",
            headers:{
                "content-type" : "application/json; charset = utf-8"
            },
            body:email
        };

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;

    } catch (error) {
        console.log(error);
        
    }

}



document.getElementById('email').addEventListener('keyup',()=>{
    checkBtn.style.display="";
    check.innerHTML = "";
    document.getElementById("emailSendNumberBtn").style.display="none";
    document.getElementById("emailCheckNumber").style.display="none";
    document.getElementById("emailCheckNumberBtn").style.display="none";
});

//이메일 유효성
function emailValidChk(email) {
    if(pattern.test(email) === false) { return false; }
    else { return true; }
}

//비밀번호 유효성
function pwdValidChk(pwd) {
    if(regex_pwd.test(pwd) === false) { return false; }
    else { return true; }
}

//아이디 중복체크 비동기 메서드
async function emailToServer(email){
    try {
        const url = "/member/emailCheck/"+email;
        const resp = await fetch(url);
        const result = resp.text();

        return result;
    } catch (error) {
        console.log(error);
    }
}

//비밀번호 이중 확인
document.getElementById('pwdCheck').addEventListener('keyup',()=>{
    let pwd = document.getElementById('pwd').value; //비밀번호 값

    let pwdCheck = document.getElementById("pwdCheck").value; // 비밀번호 더블체크 
    let checkpwd = document.getElementById('checkpwd'); // 비밀번호 더블체크 알림창

    if(pwd == pwdCheck){
        document.getElementById("pwd").style.color = "green";
        pwdCheck = document.getElementById("pwdCheck").style.color = "green";
        checkpwd.innerHTML = "";
        checkpwd.innerHTML = `<p class="text-success ischeckpwd">비밀번호 확인 완료되었습니다.</p>`;
    }else{
        document.getElementById("pwd").style.color = "red";
        pwdCheck = document.getElementById("pwdCheck").style.color = "red";
        checkpwd.innerHTML = "";
                checkpwd.innerHTML = `<p class="text-danger">비밀번호를 다시 확인해주세요.</p>`;
    }

});

//비밀번호 유효성 체크
document.getElementById('pwd').addEventListener('keyup',()=>{
    document.getElementById('pwdCheck').disabled = true;
    document.getElementById("pwdCheck").value = "";
    checkpwd.innerHTML = "";
    checkpwd.innerHTML = `<p class="text-danger">비밀번호를 다시 확인해주세요.</p>`;
    let checkpwdvalue = document.getElementById("checkpwdvalue"); //비밀번호 유효성 검사 알림창

    if(!pwdValidChk(document.getElementById('pwd').value)) {
        checkpwdvalue.innerHTML = "";
        document.getElementById('pwd').style.color = "red";
        checkpwdvalue.innerHTML = `<p class="text-danger">최소 8 ~ 16자, 영문자,숫자, 특수문자 중 2가지 이상 조합</p>`;
        console.log(pwdValidChk(document.getElementById('pwd').value));
        console.log(document.getElementById('pwd').value);
    }else{
        checkpwdvalue.innerHTML = "";
        document.getElementById('pwd').style.color = "green";
        document.getElementById('pwdCheck').disabled = false;

    }

});

//회원가입 버튼 관리
document.addEventListener('keyup',()=>{

    let nickName = document.getElementById('nickName').value;
    let phone = document.getElementById('phone').value;
    let pwdcheck = document.querySelector('.ischeckpwd').innerText; //비밀번호 체크 성공했을 떄 생김
    let ischeckEmailNum = document.querySelector('.ischeckEmailNum').innerText; //이메일 인증번호 체크 성공했을 떄 생김
    console.log(nickName);
    console.log(phone.length);
    console.log(pwdcheck);
    console.log(ischeckEmailNum);

    if((pwdcheck !== null)&&(nickName !== "")&&(phone.length == 13)&&(ischeckEmailNum !== "")){
        document.querySelector(".form_btn").disabled = false;
    }

    
});

document.getElementById('joinMembership').addEventListener('click',()=>{
    alert("회원가입이 완료되었습니다. 로그인 해주세요.");
});

//전화번호 자동 하이픈 생성
const autoHyphen2 = (target) => {
    target.value = target.value
      .replace(/[^0-9]/g, '')
     .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
   }
   



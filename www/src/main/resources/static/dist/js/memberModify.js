
console.log("userModify.js in!!");
//비밀번호 유효성
const regex_pwd = /^(?!((?:[A-Za-z]+)|(?:[~!@#$%^&*()_+=]+)|(?:[0-9]+))$)[A-Za-z\d~!@#$%^&*()_+=]{8,16}$/;

//비밀번호 유효성
function pwdValidChk(pwd) {
    if(regex_pwd.test(pwd) === false) { return false; }
    else { return true; }
}

//비밀번호 이중 확인
document.getElementById('pwdCheck').addEventListener('keyup',()=>{
    let pwd = document.getElementById('pwd').value; //비밀번호 값

    let pwdCheck = document.getElementById("pwdCheck").value; // 비밀번호 더블체크 
    let checkpwd = document.getElementById('checkpwd'); // 비밀번호 더블체크 알림창

    document.getElementById('updateBtn').disabled = true;
    if(pwd == pwdCheck){
        document.getElementById("pwd").style.color = "green";
        pwdCheck = document.getElementById("pwdCheck").style.color = "green";
        checkpwd.innerHTML = "";
        checkpwd.innerHTML = `<p class="text-success ischeckpwd">비밀번호 확인 완료되었습니다.</p>`;
        document.getElementById('updateBtn').disabled = false;
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
    document.getElementById('updateBtn').disabled = true;
    document.getElementById("pwdCheck").value = "";
    checkpwd.innerHTML = "";
    checkpwd.innerHTML = `<p class="text-danger">비밀번호를 다시 확인해주세요.</p>`;
    let checkpwdvalue = document.getElementById("checkpwdvalue"); //비밀번호 유효성 검사 알림창

    console.log(pwd);
    //공백이면 innertext 다 지우고 수정버튼 활성화
    if(pwd.value == ""){
        checkpwdvalue.innerHTML = "";
        checkpwd.innerHTML = "";
        document.getElementById('updateBtn').disabled = false;
        return false;
    }

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






document.getElementById('deleteBtn').addEventListener('click', (e)=>{
    const isDelete = confirm("정말로 회원삭제를 하겠습니까?");
    // confirm => 예 아니오 콘솔창이 뜨고 true / false 값으로 나온다.
    console.log(isDelete);
    if(isDelete){
        alert("회원삭제가 완료되었습니다.");
       return location.href = "/member/deleteMember";
    }
});

document.getElementById("updateBtn").addEventListener('click',(e)=>{
    alert("수정이 완료되었습니다.");
    alert("다시 로그인 해주세요.");

});

//전화번호 자동 하이픈 생성
const autoHyphen2 = (target) => {
    target.value = target.value
      .replace(/[^0-9]/g, '')
     .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
   }
   

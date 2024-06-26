//전화번호 자동 하이픈 생성
const autoHyphen2 = (target) => {
    target.value = target.value
      .replace(/[^0-9]/g, '')
     .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
   }



   //아이디 체크 비동기 메서드
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


   //전화번호 이름 체크 비동기 메서드
   async function phoneToServer(phone, nickName){
    try {
        const url = "/member/phoneCheck/"+phone+"/"+nickName;
        const resp = await fetch(url);
        const result = await resp.text();

        // const resp = await fetch("/member/phoneCheck/"+phone+"/"+nickName);
        // const result = await resp.json();
  
        return result;
    } catch (error) {
        console.log(error);
    }
  }




//아이디 찾기
document.getElementById('checkBtnEmail').addEventListener('click',()=>{
  let name = document.getElementById("findEmail").value; //이름
  let phoneE = document.getElementById("phoneE").value; //전화번호
  let emailPhoneCheckLine = document.getElementById("emailPhoneCheckLine");

  console.log(emailPhoneCheckLine);

  if(name == "" || name == null){
    alert('이름을 입력해주세요.');
    emailPhoneCheckLine.innerHTML = "";
    emailPhoneCheckLine.innerHTML = `<span style="color: red; font-size:16px; font-weight: bold; box-shadow: none;">이름을 입력해주세요.</span>`;
    return false;
  }

  if(phoneE == "" || phoneE == null){
    alert('휴대폰번호를 입력해주세요.');
    emailPhoneCheckLine.innerHTML = "";
    emailPhoneCheckLine.innerHTML = `<span style="color: red; font-size:16px; font-weight: bold; box-shadow: none;">휴대폰번호를 입력해주세요.</span>`;
    return false;
  }




  phoneToServer(phoneE, name).then(result =>{
    console.log(result);
    if(result !== ""){
      
      emailPhoneCheckLine.innerHTML = "";
      emailPhoneCheckLine.innerHTML = `<span style=" font-size:16px; font-weight: bold; box-shadow: none;" >아이디 : ${result}</span>`;
      
      //return false;
      
    }else{
      emailPhoneCheckLine.innerHTML = "";
      document.getElementById("findEmail").focus();
      emailPhoneCheckLine.innerHTML = `<span style="color: red; font-size:16px; font-weight: bold; box-shadow: none;">존재하지 않는 사용자입니다.</span>`;
      //return false;
       
    }
})



});



//비밀번호 재발급
document.getElementById('checkBtnPwd').addEventListener('click',()=>{
  let phoneP = document.getElementById("phoneP").value;
  let nickNameP = document.getElementById("nickNameP").value;
  let emailP = document.getElementById("emailP").value;


  if(emailP == "" || emailP == null){
    alert('이메일을 입력해주세요.');
    return false;
  }

  if(nickNameP == "" || nickNameP == null){
    alert('이름을 입력해주세요.');
    return false;
  }

  if(phoneP == "" || phoneP == null){
    alert('휴대폰번호를 입력해주세요.');
    return false;
  }

  document.getElementById("checkBtnPwd").style.display="none";

  document.getElementById("logdingCheck").style.display="";


  pwdReturnToServer(phoneP, nickNameP, emailP).then(result =>{
    console.log(result);
    if(result == "isOk"){
      document.getElementById("logdingCheck").style.display="none";
        document.getElementById("goToLogin").style.display="";
      alert("임시 비밀번호를 발급했습니다. 이메일을 확인해 주세요.");
      alert("로그인 후 비밀번호를 변경해주세요.");
      
      //return false;
      
    }else{
      document.getElementById("checkBtnPwd").style.display="";
      document.getElementById("logdingCheck").style.display="none";
      document.getElementById("goToLogin").style.display="none";
      alert("존재하지 않는 사용자입니다. 다시 확인해 주세요.");
      //return false;
       
    }
})
  


});

  //비밀번호 재발급 체크 비동기 메서드
  async function pwdReturnToServer(phone, nickName, email){
    try {
        const url = "/member/pwdReturnCheck/"+phone+"/"+nickName+"/"+email;
        const resp = await fetch(url);
        const result = await resp.text();

        // const resp = await fetch("/member/phoneCheck/"+phone+"/"+nickName);
        // const result = await resp.json();
  
        return result;
    } catch (error) {
        console.log(error);
    }
  }


 
  
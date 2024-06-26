console.log("js 들어왔다.");
var themeNumExist = []; // 빈 배열 생성

document.getElementById('themeNum').addEventListener('keyup',()=>{

    let themeNum = document.getElementById('themeNum').value;
    // let  = document.querySelectorAll('themeNumExist').value;
    // console.log(themeNumExist);


    //등록할 테마 번호와 비교하여 값에 넣기
    var isEqual = themeNumExist.some(function(value) {
        return value === themeNum;
    });



    if(isEqual){
        document.getElementById('themeNumCheck').innerText=""
        document.getElementById('themeNum').style.color = "red";
        document.getElementById('themeNumCheck').display="";
        document.getElementById('themeNumCheck').innerText="이미 존재하는 테마 번호입니다."
        document.getElementById('themeNumCheck').style.color = "red";
        document.getElementById("themeBtn").disabled = true;
    }else{
        document.getElementById('themeNumCheck').innerText=""
        document.getElementById('themeNumCheck').display="none";
        document.getElementById('themeNum').style.color = "black";
        document.getElementById("themeBtn").disabled = false;
    }


});

//테마 번호를 배열에 넣기
document.addEventListener('DOMContentLoaded', function() {
    var themeNumCells = document.querySelectorAll('.themeNumExist');
    
    themeNumCells.forEach(function(cell) {
        console.log(cell.textContent); // 콘솔에 값을 출력하거나 필요한 작업을 수행합니다.

        themeNumExist.push(cell.textContent.trim()); // 배열에 cell의 textContent 값을 추가

        console.log(themeNumExist);
    });
});

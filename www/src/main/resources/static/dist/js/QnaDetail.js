console.log("QNA Detail js in");
document.getElementById('listBtn').addEventListener('click',()=>{
    location.href="/qna/list";
})


document.getElementById('delBtn').addEventListener('click',()=>{
    document.getElementById('delForm').submit();
});

document.getElementById('modBtn').addEventListener('click',()=>{
    document.getElementById('title').readOnly=false;
    document.getElementById('content').readOnly=false;

    let modBtn = document.createElement('button');
    // <button></button>
    modBtn.setAttribute('type','submit');
    modBtn.classList.add('btn','btn-outline-warning');
    modBtn.innerText="수정 완료";
    //생성한 버튼 modForm에 추가
    document.getElementById('modDiv').appendChild(modBtn);

    //modBtn , delBtn 임시삭제
    document.getElementById('modBtn').remove();
    document.getElementById('delBtn').remove();
    document.getElementById('listBtn').remove();

})



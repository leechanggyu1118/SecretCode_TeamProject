// 이미지 클릭 시 모달 창 열기
document.getElementById('sb').addEventListener('click', function() {
  document.getElementById('myModal').style.display = 'block';
  document.getElementById('modalImage').src = this.src;
});

// 닫기 버튼 클릭 시 모달 창 닫기
document.getElementsByClassName('close')[0].addEventListener('click', function() {
  document.getElementById('myModal').style.display = 'none';
});

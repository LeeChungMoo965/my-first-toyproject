// 페이지가 열리면 즉시 실행되는 함수
document.addEventListener("DOMContentLoaded", function() {
    // header.html 파일을 요청해서 가져옴
    fetch('/header.html')
        .then(response => response.text())
        .then(html => {
            // 가져온 HTML을 미리 만들어둔 영역에 꽂아 넣음
            document.getElementById('header-placeholder').innerHTML = html;
        })
        .catch(error => console.error("헤더 로딩 실패:", error));
});
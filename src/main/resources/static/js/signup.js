document.getElementById('signupForm').addEventListener('submit', function(e) {
    e.preventDefault(); 

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const name = document.getElementById('name').value;

    const requestData = {
        email: email,
        password: password,
        name: name
    };

    fetch('/api/members', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
    .then(response => {
        if (response.ok) {
            alert("사장님 회원가입이 성공적으로 완료되었습니다! 🎉");
            document.getElementById('signupForm').reset();
        } else {
            alert("회원가입에 실패했습니다.");
        }
    })
    .catch(error => {
        console.error("통신 에러:", error);
        alert("서버 연결에 문제가 발생했습니다.");
    });
});
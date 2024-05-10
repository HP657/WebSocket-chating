function signin(event) {
    event.preventDefault();
    const email = document.getElementById('login-email').value;
    const password = document.getElementById('login-password').value;

    const data = {
        email: email,
        password: password
    };

    fetch('/api/auth/signin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('네트워크 에러');
        }
        return response.json();
    })
    .then(data => {
        alert(data.data);
    })
    .catch(error => {
        console.error('Error:', error);
        alert('로그인 중 에러');
    });
}

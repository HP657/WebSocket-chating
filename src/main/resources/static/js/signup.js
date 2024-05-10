function signup(event) {
    event.preventDefault();
    const username = document.getElementById('signup-username').value;
    const email = document.getElementById('signup-email').value;
    const password = document.getElementById('signup-password').value;

    const data = {
        username: username,
        email: email,
        password: password
    };

    fetch('/api/auth/signup', {
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
    .catch((error) => {
        console.error('Error:', error);
        alert('회원가입 중 에러');
    });
}

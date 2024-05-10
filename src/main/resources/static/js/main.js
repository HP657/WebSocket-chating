function showForm(form) {
    document.getElementById('loginForm').style.display = form === 'login' ? 'block' : 'none';
    document.getElementById('signupForm').style.display = form === 'signup' ? 'block' : 'none';
    document.getElementById('chatroomEntry').style.display = 'none';  // 초기 상태에서 채팅방 입장 숨기기
}

function enterChatroom() {
    var chatroomId = document.getElementById('chatroom-number').value;
    if (chatroomId) {
        window.location.href = '/chatroom/' + chatroomId;
    } else {
        alert('채팅방 번호를 입력해주세요.');
    }
}

function checkUserInfo() {
    fetch('/api/user/info', {
        credentials: 'include'
    })
    .then(response => response.json())
    .then(data => {
        if (data && data.status === 200 && data.data.userId) {
            document.getElementById('chatroomEntry').style.display = 'block'; // 채팅방 입장 폼 활성화
            document.getElementById('loginForm').style.display = 'none';
            document.getElementById('signupForm').style.display = 'none';
        } else {
            showForm('login');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showForm('login');
    });
}

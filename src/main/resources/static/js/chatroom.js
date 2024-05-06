var stompClient = null;
var userId = null;
var userMap = {}; // 사용자 이름을 저장할 객체

document.addEventListener("DOMContentLoaded", function() {
    // 먼저 사용자 정보를 불러옵니다
    loadUserMap();
    // 로그인 확인 및 WebSocket 연결 시도
    fetch('/api/user/info', {
        credentials: 'include'
    })
    .then(response => response.json())
    .then(data => {
        if (data.status === 200 && data.data.userId) {
            userId = data.data.userId;
            connect();
        } else {
            window.location.href = '/login';
        }
    })
    .catch(error => {
        console.error("Error during login: ", error);
        window.location.href = '/login';
    });
});

function loadUserMap() {
    fetch('/api/user/users', { credentials: 'include' })
    .then(response => response.json())
    .then(data => {
        if (data.status === 200) {
            data.data.forEach(user => {
                userMap[user.userId] = user.username; // 사용자 ID와 이름 매핑
            });
        } else {
            console.error("Failed to load user data:", data);
        }
    })
    .catch(error => {
        console.error("Error loading user data:", error);
    });
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        setConnected(true);
        stompClient.subscribe('/topic/public', function (message) {
            showMessage(JSON.parse(message.body));
        });
    }, function(error) {
        console.error('Connection failed: ', error);
        alert('Could not connect to WebSocket server. Please refresh the page to try again!');
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
        console.log('Disconnected');
    }
    setConnected(false);
}

function sendMessage() {
    var messageContent = document.getElementById('message').value.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            userId: userId,
            message: messageContent
        };
        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
        document.getElementById('message').value = '';
    } else {
        alert("Please enter a message.");
    }
}

function showMessage(messageData) {
    var messages = document.getElementById('messages');
    var messageElement = document.createElement('li');
    if (messageData.userId === userId) {
        messageElement.classList.add('my-message');
    } else {
        messageElement.classList.add('other-message');
    }
    var username = userMap[messageData.userId] || 'Unknown User'; // 사용자 이름 찾기
    messageElement.textContent = username + ": " + messageData.message;
    messages.appendChild(messageElement);
}


function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('send').disabled = !connected;
    document.getElementById('conversation').style.visibility = connected ? 'visible' : 'hidden';
    if (!connected) {
        document.getElementById('messages').innerHTML = '';
    }
}

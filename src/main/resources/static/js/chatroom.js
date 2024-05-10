var stompClient = null;
var userId = null;
var userMap = {};
var roomId = window.location.pathname.split('/').pop();

document.addEventListener("DOMContentLoaded", function() {
    const chatContainer = document.getElementById('chat');
    chatContainer.innerHTML = `Welcome to Room ${roomId}!`;
    loadUserMap();
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
        window.location.href = '/login';
    });
});

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        stompClient.subscribe(`/topic/public/${roomId}`, function (message) {
            showMessage(JSON.parse(message.body));
        });
        fetchExistingMessages(roomId);
    }, function(error) {
        alert('Could not connect to WebSocket server. Please refresh the page to try again!');
    });
}

function fetchExistingMessages(roomId) {
    fetch(`/${roomId}/messages`)
        .then(response => response.json())
        .then(messages => {
            messages.forEach(message => {
                showMessage(message);
            });
        })
        .catch(error => {
            console.error('Error fetching messages:', error);
        });
}


function sendMessage() {
    var messageContent = document.getElementById('message').value.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            userId: userId,
            message: messageContent,
            chatRoomId: roomId
        };
        stompClient.send(`/app/chat.send/${roomId}`, {}, JSON.stringify(chatMessage));
        document.getElementById('message').value = '';
    } else {
        alert("Please enter a message.");
    }
}

function loadUserMap() {
    return fetch('/api/user/users', { credentials: 'include' })
        .then(response => response.json())
        .then(data => {
            if (data.status === 200) {
                data.data.forEach(user => {
                    userMap[user.userId] = user.username;  // 사용자 ID를 키로, 사용자 이름을 값으로 저장
                });
            } else {
                console.error("Failed to load user data:", data);
            }
        })
        .catch(error => {
            console.error("Error loading user data:", error);
        });
}


function showMessage(messageData) {
    var messages = document.getElementById('messages');
    var messageElement = document.createElement('li');
    var username = messageData.userId.username || 'Unknown User';
    var messageContent = `${username}: ${messageData.content}`;
    messageElement.textContent = messageContent;

    if (messageData.userId.userId === userId) {
        messageElement.classList.add('my-message');
    } else {
        messageElement.classList.add('other-message');
    }

    messages.appendChild(messageElement);
}


function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('send').disabled = !connected;
    document.getElementById('conversation').style.visibility = connected ? 'visible' : 'hidden';
    if (!connected) {
        document.getElementById('messages').innerHTML = '';
    }
}

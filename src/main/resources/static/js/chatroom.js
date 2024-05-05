var stompClient = null;
var username = null;

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        stompClient.subscribe('/topic/public', function (message) {
            showMessage(JSON.parse(message.body).message);
        });
    }, function(error) {
        alert('Could not connect to WebSocket server. Please refresh the page to try again!');
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
}

function sendMessage() {
    var messageContent = document.getElementById('message').value;
    if (messageContent && stompClient && username) {
        var chatMessage = {
            username: username,
            message: messageContent
        };
        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
        document.getElementById('message').value = '';
    }
}

function showMessage(message) {
    var messages = document.getElementById('messages');
    var messageElement = document.createElement('li');
    messageElement.appendChild(document.createTextNode(message));
    messages.appendChild(messageElement);
}

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('send').disabled = !connected;
    document.getElementById('conversation').style.visibility = connected ? 'visible' : 'hidden';
    document.getElementById('messages').innerHTML = '';
}

document.addEventListener("DOMContentLoaded", function() {
    fetch('/api/user/info', {
        credentials: 'include'
    })
    .then(response => response.json())
    .then(data => {
        if (data.status === 200 && data.data.username) {
            username = data.data.username;
            connect();
        } else {
            window.location.href = '/login';
        }
    })
    .catch(error => {
        window.location.href = '/login';
    });
});
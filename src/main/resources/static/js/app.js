let stompClient = null;

function connect(chatRoomId) {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        document.getElementById('chat-container').style.display = 'block';
        stompClient.subscribe('/topic/chatroom/' + chatRoomId, function (messageOutput) {
            showMessage(JSON.parse(messageOutput.body).content);
        });
    });
}

function sendMessage() {
    var messageContent = document.getElementById('messageInput').value.trim();
    if (messageContent && stompClient) {
        var chatRoomId = document.querySelector('input[type="text"]').getAttribute('data-chatroom-id');
        stompClient.send("/app/chat/" + chatRoomId + "/sendMessage", {}, JSON.stringify({content: messageContent}));
        document.getElementById('messageInput').value = '';
    }
}

function showMessage(message) {
    var messageArea = document.getElementById('messageArea');
    var messageElement = document.createElement('li');
    messageElement.appendChild(document.createTextNode(message));
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

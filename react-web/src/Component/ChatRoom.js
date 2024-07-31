import React, { useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';
import '../Css/chatroom.css';

const ChatRoom = () => {
    const [stompClient, setStompClient] = useState(null);
    const [userId, setUserId] = useState(null);
    const [roomId, setRoomId] = useState(window.location.pathname.split('/').pop());
    const [messages, setMessages] = useState([]);
    const [messageContent, setMessageContent] = useState('');
    const [users, setUsers] = useState({});
    const [connected, setConnected] = useState(false);

    useEffect(() => {
        fetchUserInfo();
        loadUserMap();
    }, []);

    const fetchUserInfo = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/user/info', { credentials: 'include' });
            const data = await response.json();
            if (data.status === 200 && data.data.userId) {
                setUserId(data.data.userId);
                connect();
            } else {
                redirectToHome();
            }
        } catch (error) {
            console.error('Error fetching user info:', error);
            redirectToHome();
        }
    };

    const redirectToHome = () => {
        window.location.href = '/';
    };

    const connect = () => {
        const client = new Client({
            brokerURL: 'ws://localhost:8080/ws',
            debug: (str) => console.log(str),
            onConnect: (frame) => {
                setConnected(true);
                console.log('Connected: ' + frame);
                subscribeToMessages(client);
                fetchExistingMessages();
            },
            onStompError: (frame) => {
                alert('Could not connect to WebSocket server. Please refresh the page to try again!');
                console.error('STOMP Error:', frame);
            },
        });
        client.activate();
        setStompClient(client);
    };

    const subscribeToMessages = (client) => {
        client.subscribe(`/topic/public/${roomId}`, (message) => {
            console.log('Received message:', message.body);
            showMessage(JSON.parse(message.body)); // Ensure incoming messages are added
        });
    };

    const fetchExistingMessages = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/chatroom/${roomId}/messages`);
            if (!response.ok) throw new Error('Failed to fetch existing messages');
            const messages = await response.json();
            // Sort messages by timestamp in descending order
            messages.sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp));
            setMessages(messages); // Set messages in descending order
        } catch (error) {
            console.error('Error fetching messages:', error);
        }
    };

    const sendMessage = () => {
        if (!messageContent) {
            alert("Please enter a message.");
            return;
        }

        if (stompClient && connected) {
            const chatMessage = {
                userId: userId,
                message: messageContent,
                chatRoomId: roomId,
            };

            console.log('Sending message:', chatMessage);

            stompClient.publish({
                destination: `/app/chat.send/${roomId}`,
                body: JSON.stringify(chatMessage)
            });
            setMessageContent(''); // Clear input field
        } else {
            alert("WebSocket client is not connected.");
        }
    };

    const loadUserMap = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/user/users', { credentials: 'include' });
            const data = await response.json();
            if (data.status === 200) {
                const userMap = {};
                data.data.forEach((user) => {
                    userMap[user.userId] = user.username;  
                });
                setUsers(userMap);
            } else {
                console.error("Failed to load user data:", data);
            }
        } catch (error) {
            console.error("Error loading user data:", error);
        }
    };

    const showMessage = (messageData) => {
        setMessages((prevMessages) => [messageData, ...prevMessages]); // Add new message to the beginning
    };

    const logout = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/auth/logout', { method: 'GET', credentials: 'include' });
            if (response.ok) {
                redirectToHome();
            } else {
                alert('Logout error');
            }
        } catch (error) {
            console.error('Error logging out:', error);
        }
    };

    return (
        <div>
            <h2>Chat Room {roomId}</h2>
            <button onClick={logout}>Logout</button>
            <div style={{ visibility: connected ? 'visible' : 'hidden' }}>
                <input 
                    type="text" 
                    value={messageContent} 
                    onChange={(e) => setMessageContent(e.target.value)} 
                    autoComplete="off" 
                />
                <button onClick={sendMessage} disabled={!connected}>Send</button>
                <ul id="messages">
                    {messages.map((msg, index) => {
                        const username = msg.userId?.username || 'Unknown User'; // Access username directly from userId object
                        return (
                            <li key={index} className={msg.userId.userId === userId ? 'my-message' : 'other-message'}>
                                {`${username}: ${msg.content || ''}`} {/* Use msg.content for the message content */}
                            </li>
                        );
                    })}
                </ul>
            </div>
        </div>
    );
};

export default ChatRoom;

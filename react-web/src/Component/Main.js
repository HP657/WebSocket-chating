import React, { useState, useEffect } from 'react';
import '../Css/styles.css'; 

const Main = () => {
    const [formType, setFormType] = useState(null);
    const [userId, setUserId] = useState(null);
    const [chatroomId, setChatroomId] = useState('');

    useEffect(() => {
        checkUserInfo();
    }, []);

    const checkUserInfo = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/user/info', {
                credentials: 'include',
            });
            const data = await response.json();
            if (data && data.status === 200 && data.data.userId) {
                setUserId(data.data.userId);
                setFormType('chatroom');
            } else {
                setFormType('login');
            }
        } catch (error) {
            console.error('Error:', error);
            setFormType('login');
        }
    };

    const signin = async (event) => {
        event.preventDefault();
        const email = event.target.email.value;
        const password = event.target.password.value;

        const data = { email, password };

        try {
            const response = await fetch('http://localhost:8080/api/auth/signin', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
                credentials: 'include', 
            });
            if (!response.ok) {
                throw new Error('네트워크 에러');
            }
            const result = await response.json();
            alert(result.data);
            checkUserInfo();
        } catch (error) {
            console.error('Error:', error);
            alert('로그인 중 에러');
        }
    };

    const signup = async (event) => {
        event.preventDefault();
        const username = event.target.username.value;
        const email = event.target.email.value;
        const password = event.target.password.value;

        const data = { username, email, password };

        try {
            const response = await fetch('http://localhost:8080/api/auth/signup', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
                credentials: 'include', 
            });
            if (!response.ok) {
                throw new Error('네트워크 에러');
            }
            const result = await response.json();
            alert(result.data);
            setFormType('login');
        } catch (error) {
            console.error('Error:', error);
            alert('회원가입 중 에러');
        }
    };

    const enterChatroom = () => {
        if (chatroomId) {
            window.location.href = `/chatroom/${chatroomId}`;
        } else {
            alert('채팅방 번호를 입력해주세요.');
        }
    };

    const renderForm = () => {
        switch (formType) {
            case 'login':
                return (
                    <div id="loginForm">
                        <h2>로그인</h2>
                        <form onSubmit={signin}>
                            <label htmlFor="email">이메일:</label>
                            <input type="email" name="email" required /><br />
                            <label htmlFor="password">비밀번호:</label>
                            <input type="password" name="password" required /><br />
                            <button type="submit">로그인</button>
                        </form>
                    </div>
                );
            case 'signup':
                return (
                    <div id="signupForm">
                        <h2>회원가입</h2>
                        <form onSubmit={signup}>
                            <label htmlFor="username">유저네임:</label>
                            <input type="text" name="username" required /><br />
                            <label htmlFor="email">이메일:</label>
                            <input type="email" name="email" required /><br />
                            <label htmlFor="password">비밀번호:</label>
                            <input type="password" name="password" required /><br />
                            <button type="submit">회원가입</button>
                        </form>
                    </div>
                );
            case 'chatroom':
                return (
                    <div id="chatroomEntry">
                        <h2>채팅방 입장</h2>
                        <input
                            type="number"
                            value={chatroomId}
                            onChange={(e) => setChatroomId(e.target.value)}
                            placeholder="채팅방 번호"
                            min="1"
                            required
                        />
                        <button onClick={enterChatroom}>입장</button>
                    </div>
                );
            default:
                return null;
        }
    };

    return (
        <div>
            <h1>메인화면</h1>
            <button onClick={() => setFormType('login')}>로그인</button>
            <button onClick={() => setFormType('signup')}>회원가입</button>
            <button onClick={checkUserInfo}>사용자 정보 확인(채팅방 입장)</button>
            {renderForm()}
        </div>
    );
};

export default Main;

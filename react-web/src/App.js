import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Main from './Component/Main';
import ChatRoom from './Component/ChatRoom';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/chatroom/:id" element={<ChatRoom />} />
      </Routes>
    </Router>
  );
}

export default App;

package WebSocket.HP657.HP657.service;

import WebSocket.HP657.HP657.entity.ChatRoomEntity;
import WebSocket.HP657.HP657.entity.UserEntity;
import WebSocket.HP657.HP657.repository.ChatRoomRepository;
import WebSocket.HP657.HP657.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ChatRoomEntity createChatroom(String roomName, Long userId1, Long userId2) {
        UserEntity user1 = userRepository.findById(userId1).orElseThrow(() -> new RuntimeException("User not found"));
        UserEntity user2 = userRepository.findById(userId2).orElseThrow(() -> new RuntimeException("User not found"));

        ChatRoomEntity chatRoom = new ChatRoomEntity(roomName);
        chatRoom.getUsers().add(user1);
        chatRoom.getUsers().add(user2);
        return chatRoomRepository.save(chatRoom);
    }
}

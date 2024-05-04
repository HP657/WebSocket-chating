package WebSocket.HP657.HP657.service;
import WebSocket.HP657.HP657.dto.ChatMessageDto;
import WebSocket.HP657.HP657.entity.ChatMessageEntity;
import WebSocket.HP657.HP657.entity.ChatRoomEntity;
import WebSocket.HP657.HP657.entity.UserEntity;
import WebSocket.HP657.HP657.repository.ChatMessageRepository;
import WebSocket.HP657.HP657.repository.ChatRoomRepository;
import WebSocket.HP657.HP657.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserRepository userRepository;

    public ChatMessageDto saveMessage(ChatMessageDto messageDto, String chatRoomId) {
        // 채팅방을 ID로 찾기
        ChatRoomEntity chatRoom = chatRoomRepository.findById(Long.parseLong(chatRoomId))
                .orElseThrow(() -> new IllegalStateException("Chat room not found with ID: " + chatRoomId));

        // DTO를 엔티티로 변환
        ChatMessageEntity message = convertToEntity(messageDto);
        message.setChatRoom(chatRoom); // 채팅방 설정
        chatMessageRepository.save(message); // 메시지 저장

        return messageDto;  // DTO 반환
    }

    private ChatMessageEntity convertToEntity(ChatMessageDto chatMessageDto) {
        ChatMessageEntity message = new ChatMessageEntity();
        message.setSender(chatMessageDto.getSender());
        message.setContent(chatMessageDto.getContent());
        message.setTimestamp(chatMessageDto.getTimestamp());
        return message;
    }

    public boolean addUserToChatRoom(String username, String chatRoomId) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        ChatRoomEntity chatRoom = chatRoomRepository.findById(Long.parseLong(chatRoomId))
                .orElseThrow(() -> new IllegalArgumentException("Chat room not found with ID: " + chatRoomId));

        if (!chatRoom.getUsers().contains(user)) {
            chatRoom.getUsers().add(user);
            chatRoomRepository.save(chatRoom);
            return true;
        }
        return false;
    }
}

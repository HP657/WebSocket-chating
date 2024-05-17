package WebSocket.HP657.HP657.service;

import WebSocket.HP657.HP657.dto.MessageDto;
import WebSocket.HP657.HP657.entity.MessageEntity;
import WebSocket.HP657.HP657.entity.UserEntity;
import WebSocket.HP657.HP657.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;  // UserService 주입

    public MessageEntity saveMessage(MessageDto messageDTO) {
        UserEntity user = userService.findUserById(messageDTO.getUserId());  // UserEntity 조회
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + messageDTO.getUserId());
        }
        MessageEntity message = MessageEntity.builder()
                .userId(user)
                .content(messageDTO.getMessage())
                .chatRoomId(messageDTO.getChatRoomId())
                .timestamp(LocalDateTime.now())
                .build();

        return messageRepository.save(message);
    }

    public List<MessageEntity> getMessagesByChatRoomId(Long chatRoomId) {
        return messageRepository.findByChatRoomIdOrderByTimestampAsc(chatRoomId);
    }
}

package WebSocket.HP657.HP657.service;

import WebSocket.HP657.HP657.dto.MessageDto;
import WebSocket.HP657.HP657.entity.MessageEntity;
import WebSocket.HP657.HP657.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public MessageEntity saveMessage(MessageDto messageDTO) {
        MessageEntity message = MessageEntity.builder()
                .username(messageDTO.getUsername())
                .content(messageDTO.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return messageRepository.save(message);
    }
}

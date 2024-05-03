package WebSocket.HP657.HP657.service;

import WebSocket.HP657.HP657.dto.ChatMessageDto;
import WebSocket.HP657.HP657.entity.ChatMessageEntity;
import WebSocket.HP657.HP657.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public void saveMessage(ChatMessageDto messageDto) {
        ChatMessageEntity message = convertToEntity(messageDto);
        chatMessageRepository.save(message);
    }

    private ChatMessageEntity convertToEntity(ChatMessageDto chatMessageDto) {
        ChatMessageEntity message = new ChatMessageEntity();
        message.setSender(chatMessageDto.getSender());
        message.setContent(chatMessageDto.getContent());
        message.setTimestamp(chatMessageDto.getTimestamp());
        return message;
    }

    // More methods...
}


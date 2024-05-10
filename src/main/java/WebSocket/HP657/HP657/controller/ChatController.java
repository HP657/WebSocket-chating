package WebSocket.HP657.HP657.controller;

import WebSocket.HP657.HP657.dto.MessageDto;
import WebSocket.HP657.HP657.entity.MessageEntity;
import WebSocket.HP657.HP657.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    private MessageService messageService;
    // 채팅 메시지 보내기
    @MessageMapping("/chat.send/{roomId}")
    @SendTo("/topic/public/{roomId}")
    public MessageEntity sendMessage(@Payload MessageDto messageDto, @DestinationVariable String roomId) {
        return messageService.saveMessage(messageDto);
    }
}

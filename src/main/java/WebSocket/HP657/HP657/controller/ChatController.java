package WebSocket.HP657.HP657.controller;

import WebSocket.HP657.HP657.dto.ChatMessageDto;
import WebSocket.HP657.HP657.entity.ChatMessageEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageEntity sendMessage(ChatMessageEntity chatMessageEntity) {
        return chatMessageEntity;
    }
}

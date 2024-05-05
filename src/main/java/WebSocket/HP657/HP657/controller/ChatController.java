package WebSocket.HP657.HP657.controller;

import WebSocket.HP657.HP657.dto.MessageDto;
import WebSocket.HP657.HP657.entity.MessageEntity;
import WebSocket.HP657.HP657.service.MessageService;
import WebSocket.HP657.HP657.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ChatController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public MessageDto sendMessage(@Payload MessageDto messageDto) {
        MessageEntity savedMessage = messageService.saveMessage(messageDto);
        return new MessageDto(savedMessage.getUsername(), savedMessage.getContent());
    }

    @GetMapping("/chatroom")
    public String chatroom() {
        return "chatroom";
    }
}

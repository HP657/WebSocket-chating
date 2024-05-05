package WebSocket.HP657.HP657.controller;

import WebSocket.HP657.HP657.dto.MessageDto;
import WebSocket.HP657.HP657.entity.MessageEntity;
import WebSocket.HP657.HP657.entity.UserEntity;
import WebSocket.HP657.HP657.service.MessageService;
import WebSocket.HP657.HP657.service.UserService;
import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
    public MessageDto sendMessage(MessageDto messageDto) {
        MessageEntity savedMessage = messageService.saveMessage(messageDto);
        return new MessageDto(savedMessage.getUsername(), savedMessage.getContent());
    }

    @GetMapping("/chatroom")
    public String chatroom(HttpServletRequest request) {
        return "chatroom";
    }
}

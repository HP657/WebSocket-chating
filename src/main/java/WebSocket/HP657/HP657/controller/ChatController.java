package WebSocket.HP657.HP657.controller;

import WebSocket.HP657.HP657.dto.MessageDto;
import WebSocket.HP657.HP657.entity.MessageEntity;
import WebSocket.HP657.HP657.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ChatController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat.send/{roomId}")
    @SendTo("/topic/public/{roomId}")
    public MessageDto sendMessage(@Payload MessageDto messageDto, @DestinationVariable String roomId) {
        MessageEntity savedMessage = messageService.saveMessage(messageDto);
        return new MessageDto(savedMessage.getUserId(), savedMessage.getContent());
    }

    @GetMapping("/chatroom/{roomId}")
    public String chatroom(@PathVariable String roomId, Model model) {
        model.addAttribute("roomId", roomId);
        return "chatroom";
    }
}

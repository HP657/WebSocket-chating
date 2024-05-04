package WebSocket.HP657.HP657.controller;

import WebSocket.HP657.HP657.dto.ChatMessageDto;
import WebSocket.HP657.HP657.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat/{chatRoomId}/sendMessage")
    @SendTo("/topic/chatroom/{chatRoomId}")
    public ChatMessageDto sendMessage(@Payload ChatMessageDto message, @DestinationVariable Long chatRoomId) {
        chatService.saveMessage(message, chatRoomId.toString()); // 채팅 메시지 저장
        return message;
    }

    @MessageMapping("/chat/{chatRoomId}/addUser")
    @SendTo("/topic/chatroom/{chatRoomId}")
    public ChatMessageDto addUser(@Payload ChatMessageDto message, SimpMessageHeaderAccessor headerAccessor, @DestinationVariable Long chatRoomId) {
        String username = message.getSender();
        boolean added = chatService.addUserToChatRoom(username, chatRoomId.toString());
        if (!added) {
            throw new IllegalStateException("Failed to add user to chat room");
        }
        headerAccessor.getSessionAttributes().put("username", username);
        return message;
    }
}

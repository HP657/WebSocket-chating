package WebSocket.HP657.HP657.controller;

import WebSocket.HP657.HP657.dto.InviteDto;
import WebSocket.HP657.HP657.dto.MessageDto;
import WebSocket.HP657.HP657.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {
    @Autowired
    private ChatService chatService;

    // 채팅 메시지 보내기
    @MessageMapping("/chat.send/{roomId}")
    @SendTo("/topic/public/{roomId}")
    public MessageDto sendMessage(@Payload MessageDto messageDto, @DestinationVariable Long roomId) {
        return chatService.processMessage(messageDto, roomId.toString());
    }

    // 채팅방에 사용자 초대
    @PostMapping("/chatroom/{roomId}/invite")
    public ResponseEntity<?> inviteUserToRoom(@PathVariable Long roomId, @RequestBody InviteDto inviteDto) {
        chatService.inviteUserToRoom(roomId, inviteDto.getUserId());
        return ResponseEntity.ok().build();
    }

    // 채팅방 페이지
    @GetMapping("/chatroom/{roomId}")
    public String getChatRoomPage(@PathVariable Long roomId, Model model) {
        model.addAttribute("roomId", roomId);
        return "chatroom";
    }
}

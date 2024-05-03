package WebSocket.HP657.HP657.controller;

import WebSocket.HP657.HP657.entity.ChatRoomEntity;
import WebSocket.HP657.HP657.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatrooms")
public class ChatRoomApiController {

    @Autowired
    private ChatRoomService chatRoomService;

    @PostMapping("/create")
    public ResponseEntity<?> createChatroom(@RequestParam String roomName, @RequestParam Long userId1, @RequestParam Long userId2) {
        ChatRoomEntity chatRoom = chatRoomService.createChatroom(roomName, userId1, userId2);
        return ResponseEntity.ok("Chatroom created with ID: " + chatRoom.getChatRoomId());
    }
}

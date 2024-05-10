package WebSocket.HP657.HP657.api;

import WebSocket.HP657.HP657.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatroom")
public class ChatRoomApiController {
    @Autowired
    private MessageService messageService;

    //기존 채팅 가져오기
    @GetMapping("/{chatRoomId}/messages")
    public ResponseEntity<?> getMessages(@PathVariable Long chatRoomId) {
        return ResponseEntity.ok(messageService.getMessagesByChatRoomId(chatRoomId));
    }
}

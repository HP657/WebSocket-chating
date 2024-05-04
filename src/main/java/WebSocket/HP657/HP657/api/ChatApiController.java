package WebSocket.HP657.HP657.api;

import WebSocket.HP657.HP657.dto.ChatMessageDto;
import WebSocket.HP657.HP657.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class ChatApiController {
    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<Void> postMessage(@RequestBody ChatMessageDto chatMessageDto) {
        chatService.saveMessage(chatMessageDto, chatMessageDto.getChatRoomId().toString());
        return ResponseEntity.ok().build();
    }
}

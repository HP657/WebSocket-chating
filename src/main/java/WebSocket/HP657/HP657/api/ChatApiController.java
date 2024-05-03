package WebSocket.HP657.HP657.api;

import WebSocket.HP657.HP657.dto.ChatMessageDto;
import WebSocket.HP657.HP657.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class ChatApiController {
    private final ChatService chatService;

    @Autowired
    public ChatApiController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<Void> postMessage(@RequestBody ChatMessageDto chatMessageDto) {
        chatService.saveMessage(chatMessageDto);
        return ResponseEntity.ok().build();
    }

}

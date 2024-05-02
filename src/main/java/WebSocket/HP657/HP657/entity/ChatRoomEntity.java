package WebSocket.HP657.HP657.entity;

import WebSocket.HP657.HP657.dto.ChatMessageDto;
import WebSocket.HP657.HP657.service.ChatService;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chattingroom")
public class ChatRoom {
    private Long id;
    private String name;
    private static Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void handlerActions(WebSocketSession session, ChatMessageDto chatMessageDto, ChatService chatService) {
        if (chatMessageDto.getType().equals(ChatMessageDto.MessageType.ENTER)) {
            sessions.add(session);
        }
        chatService.saveMessage(chatMessageDto);
        sendMessage(chatMessageDto, chatService);
    }

    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }

    public static void deleteSession(WebSocketSession webSocketSession){
        sessions.remove(webSocketSession);
    }
}
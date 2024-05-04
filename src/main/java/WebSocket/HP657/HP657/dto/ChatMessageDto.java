package WebSocket.HP657.HP657.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ChatMessageDto {
    private Long chatRoomId;  // 채팅방 ID 추가
    private String sender;
    private String content;
    private Instant timestamp;
}

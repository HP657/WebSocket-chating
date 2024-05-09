package WebSocket.HP657.HP657.dto;

import WebSocket.HP657.HP657.entity.MessageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private Long userId;
    private String message;
    private Long chatRoomId;
}

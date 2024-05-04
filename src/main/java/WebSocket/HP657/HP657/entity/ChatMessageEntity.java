package WebSocket.HP657.HP657.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
public class ChatMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    private String content;
    private Instant timestamp;

    @ManyToOne(fetch = FetchType.LAZY) // 채팅 메시지와 채팅방 간의 관계는 '다대일' 관계입니다.
    @JoinColumn(name = "chat_room_id") // 이 필드는 외래 키로 채팅방을 참조합니다.
    private ChatRoomEntity chatRoom;

    // chatRoom 필드의 setter 메서드
    public void setChatRoom(ChatRoomEntity chatRoom) {
        this.chatRoom = chatRoom;
    }
}

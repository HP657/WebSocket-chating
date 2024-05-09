package WebSocket.HP657.HP657.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@Table(name = "chat_rooms")
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;
    private String chatroomname;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "room_members",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    private Set<UserEntity> members = new HashSet<>();

    // Lombok을 이용한 Builder 패턴 설정 예제
    @Builder
    public ChatRoomEntity(Long chatRoomId, String chatroomname, Set<UserEntity> members) {
        this.chatRoomId = chatRoomId;
        this.chatroomname = chatroomname;
        this.members = members != null ? members : new HashSet<>();
    }
}

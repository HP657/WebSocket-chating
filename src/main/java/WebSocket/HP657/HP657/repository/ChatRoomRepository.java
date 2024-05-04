package WebSocket.HP657.HP657.repository;

import WebSocket.HP657.HP657.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
    ChatRoomEntity findByName(String name);
}

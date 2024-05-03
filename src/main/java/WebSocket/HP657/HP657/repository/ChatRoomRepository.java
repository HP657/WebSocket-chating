package WebSocket.HP657.HP657.repository;

import WebSocket.HP657.HP657.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
    // 여기에 필요한 쿼리 메소드를 추가할 수 있습니다.
}

package WebSocket.HP657.HP657.repository;

import WebSocket.HP657.HP657.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
}

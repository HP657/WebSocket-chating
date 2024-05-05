package WebSocket.HP657.HP657.repository;

import WebSocket.HP657.HP657.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
}


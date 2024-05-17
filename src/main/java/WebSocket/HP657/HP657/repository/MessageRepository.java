package WebSocket.HP657.HP657.repository;

import WebSocket.HP657.HP657.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findByChatRoomIdOrderByTimestampAsc(Long chatRoomId);
}


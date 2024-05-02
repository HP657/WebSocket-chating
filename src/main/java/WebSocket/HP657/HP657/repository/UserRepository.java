package WebSocket.HP657.HP657.repository;

import WebSocket.HP657.HP657.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findUserByEmail(String email);
}
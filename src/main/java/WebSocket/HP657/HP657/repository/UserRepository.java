package WebSocket.HP657.HP657.repository;

import WebSocket.HP657.HP657.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findUserByEmail(String email);

    Optional<UserEntity> findByUsername(String username);
}
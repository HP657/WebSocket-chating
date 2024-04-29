package WebSocket.HP657.HP657.service;

import WebSocket.HP657.HP657.dto.UserDto;
import WebSocket.HP657.HP657.entity.UserEntity;
import WebSocket.HP657.HP657.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity registerNewUser(UserDto userDto) {
        UserEntity user = new UserEntity(
                userDto.getUsername(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword())
        );
        return userRepository.save(user);
    }

    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }
}


package WebSocket.HP657.HP657.service;

import WebSocket.HP657.HP657.entity.UserEntity;
import WebSocket.HP657.HP657.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity findUserBySession(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            throw new IllegalStateException("로그인 중이 아님");
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(userId + "번 유저아이디 찾을수 없음"));
    }


    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<UserEntity> findAllUsers() {
        return (List<UserEntity>) userRepository.findAll();
    }
}
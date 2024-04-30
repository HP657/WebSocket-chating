package WebSocket.HP657.HP657.service;

import WebSocket.HP657.HP657.dto.SignInDto;
import WebSocket.HP657.HP657.dto.SignUpDto;
import WebSocket.HP657.HP657.entity.UserEntity;
import WebSocket.HP657.HP657.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
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

    public UserEntity signup(SignUpDto signupDto) {
        UserEntity user = new UserEntity(
                signupDto.getUsername(),
                signupDto.getEmail(),
                passwordEncoder.encode(signupDto.getPassword())
        );
        return userRepository.save(user);
    }
    public String signin(SignInDto signinDto, HttpServletRequest request) {
        UserEntity userByEmail = userRepository.findUserByEmail(signinDto.getEmail());
        System.out.println(userByEmail);
        if (userByEmail == null) {
            return "존재하지 않는 사용자입니다.";
        } else {
            try {
                String submittedPassword = signinDto.getPassword();
                System.out.println(submittedPassword);
                System.out.println(userByEmail.getPassword());
                if (passwordEncoder.matches(submittedPassword, userByEmail.getPassword())) {
                    request.getSession().setAttribute("userId", userByEmail.getUserId());
                    return "로그인 성공";
                } else {
                    return "비밀번호가 일치하지 않습니다.";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "로그인 처리 중 오류가 발생했습니다.";
            }
        }
    }

    public UserEntity findUserBySession(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            throw new IllegalStateException("No user logged in");
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No user found with ID: " + userId));
    }

    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }
}


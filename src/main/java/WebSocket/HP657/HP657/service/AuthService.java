package WebSocket.HP657.HP657.service;

import WebSocket.HP657.HP657.dto.Response;
import WebSocket.HP657.HP657.dto.SignInDto;
import WebSocket.HP657.HP657.dto.SignUpDto;
import WebSocket.HP657.HP657.entity.UserEntity;
import WebSocket.HP657.HP657.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<Response<String>> signup(SignUpDto signUpDto) {
        UserEntity user = new UserEntity(
                signUpDto.getUsername(),
                signUpDto.getEmail(),
                passwordEncoder.encode(signUpDto.getPassword())
        );
        userRepository.save(user);
        return new Response<>("회원가입 성공적", HttpStatus.OK).toResponseEntity();
    }

    public String signin(SignInDto signinDto, HttpServletRequest request) {
        UserEntity userByEmail = userRepository.findUserByEmail(signinDto.getEmail());
        System.out.println(userByEmail);
        if (userByEmail == null) {
            return "존재하지 않는 사용자임";
        } else {
            try {
                String submittedPassword = signinDto.getPassword();
                System.out.println(submittedPassword);
                System.out.println(userByEmail.getPassword());
                if (passwordEncoder.matches(submittedPassword, userByEmail.getPassword())) {
                    request.getSession().setAttribute("userId", userByEmail.getUserId());
                    return "로그인 성공적";
                } else {
                    return "비밀번호가 일치하지 않음";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "로그인 중 오류가 발생함";
            }
        }
    }

    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "로그아웃 성공적";
    }
}


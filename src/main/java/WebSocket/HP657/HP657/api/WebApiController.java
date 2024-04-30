package WebSocket.HP657.HP657.api;

import WebSocket.HP657.HP657.dto.SignUpDto;
import WebSocket.HP657.HP657.dto.SignInDto;
import WebSocket.HP657.HP657.entity.UserEntity;
import WebSocket.HP657.HP657.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class WebApiController {

    @Autowired
    private UserService userService;

    //회원가입
    @PostMapping("/signup")
    public String signUpUser(@RequestBody SignUpDto signupDto) {
        return userService.signup(signupDto);
    }

    //로그인
    @PostMapping("/signin")
    public String signInUser(@RequestBody SignInDto signinDto, HttpServletRequest request) {
        return userService.signin(signinDto, request);
    }

    //로그아웃
    @GetMapping("/logout")
    public String logOutUser(HttpServletRequest request) {
        return userService.logout(request);
    }

    //id로 회원 정보
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        Optional<UserEntity> user = userService.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //로그인중인 회원정보
    @GetMapping("/user/info")
    public UserEntity getUserInfo(HttpServletRequest request) {
        return userService.findUserBySession(request);
    }
}


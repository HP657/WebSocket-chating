package WebSocket.HP657.HP657.api;

import WebSocket.HP657.HP657.dto.SignUpDto;
import WebSocket.HP657.HP657.dto.SignInDto;
import WebSocket.HP657.HP657.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    @Autowired
    private AuthService authService;

    //회원가입
    @PostMapping("/signup")
    public String signUpUser(@RequestBody SignUpDto signupDto) {
        return authService.signup(signupDto);
    }

    //로그인
    @PostMapping("/signin")
    public String signInUser(@RequestBody SignInDto signinDto, HttpServletRequest request) {
        return authService.signin(signinDto, request);
    }

    //로그아웃
    @GetMapping("/logout")
    public String logOutUser(HttpServletRequest request) {
        return authService.logout(request);
    }

}
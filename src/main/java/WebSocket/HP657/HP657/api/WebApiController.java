package WebSocket.HP657.HP657.api;

import WebSocket.HP657.HP657.dto.Response;
import WebSocket.HP657.HP657.entity.UserEntity;
import WebSocket.HP657.HP657.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class WebApiController {

    @Autowired
    private UserService userService;

    //id로 회원 정보
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        Optional<UserEntity> user = userService.findById(id).getData();
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //로그인중인 회원정보
    @GetMapping("/info")
    public ResponseEntity<? extends Response<?>> getUserInfo(HttpServletRequest request) {
        return userService.findUserBySession(request).toResponseEntity();
    }

    // 모든 회원정보
    @GetMapping("/users")
    public ResponseEntity<Response<List<UserEntity>>> getAllUsers() {
        return userService.findAllUsers().toResponseEntity();
    }
}
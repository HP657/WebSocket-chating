package WebSocket.HP657.HP657.api;

import WebSocket.HP657.HP657.dto.UserDto;
import WebSocket.HP657.HP657.entity.UserEntity;
import WebSocket.HP657.HP657.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class WebApiController {

    @Autowired
    private UserService userService;

    // POST 요청을 통한 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserDto userDto) {
        UserEntity registeredUser = userService.registerNewUser(userDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    // 사용자 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        Optional<UserEntity> user = userService.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


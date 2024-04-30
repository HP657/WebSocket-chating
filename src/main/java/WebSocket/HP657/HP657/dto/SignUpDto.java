package WebSocket.HP657.HP657.dto;


import WebSocket.HP657.HP657.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {
    private String username;
    private String email;
    private String password;


    public UserEntity toEntity() {
        return UserEntity.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}

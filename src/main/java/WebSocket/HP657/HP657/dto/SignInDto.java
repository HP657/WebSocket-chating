package WebSocket.HP657.HP657.dto;

import WebSocket.HP657.HP657.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignInDto {
    private String email;
    private String password;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .email(email)
                .password(password)
                .build();
    }
}

package WebSocket.HP657.HP657.dto;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
public class Response<T> {
    private T data;
    private int status;

    public Response(T data, HttpStatus status) {
        this.data = data;
        this.status = status.value();
    }

    public ResponseEntity<Response<T>> toResponseEntity() {
        return ResponseEntity.status(this.status).body(this);
    }
}
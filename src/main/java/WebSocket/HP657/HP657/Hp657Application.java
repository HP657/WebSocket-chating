package WebSocket.HP657.HP657;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Hp657Application {

	public static void main(String[] args) {
		SpringApplication.run(Hp657Application.class, args);
	}

}

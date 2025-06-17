package in.tech_camp.protospace;

import org.mybatis.spring.annotation.MapperScan; // ←これを追加！
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("in.tech_camp.protospace.repository") // ←これを追加！
@SpringBootApplication
public class ProtospaceKcsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProtospaceKcsApplication.class, args);
	}

}

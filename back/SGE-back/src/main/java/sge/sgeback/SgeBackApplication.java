package sge.sgeback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class SgeBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SgeBackApplication.class, args);
	}

}
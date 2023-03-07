package com.uj15.timedeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TimeDealApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeDealApplication.class, args);
	}
}

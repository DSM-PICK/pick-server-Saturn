package com.dsm.pick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PickApplication {

//	@PostConstruct
//	public void setTimeZone() {
//		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
//	}

	public static void main(String[] args) {
		SpringApplication.run(PickApplication.class, args);
	}
}
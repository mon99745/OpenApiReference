package com.shop.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 스프링 부트에서 스케줄러가 작동하게 함
@EnableJpaAuditing // 시간 변경을 자동으로
@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

}

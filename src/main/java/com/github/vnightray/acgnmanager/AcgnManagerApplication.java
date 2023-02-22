package com.github.vnightray.acgnmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.github.vnightray.acgnmanager.mapper")
public class AcgnManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(AcgnManagerApplication.class, args);
	}
}

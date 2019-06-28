package com.example.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目学习地址https://github.com/macrozheng/mall-admin-web
 */
@SpringBootApplication
@MapperScan("com.example.mall.mapper")
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
        System.out.println(121212212);
	}

}

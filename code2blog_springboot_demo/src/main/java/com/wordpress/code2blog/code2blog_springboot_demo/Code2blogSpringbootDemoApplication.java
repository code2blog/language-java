package com.wordpress.code2blog.code2blog_springboot_demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Code2blogSpringbootDemoApplication {
	static Logger logger = LogManager.getLogger(Code2blogSpringbootDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(Code2blogSpringbootDemoApplication.class, args);
		logger.info("welcome to boot...");
	}

}

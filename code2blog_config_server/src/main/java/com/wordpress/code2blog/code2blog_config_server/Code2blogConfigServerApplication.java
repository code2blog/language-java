package com.wordpress.code2blog.code2blog_config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class Code2blogConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Code2blogConfigServerApplication.class, args);
	}

}

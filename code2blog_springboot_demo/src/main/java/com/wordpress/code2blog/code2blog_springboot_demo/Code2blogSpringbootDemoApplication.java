package com.wordpress.code2blog.code2blog_springboot_demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Code2blogSpringbootDemoApplication implements CommandLineRunner {
    static Logger logger = LogManager.getLogger(Code2blogSpringbootDemoApplication.class);

    @Value("${myapp.environment}")
    String environment;

    @Autowired
    MyProperties myProps;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Code2blogSpringbootDemoApplication.class, args);
        MySpringBean mySpringBean = context.getBean("mySpringBean", MySpringBean.class);
        logger.info(String.format("mySpringBean says i am in [%s] environment", mySpringBean.getEnvironment()));
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(String.format("Value annotation says i am in [%s] environment", environment));
        logger.info(String.format("ConfigurationProperties annotation says i am in [%s] environment", myProps.getEnvironment()));
    }
}

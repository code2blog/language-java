package com.wordpress.code2blog.code2blog_springboot_demo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MyChildBean {

    public MyChildBean(){
        log.info("child bean created.");
    }

    public void sayHello(){
        log.info("child bean says hello");
    }
}

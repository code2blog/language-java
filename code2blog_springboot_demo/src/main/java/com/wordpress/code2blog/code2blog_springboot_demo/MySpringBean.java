package com.wordpress.code2blog.code2blog_springboot_demo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MySpringBean {
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

    public void show(){
       log.info("hello from show() method of my spring bean");
    }
}

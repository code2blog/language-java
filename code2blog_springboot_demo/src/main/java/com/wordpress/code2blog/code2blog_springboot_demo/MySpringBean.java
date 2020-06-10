package com.wordpress.code2blog.code2blog_springboot_demo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@ConfigurationProperties(prefix = "forspringbean")
@PropertySource(value = "classpath:myCustom.properties")
public class MySpringBean {

    @Getter
    @Setter
    String contentType;

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

    @Autowired
    @Getter
    @Setter
    MyChildBean child;

    public MySpringBean(){
        log.info("spring bean created");
    }

    public void show(){
       log.info("hello from show() method of my spring bean");
       child.sayHello();
    }
}

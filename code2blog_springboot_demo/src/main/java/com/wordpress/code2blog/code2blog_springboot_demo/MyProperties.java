package com.wordpress.code2blog.code2blog_springboot_demo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "myapp")
@PropertySource(value = "classpath:application.properties")
public class MyProperties {

    @Getter
    @Setter
    String environment;

    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    String version;

}

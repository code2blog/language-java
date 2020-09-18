package com.wordpress.code2blog.code2blog_config_client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("limits")
public class Configuration {
	private int minimum;
	private int maximum;
}

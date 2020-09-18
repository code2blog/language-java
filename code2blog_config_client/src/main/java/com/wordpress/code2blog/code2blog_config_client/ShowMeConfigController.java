package com.wordpress.code2blog.code2blog_config_client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/view")
public class ShowMeConfigController {

    @Value("${name}")
    String name;

    @Autowired
    private Configuration configuration;

    @GetMapping("/name")
    public String getValueAnnotatedConfig() {
        return name;
    }

    @GetMapping("/minMax")
    public String getConfiguracionPropertiesAnnotatedConfig() {
        return String.format("from min value=%d upto max value=%d", configuration.getMinimum(), configuration.getMaximum());
    }

}

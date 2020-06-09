package com.wordpress.code2blog.code2blog_springboot_jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

    @Autowired
    MyRepository repository;

    @RequestMapping("/addToTable")
    @ResponseBody
    public String addToTable(@RequestParam String name) {
        MyEntity entity = new MyEntity();
        entity.setName(name);
        repository.save(entity);
        return String.format("[%s] added to table successfully", name);
    }
}

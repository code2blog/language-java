package com.wordpress.code2blog.code2blog_springboot_jpa;

import org.springframework.data.repository.CrudRepository;

public interface MyRepository extends CrudRepository<MyEntity, Long> {
}

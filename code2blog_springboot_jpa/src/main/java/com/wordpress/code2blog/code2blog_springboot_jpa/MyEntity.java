package com.wordpress.code2blog.code2blog_springboot_jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class MyEntity {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "MY_ENTITY_ID_SEQ")
    private Long id;

    @Getter
    @Setter
    private String name;

}

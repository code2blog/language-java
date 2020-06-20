package com.wordpress.code2blog.code2blog_springboot_dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// use repository to write custom queries
@Repository
public class DynamoDbRepository {

	@Autowired
	@Getter
	@Setter
	private DynamoDBMapper mapper;

}
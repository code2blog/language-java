package com.wordpress.code2blog.code2blog_springboot_dynamodb;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dynamoDb")
@Log4j2
public class DynamoDbController {

	@Autowired
	@Getter
	@Setter
	private DynamoDbRepository repository;

	@GetMapping
	public ResponseEntity<UserModel> getOneStudentDetails(@RequestParam String userId) {
		UserModel user = repository.getMapper().load(UserModel.class, userId);
		log.info(String.format("Search using userId=[%s], returned [%s]", userId, user));
		return new ResponseEntity<UserModel>(user, HttpStatus.OK);
	}

}
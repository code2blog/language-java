package com.wordpress.code2blog.code2blog_springboot_dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

// use repository to write custom queries
@Repository
@Log4j2
public class DynamoDbRepository {

	@Autowired
	@Getter
	@Setter
	private DynamoDBMapper mapper;

	public void updateUser(UserModel user) {
		mapper.save(user, buildDynamoDBSaveExpression(user));
		log.info(String.format("user [%s] updated successfully",user));
	}

	public DynamoDBSaveExpression buildDynamoDBSaveExpression(UserModel user) {
		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
		Map<String, ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("UserId", new ExpectedAttributeValue(new AttributeValue(user.getUserId()))
				.withComparisonOperator(ComparisonOperator.EQ));
		saveExpression.setExpected(expected);
		return saveExpression;
	}
}
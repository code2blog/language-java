package code2blog.jsp_map_dropdown;

import org.junit.Test;

public class TestJsonCreation {
	
	@Test
	public void should_create_json_when_class_SomeClassRepresentingYourAccessLayerToDB_is_invoked() {
		String jsonStringOfMap = SomeClassRepresentingYourAccessLayerToDB.getJsonStringOfMap();
		System.out.println(jsonStringOfMap);
		assert jsonStringOfMap.contains("{");
	}
}

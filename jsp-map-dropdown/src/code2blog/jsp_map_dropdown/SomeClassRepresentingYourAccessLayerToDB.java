package code2blog.jsp_map_dropdown;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;

public class SomeClassRepresentingYourAccessLayerToDB {
	
	public static String getJsonStringOfMap(){
		Map map = new LinkedHashMap();
		map.put("India", new String[]{"Karnataka","Kerala","Tamilnadu"});
		map.put("SouthAfrica", new String[]{"Capetown","Johnnesburg","Pretoria"});
		map.put("USA", new String[]{"NewYork","Texas"});
		return new Gson().toJson(map);
	}
}

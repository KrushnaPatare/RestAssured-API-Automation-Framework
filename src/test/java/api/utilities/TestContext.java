package api.utilities;

import java.util.HashMap;
import java.util.Map;

public class TestContext {
	
	public static String petExelPath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\excelDataFiles\\SchemaValidation.xlsx";
	
	public static Map<String, String> header = new HashMap<String, String>();
	public static Map<String, String> getAppJsonHeader()
	{
		header.put("Accept", "application/json");
		header.put("Content-Type", "application/json");
		return header;
	}
	
	public static Map<String, Integer> param = new HashMap<String, Integer>();
	public static Map<String, Integer> getTimeoutParam()
	{
		param.put("http.connection.timeout", 5000);
		param.put("http.socket.timeout", 5000);
		return param;
	}

}

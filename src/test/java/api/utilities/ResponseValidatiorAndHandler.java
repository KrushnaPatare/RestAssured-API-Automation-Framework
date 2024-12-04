package api.utilities;

	import io.restassured.path.json.JsonPath;
	import io.restassured.response.Response;
	import io.restassured.module.jsv.JsonSchemaValidator;
	import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
	import java.util.concurrent.TimeUnit;

	public class ResponseValidatiorAndHandler {


	    // Validate HTTP Status Code
	    public static void validateStatusCode(Response response, int expectedStatusCode) 
	    {
	        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, 
	                "Unexpected status code: " + response.getBody().asString());
	    }

	    // Validate Response Time
	    public static void validateResponseTime(Response response, long maxResponseTimeMs) 
	    {
	        Assert.assertTrue(response.getTimeIn(TimeUnit.MILLISECONDS) <= maxResponseTimeMs, 
	                "Response time exceeded: " + response.getTimeIn(TimeUnit.MILLISECONDS) + "ms");
	    }

	    // Validate Key Existence in JSON
	    public static void validateJsonKeyExists(Response response, String key) 
	    {
	        JsonPath jsonPath = response.jsonPath();
	        Assert.assertTrue(jsonPath.get(key) != null, "Key not found in response: " + key);
	    }

	    // Validate JSON Value
	    public static void validateJsonValue(Response response, String key, Object expectedValue) 
	    {
	        JsonPath jsonPath = response.jsonPath();
	        Object actualValue = jsonPath.get(key);
	        Assert.assertEquals(actualValue, expectedValue, 
	                "Value mismatch for key '" + key + "': Expected=" + expectedValue + ", Actual=" + actualValue);
	    }

	    // Validate JSON Schema
	    public static void validateJsonSchema(Response response, File schemaFile) 
	    {
	        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
	    }

	    // Validate Content-Type Header
	    public static void validateContentType(Response response, String expectedContentType) 
	    {
	        Assert.assertEquals(response.getContentType(), expectedContentType, 
	                "Unexpected Content-Type: " + response.getContentType());
	    }

	    // Validate Headers
	    public static void validateHeader(Response response, String headerName, String expectedValue) 
	    {
	        String actualValue = response.getHeader(headerName);
	        Assert.assertEquals(actualValue, expectedValue, 
	                "Header mismatch for '" + headerName + "': Expected=" + expectedValue + ", Actual=" + actualValue);
	    }

	    // Validate Response Body Contains Value
	    public static void validateBodyContains(Response response, String expectedValue) 
	    {
	        String responseBody = response.getBody().asString();
	        Assert.assertTrue(responseBody.contains(expectedValue), 
	                "Response body does not contain expected value: " + expectedValue);
	    }

	    // Validate Array Size
	    public static void validateJsonArraySize(Response response, String jsonArrayPath, int expectedSize) 
	    {
	        List<?> jsonArray = response.jsonPath().getList(jsonArrayPath);
	        Assert.assertEquals(jsonArray.size(), expectedSize, 
	                "Unexpected array size for path '" + jsonArrayPath + "': " + jsonArray.size());
	    }

	    // Validate Non-Empty Response
	    public static void validateNonEmptyResponse(Response response) 
	    {
	        Assert.assertFalse(response.getBody().asString().isEmpty(), 
	                "Response body is empty!");
	    }

	    // Validate Nested JSON Value
	    public static void validateNestedJsonValue(Response response, String nestedKeyPath, Object expectedValue) 
	    {
	        JsonPath jsonPath = response.jsonPath();
	        Object actualValue = jsonPath.get(nestedKeyPath);
	        Assert.assertEquals(actualValue, expectedValue, 
	                "Mismatch for nested key path '" + nestedKeyPath + "': Expected=" + expectedValue + ", Actual=" + actualValue);
	    }

	    // Validate Status Line
	    public static void validateStatusLine(Response response, String expectedStatusLine) 
	    {
	        Assert.assertEquals(response.getStatusLine(), expectedStatusLine, 
	                "Unexpected status line: " + response.getStatusLine());
	    }

	    // Validate Response is JSON
	    public static void validateResponseIsJson(Response response) 
	    {
	        Assert.assertTrue(response.getContentType().contains("application/json"), 
	                "Response is not JSON: " + response.getContentType());
	    }

	    // Validate Query Parameter in Response
	    public static void validateQueryParam(Response response, String param, Object expectedValue) 
	    {
	        String actualValue = response.jsonPath().getString(param);
	        Assert.assertEquals(actualValue, expectedValue, 
	                "Mismatch for query parameter '" + param + "': Expected=" + expectedValue + ", Actual=" + actualValue);
	    }

	    // Validate Empty JSON Array
	    public static void validateEmptyJsonArray(Response response, String jsonArrayPath) 
	    {
	        List<?> jsonArray = response.jsonPath().getList(jsonArrayPath);
	        Assert.assertTrue(jsonArray.isEmpty(), 
	                "Expected empty array for path '" + jsonArrayPath + "', but found: " + jsonArray.size());
	    }
	
	    
	    
	    
	    
	    public static <T> T  deserializedResponse(Response response, Class T )
		{
			ObjectMapper mapper = new ObjectMapper();
			T responseDeserialized = null;
			try {
				responseDeserialized = (T) mapper.readValue(response.asString(), T);
				String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDeserialized); // Pretty print JSON
				System.out.println("Handling Response: \n"+responseDeserialized.toString());
			} catch (IOException e) {
				e.printStackTrace(System.out);
			}
			return responseDeserialized;
		}



}

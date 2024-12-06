package api.test;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.path.Routes;
import api.payload.User;
import api.utilities.ExtentReportManager;
import api.utilities.RequestResponseFilter;
import api.utilities.TestContext;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

@Listeners(api.utilities.ExtentReportManager.class)
public class UserTest {
	
	Logger logger;
	Faker faker;
	User userpayload;
	
	
	@BeforeClass
	public void setUpData()
	{
		logger = LogManager.getLogger(this.getClass());
		faker = new Faker();
		userpayload = new User();
		
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setPassword(faker.internet().password());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPhone(faker.phoneNumber().cellPhone());
	}
	
	@BeforeMethod()
	public void globalSetUP() 
	{
		RestAssured.baseURI=Routes.base_url;
//		RestAssured.basePath = "/v2";
		RestAssured.requestSpecification = new RequestSpecBuilder()
				.setConfig(RestAssured.config()
			    .httpClient(HttpClientConfig.httpClientConfig()
                .setParams(TestContext.getTimeoutParam())))
			    .addHeaders(TestContext.getAppJsonHeader())
			    .build();
		Options options = Options.builder().logStacktrace().build();
		RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig(options);
		 RestAssured.config = config;
		RestAssured.filters(new RequestResponseFilter());
	}
	
	
	@AfterMethod()
	public void tearDown() 
	{
		RestAssured.reset();
	}
	
		
	//Post user api	
		@Test(priority = 1, description = "This test verifies the Create User POST response status code")
	public void createUser_shouldReturn200StatusCode() {
	    
	    logger.info("****************************************************************");
	    logger.info("*****<<<<<Starting createUser_shouldReturn200StatusCode>>>>>*****");
	
	    try {
	        // Sending POST request to create a new user
	        logger.info("Sending POST request to create a new user");
	        Response postUserResponse = UserEndpoints.createUser(userpayload);
	
	        // Extracting and logging response time
	        long responseTime = postUserResponse.getTime();
	       
	        // Assertion for response time
	        Assert.assertTrue(responseTime >= 200 && responseTime <= 2000, "Response time is outside the expected range (200-2000ms).");
	        Assert.assertEquals(postUserResponse.getStatusCode(), 200, "Expected status code is 200.");
	        Assert.assertEquals(postUserResponse.getHeader("Content-Type"), "application/json", "Expected content type is application/json.");
	
	        // Extracting and validating field types from response
	        String jsonBody = postUserResponse.getBody().asString();
	        JSONObject jsonObj = new JSONObject(jsonBody);
	        Object codeField = jsonObj.get("code");
	        Object typeField = jsonObj.get("type");
	        Object messageField = jsonObj.get("message");
	
	        logger.info("Asserting response field types");
	        Assert.assertTrue(codeField instanceof Integer, "Code field should be Integer.");
	        Assert.assertTrue(typeField instanceof String, "Type field should be String.");
	        Assert.assertTrue(messageField instanceof String, "Message field should be String.");
	        
	     // Extracting and logging field values
	        int code = postUserResponse.then().extract().path("code");
	        String type = postUserResponse.then().extract().path("type");
	
	        // Assertion for field values
	        Assert.assertEquals(code, 200, "Expected code is 200.");
	        Assert.assertEquals(type, "unknown", "Expected type is 'unknown'.");
	        

	        // Path to JSON schema
	        String jsonFilePath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\jsonSchemaFiles\\postUserSchema.json";
	        File schemaFile = new File(jsonFilePath);
	
	        // Schema validation
	        postUserResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
	        
	    } catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } catch (Exception e) {
	        logger.error("Unexpected exception occurred: {}", e.getMessage());
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } finally {
	        logger.info("***** Finished createUser_shouldReturn200StatusCode *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
		
		/*
	@Test(priority = 6, description = "This test verifies the response status code when creating a duplicate user")
	public void createDuplicateUser_shouldReturn200StatusCode() {
	
	    logger.info("********************************************");
	    logger.info("***** Starting createDuplicateUser_shouldReturn200StatusCode *****");
	
	    try {
	        // Sending POST request to create a duplicate user
	        logger.info("Sending POST request to create a duplicate user");
	        Response postUserResponse = UserEndpoints.createUser(userpayload);
	        postUserResponse.then().log().all();
	
	        // Extracting and logging status code
	        int statusCode = post
	
	*/
	
		
	//Get user api	
	@Test(priority = 2, description = "This test verifies that GET user response time is within the expected range")
	public void getUser_shouldRespondWithinExpectedTime() 
	{
	    logger.info("***** Starting getUser_shouldRespondWithinExpectedTime *****");
	    ExtentReportManager.test.info("Test for GET user response time started");
	
	    try {
	        Response getUserResponse = UserEndpoints.readUser(userpayload.getUsername());

	        // Log and extract response time
	        long responseTime = getUserResponse.getTime();
	        String contentType = getUserResponse.getHeader("Content-Type");
	        String jsonBody = getUserResponse.body().asString();
	        JSONObject jsonObj = new JSONObject(jsonBody);

	        // Assertion for response time
	        Assert.assertTrue(responseTime >= 200 && responseTime <= 1500, "Response time is outside the expected range.");
	        Assert.assertEquals(contentType, "application/json", "Expected Content-Type is application/json.");
	        Assert.assertTrue(jsonObj.get("id") instanceof Integer, "Id field should be integer.");
	        Assert.assertTrue(jsonObj.get("username") instanceof String, "Username field should be String.");
	        Assert.assertTrue(jsonObj.get("firstName") instanceof String, "FirstName field should be String.");
	        Assert.assertTrue(jsonObj.get("lastName") instanceof String, "LastName field should be String.");
	        Assert.assertTrue(jsonObj.get("email") instanceof String, "Email field should be String.");
	        Assert.assertTrue(jsonObj.get("password") instanceof String, "Password field should be String.");
	        Assert.assertTrue(jsonObj.get("phone") instanceof String, "Phone field should be String.");
	        Assert.assertTrue(jsonObj.get("userStatus") instanceof Integer, "UserStatus field should be Integer.");
	       
	        String username = getUserResponse.then().extract().path("username");
	        String firstName = getUserResponse.then().extract().path("firstName");
	        String lastName = getUserResponse.then().extract().path("lastName");
	        String email = getUserResponse.then().extract().path("email");
	        String password = getUserResponse.then().extract().path("password");
	        String phone = getUserResponse.then().extract().path("phone");
	        int userStatus = getUserResponse.then().extract().path("userStatus");
	
	        // Assertions for field values
	        Assert.assertEquals(username, userpayload.getUsername(), "Username mismatch.");
	        Assert.assertEquals(firstName, userpayload.getFirstName(), "FirstName mismatch.");
	        Assert.assertEquals(lastName, userpayload.getLastName(), "LastName mismatch.");
	        Assert.assertEquals(email, userpayload.getEmail(), "Email mismatch.");
	        Assert.assertEquals(password, userpayload.getPassword(), "Password mismatch.");
	        Assert.assertEquals(phone, userpayload.getPhone(), "Phone mismatch.");
	        Assert.assertEquals(userStatus, userpayload.getUserStatus(), "UserStatus mismatch.");
	        
	    } 
	    catch (Exception e) {
	        logger.error("Unexpected exception occurred: {}", e.getMessage());
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished getUser_shouldRespondWithinExpectedTime *****");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	
	
	
	
/*	@Test(priority = 12, description = "This test verifies that a GET request with an invalid username returns 404 error")
	public void getUser_withInvalidUsername_shouldReturn404Error() 
	{
	    logger.info("***** Starting getUser_withInvalidUsername_shouldReturn404Error *****");
	    ExtentReportManager.test.info("Test for GET user with invalid username started");
	
	    try {
	        // Sending GET request with an invalid username
	        String wrongUsername = "wrongUsername";
	        Response getUserResponse = UserEndpoints.readUser(wrongUsername);
	        getUserResponse.then().log().all();
	
	        // Extracting fields from the response
	        int statusCode = getUserResponse.getStatusCode();
	        int code = getUserResponse.then().extract().path("code");
	        String type = getUserResponse.then().extract().path("type");
	        String message = getUserResponse.then().extract().path("message");
	        long responseTime = getUserResponse.getTime();
	
	        // Logging the response details
	        logger.info("Response code: {}, Type: {}, Message: {}", code, type, message);
	        ExtentReportManager.test.info("Response code: " + code + ", Type: " + type + ", Message: " + message);
	
	        // Assertions for response details
	        Assert.assertEquals(statusCode, 404, "Expected status code is 404.");
	        Assert.assertEquals(code, 1, "Expected error code is 1.");
	        Assert.assertEquals(type, "error", "Expected error type is 'error'.");
	        Assert.assertEquals(message, "User not found", "Expected error message is 'User not found'.");
	        Assert.assertTrue(responseTime >= 200 && responseTime <= 1500, "Response time is outside the expected range.");
	        ExtentReportManager.test.pass("Assertions for 404 error response passed");
	    } 
	    catch (Exception e) {
	        logger.error("Unexpected exception occurred: {}", e.getMessage());
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished getUser
*/	
		
		
		
		
		
		
		
	//Put user api	
		 		
        		
	@Test(priority = 3, description = "This test verifies the successful update of a user and returns 200 status code")
	public void updateUser_shouldReturn200StatusCode() 
	{
	    // Start the test and log the initial info
	    ExtentReportManager.test.info("Test for updating user started");
	    logger.info("********************************************");
	    logger.info("***** Starting updateUser_shouldReturn200StatusCode *****");
	
	    try {
	        // Update the user details with random data
	        userpayload.setFirstName(faker.name().firstName());
	        userpayload.setLastName(faker.name().lastName());
	        userpayload.setEmail(faker.internet().safeEmailAddress());
	        
	        // Send PUT request to update user
	       Response putUserResponse = UserEndpoints.updateUser(userpayload, userpayload.getUsername());
	
	        // Log and verify the response
	        int statusCode = putUserResponse.getStatusCode();
	        long resTime = putUserResponse.getTime();	
	        String contentType = putUserResponse.getHeader("Content-Type");
	        String jsonBody = putUserResponse.getBody().asPrettyString();
	        JSONObject jsonObj = new JSONObject(jsonBody);
	
	        // Assert status code
	        Assert.assertEquals(statusCode, 200, "Expected status code is 200.");
	        Assert.assertTrue(resTime >= 200 && resTime <= 2000, "Response time is outside the expected range (200-2000ms).");
	    	Assert.assertEquals(contentType, "application/json", "Expected Content-Type is application/json.");
	    	
	    	// Extract fields and assert their types
	        Assert.assertTrue(jsonObj.get("id") instanceof Integer, "Id field should be an integer.");
	        Assert.assertTrue(jsonObj.get("username") instanceof String, "Username field should be a string.");
	        Assert.assertTrue(jsonObj.get("firstName") instanceof String, "FirstName field should be a string.");
	        Assert.assertTrue(jsonObj.get("lastName") instanceof String, "LastName field should be a string.");
	        Assert.assertTrue(jsonObj.get("email") instanceof String, "Email field should be a string.");
	        Assert.assertTrue(jsonObj.get("password") instanceof String, "Password field should be a string.");
	        Assert.assertTrue(jsonObj.get("phone") instanceof String, "Phone field should be a string.");
	        Assert.assertTrue(jsonObj.get("userStatus") instanceof Integer, "UserStatus field should be an integer.");
	       
	        // Extract and assert field values
	        String username = putUserResponse.then().extract().path("username");
	        String firstName = putUserResponse.then().extract().path("firstName");
	        String lastName = putUserResponse.then().extract().path("lastName");
	        String email = putUserResponse.then().extract().path("email");
	        String password = putUserResponse.then().extract().path("password");
	        String phone = putUserResponse.then().extract().path("phone");
	        int userStatus = putUserResponse.then().extract().path("userStatus");
	
	        Assert.assertEquals(username, userpayload.getUsername());
	        Assert.assertEquals(firstName, userpayload.getFirstName());
	        Assert.assertEquals(lastName, userpayload.getLastName());
	        Assert.assertEquals(email, userpayload.getEmail());
	        Assert.assertEquals(password, userpayload.getPassword());
	        Assert.assertEquals(phone, userpayload.getPhone());
	        Assert.assertEquals(userStatus, userpayload.getUserStatus());
	        
	        String jsonFilePath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\jsonSchemaFiles\\putUserSchema.json";
	        File file = new File(jsonFilePath);
	
	        putUserResponse.then().body(JsonSchemaValidator.matchesJsonSchema(file));
	
	
	    } 
	    catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    catch (Exception e) {
	        logger.error("Unexpected exception: {}", e.getMessage());
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished updateUser_shouldReturn200StatusCode *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	
		
	
	@Test(priority = 4, description = "This test verifies how the system handles an update request with an invalid username")
	public void updateUser_withInvalidUsername_shouldHandleGracefully() 
	{
	    // Log and start the test
	    ExtentReportManager.test.info("Test for updating user with invalid username started");
	    logger.info("********************************************");
	    logger.info("***** Starting updateUser_withInvalidUsername_shouldHandleGracefully *****");
	
	    try {
	        // Use an invalid username
	        String wrongUsername = "wrongUsername";
	        logger.info("Using invalid username: {}", wrongUsername);
	        ExtentReportManager.test.info("Using invalid username: " + wrongUsername);
	
	        // Send PUT request with invalid username
	        Response putUserResponse = UserEndpoints.updateUser(userpayload, wrongUsername);
	
	        // Extract fields from response
	        int code = putUserResponse.then().extract().path("code");
	        String type = putUserResponse.then().extract().path("type");
	        long resTime = putUserResponse.getTime();
	        JSONObject jsonObj = new JSONObject(putUserResponse.body().asString());
	
	        // Assert field types and values
	        Assert.assertTrue(jsonObj.get("code") instanceof Integer, "Code field should be an integer.");
	        Assert.assertTrue(jsonObj.get("type") instanceof String, "Type field should be a string.");
	        Assert.assertTrue(jsonObj.get("message") instanceof String, "Message field should be a string.");
	        Assert.assertEquals(code, 200);
	        Assert.assertEquals(type, "unknown");
	        Assert.assertEquals(putUserResponse.getStatusCode(), 200);
	        Assert.assertTrue(resTime >= 200 && resTime <= 2000);
	
	        ExtentReportManager.test.pass("Assertions for invalid username response passed");
	    } 
	    catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished updateUser_withInvalidUsername_shouldHandleGracefully *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	
	
//Login user api	
	
	@Test(priority = 5, description = "This test verifies that logging in a user returns a 200 status code")
	public void loginUser_shouldReturn200StatusCode() {
	    logger.info("********************************************");
	    logger.info("***** Starting loginUser_shouldReturn200StatusCode *****");
	
	    try {
	        // Send login request
	        Response getLoginUserResponse = UserEndpoints.loginUser(userpayload.getUsername(), userpayload.getPassword());
	        
	        long resTime = getLoginUserResponse.getTime();
	        String jsonBody = getLoginUserResponse.body().asString();

	        // Parse JSON and check field types
	        JSONObject jsonObj = new JSONObject(jsonBody);
	        Object codeField = jsonObj.get("code");
	        Object typeField = jsonObj.get("type");
	        Object messageField = jsonObj.get("message");


	        // Log response status and body
	      	Assert.assertEquals(getLoginUserResponse.getStatusCode(), 200);
	        Assert.assertTrue(resTime >= 200 && resTime <= 1500, "Response time is outside the expected range");
	        Assert.assertEquals(getLoginUserResponse.getHeader("Content-Type"), "application/json", "Expected Content-Type is application/json");

	        Assert.assertTrue(codeField instanceof Integer, "Code field should be an Integer.");
	        Assert.assertTrue(typeField instanceof String, "Type field should be a String.");
	        Assert.assertTrue(messageField instanceof String, "Message field should be a String.");
	        
	     // Extract field values from the response
	        int code = getLoginUserResponse.then().extract().path("code");
	        String type = getLoginUserResponse.then().extract().path("type");
	        String message = getLoginUserResponse.then().extract().path("message");
	
	        // Assert field values
	        Assert.assertEquals(code, 200, "Expected code is 200");
	        Assert.assertEquals(type, "unknown", "Expected type is 'unknown'");
	        Assert.assertTrue(message.contains("logged in"), "Message should contain 'logged in'");
	    
	        // Load JSON schema file and validate response
	        String jsonFilePath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\jsonSchemaFiles\\getUserLoginSchema.json";
	        File file = new File(jsonFilePath);
	        logger.info("Asserting that the response matches the JSON schema from {}", jsonFilePath);
	        getLoginUserResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(file));
	       
	    } 
	    catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished loginUser_shouldReturn200StatusCode *****");
	        logger.info("********************************************");
	    }
	}
			
		
	
	
//Logout user api	
	@Test(priority = 6, description = "This test verifies that the logout response returns a 200 status code")
	public void logoutUser_shouldReturn200StatusCode() 
	{
	    logger.info("********************************************");
	    logger.info("***** Starting logoutUser_shouldReturn200StatusCode *****");
	    
	    try {
	        // Log the start of the test
	        ExtentReportManager.test.info("Testing logout user for correct status code");
	        
	        // Sending the logout request
	        Response getLogoutUsertResponse = UserEndpoints.logoutUser();
	        long resTime = getLogoutUsertResponse.getTime();

	        
	        // Asserting status code
	        Assert.assertEquals(getLogoutUsertResponse.getStatusCode(), 200, "Expected status code is 200.");
	        Assert.assertTrue(resTime >= 200 && resTime <= 2000, "Expected response time between 200 and 2000ms.");
	        Assert.assertEquals(getLogoutUsertResponse.getHeader("Content-Type"), "application/json", "Expected content type is application/json.");

	        // Extract response body and assert field types
	        String jsonBody = getLogoutUsertResponse.body().asString();
	        JSONObject jsonObj = new JSONObject(jsonBody);
	        
	        Assert.assertTrue(jsonObj.get("code") instanceof Integer, "Code field should be an integer.");
	        Assert.assertTrue(jsonObj.get("type") instanceof String, "Type field should be a string.");
	        Assert.assertTrue(jsonObj.get("message") instanceof String, "Message field should be a string.");
	      
	        // Extract and assert specific field values
	        int code = getLogoutUsertResponse.then().extract().path("code");
	        String type = getLogoutUsertResponse.then().extract().path("type");

	        Assert.assertEquals(code, 200, "Expected code is 200.");
	        Assert.assertEquals(type, "unknown", "Expected type is 'unknown'.");
	    
	        // Validate JSON schema
	        String jsonFilePath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\jsonSchemaFiles\\getUserLogoutSchema.json";
	        File file = new File(jsonFilePath);
	        getLogoutUsertResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(file));
	    
	    } 
	    catch (AssertionError e) 
	    {
	        logger.error("Assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;  // Rethrow for test failure
	    } 
	    finally 
	    {
	        logger.info("***** Finished logoutUser_shouldReturn200StatusCode *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	
	
//Delete user api	
	
	@Test(priority = 7, description = "This test verifies that the DELETE request for a user returns a 200 status code")
	public void deleteUser_shouldReturn200StatusCode() {
	    // Logging and starting test
	    logger.info("********************************************");
	    logger.info("***** Starting deleteUser_shouldReturn200StatusCode *****");

	    try {
	        ExtentReportManager.test.info("Sending DELETE request for user");
	        
	        // Sending DELETE request to delete a user
	        Response deleteUserResponse = UserEndpoints.deleteUser(userpayload.getUsername());
	        
	        // Verifying status code
	        int statusCode = deleteUserResponse.getStatusCode();
	        long resTime = deleteUserResponse.getTime();

	        Assert.assertEquals(statusCode, 200, "Expected status code is 200.");
	        Assert.assertTrue(resTime >= 200 && resTime <= 2000, "Response time is outside the expected range (200-2000ms).");

	        JSONObject jsonObj = new JSONObject(deleteUserResponse.body().asString());

	        Object codeField = jsonObj.get("code");
	        Object typeField = jsonObj.get("type");
	        Object messageField = jsonObj.get("message");

	        // Asserting field types
	        Assert.assertTrue(codeField instanceof Integer, "Code field should be an integer.");
	        Assert.assertTrue(typeField instanceof String, "Type field should be a string.");
	        Assert.assertTrue(messageField instanceof String, "Message field should be a string.");
	        ExtentReportManager.test.pass("Field types are as expected");
	        
	        int code = deleteUserResponse.then().extract().path("code");
	        String type = deleteUserResponse.then().extract().path("type");

	        // Asserting field values
	        Assert.assertEquals(code, 200, "Expected code is 200.");
	        Assert.assertEquals(type, "unknown", "Expected type is 'unknown'.");
	        
	        File schemaFile = new File("F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\jsonSchemaFiles\\deleteUserSchema.json");

	        deleteUserResponse.then().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
	        
	        
	    } catch (Exception e) {
	        logger.error("Unexpected error: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } finally {
	        logger.info("***** Finished deleteUser_shouldReturn200StatusCode *****");
	        logger.info("********************************************");
	    }
	}

	
	

	
	@Test(priority = 8, description = "This test verifies that a deleted user cannot be fetched and returns a 404 error")
	public void deletedUser_shouldNotBeFetched_shouldReturn404Error() {
	    // Logging and starting test
	    logger.info("********************************************");
	    logger.info("***** Starting deletedUser_shouldNotBeFetched_shouldReturn404Error *****");

	    try {
	        ExtentReportManager.test.info("Sending GET request for deleted user");

	        Response deleteUserResponse = UserEndpoints.readUser(userpayload.getUsername());
	        deleteUserResponse.then().log().all();

	        int code = deleteUserResponse.then().extract().path("code");
	        String type = deleteUserResponse.then().extract().path("type");
	        String message = deleteUserResponse.then().extract().path("message");
	        long resTime = deleteUserResponse.getTime();

	        // Asserting response fields and status code
	        Assert.assertEquals(code, 1, "Expected code is 1.");
	        Assert.assertEquals(type, "error", "Expected type is 'error'.");
	        Assert.assertEquals(message, "User not found", "Expected message is 'User not found'.");
	        Assert.assertEquals(deleteUserResponse.getStatusCode(), 404, "Expected status code is 404.");
	        Assert.assertTrue(resTime >= 200 && resTime <= 2000, "Response time is outside the expected range (200-2000ms).");
	        ExtentReportManager.test.pass("Deleted user fetch returned 404 as expected");

	    } catch (Exception e) {
	        logger.error("Unexpected error: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } finally {
	        logger.info("***** Finished deletedUser_shouldNotBeFetched_shouldReturn404Error *****");
	        logger.info("********************************************");
	    }
	}

	@Test(priority = 38, description = "This test verifies that the bulk user creation returns a 200 status code")
	public void createUsersInBulk_shouldReturn200StatusCode() throws IOException {
	    // Logging and starting test
	    logger.info("********************************************");
	    logger.info("***** Starting createUsersInBulk_shouldReturn200StatusCode *****");

	    try {
	        ExtentReportManager.test.info("Sending POST request for bulk user creation");

	        String jsonFilePath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\jsonDataFiles\\body.json";
	        Response response = UserEndpoints.createUsers(jsonFilePath);

	        // Asserting status code
	        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200.");
	        ExtentReportManager.test.pass("Bulk user creation returned 200 as expected");

	    } catch (Exception e) {
	        logger.error("Unexpected error: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } finally {
	        logger.info("***** Finished createUsersInBulk_shouldReturn200StatusCode *****");
	        logger.info("********************************************");
	    }
	}

	
	


}



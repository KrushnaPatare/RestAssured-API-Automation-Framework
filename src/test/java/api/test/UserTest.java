package api.test;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.ExtentReportManager;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

@Listeners(api.utilities.ExtentReportManager.class)
public class UserTest {
	
	Logger logger;
	Faker faker;
	User userpayload;
	Response postUserResponse;
	Response getUserResponse;
	Response putUserResponse;
	Response deleteUserResponse;
	Response getLoginUserResponse;
	Response getLogoutUsertResponse;
	
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
	
		
	//Post user api	
		@Test(priority = 1, description = "This test verifies the Create User POST response status code")
	public void createUser_shouldReturn200StatusCode() {
	    
	    logger.info("********************************************");
	    logger.info("***** Starting createUser_shouldReturn200StatusCode *****");
	
	    try {
	        // Sending POST request to create a new user
	        logger.info("Sending POST request to create a new user");
	        postUserResponse = UserEndpoints.createUser(userpayload);
	        postUserResponse.then().log().all();
	
	        // Extracting and logging status code
	        int statusCode = postUserResponse.getStatusCode();
	        logger.info("StatusCode: {}", statusCode);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>StatusCode: " + statusCode + "</span></pre></b>");
	
	        // Assertion for status code
	        Assert.assertEquals(statusCode, 200, "Expected status code is 200.");
	        ExtentReportManager.test.pass("Status code assertion passed");
	
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
	
	@Test(priority = 2, description = "This test verifies the Create User response time")
	public void createUser_shouldRespondWithinExpectedTime() {
	
	    logger.info("********************************************");
	    logger.info("***** Starting createUser_shouldRespondWithinExpectedTime *****");
	
	    try {
	        // Logging the response details
	        this.postUserResponse.then().log().all();
	        
	        // Extracting and logging response time
	        long responseTime = this.postUserResponse.getTime();
	        logger.info("Response Time: {}", responseTime);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>Response Time: " + responseTime + "ms</span></pre></b>");
	
	        // Assertion for response time
	        Assert.assertTrue(responseTime >= 200 && responseTime <= 2000, "Response time is outside the expected range (200-2000ms).");
	        ExtentReportManager.test.pass("Response time assertion passed");
	
	    } catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } catch (Exception e) {
	        logger.error("Unexpected exception occurred: {}", e.getMessage());
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } finally {
	        logger.info("***** Finished createUser_shouldRespondWithinExpectedTime *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	@Test(priority = 3, description = "This test verifies the Content-Type header of the Create User response")
	public void createUser_shouldReturnCorrectContentTypeHeader() {
	
	    logger.info("********************************************");
	    logger.info("***** Starting createUser_shouldReturnCorrectContentTypeHeader *****");
	
	    try {
	        // Logging the response details
	    	this.postUserResponse.then().log().all();
	
	        // Extracting and logging Content-Type header
	        String contentType = this.postUserResponse.getHeader("Content-Type");
	        logger.info("Content-Type Header: {}", contentType);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>Content-Type Header: " + contentType + "</span></pre></b>");
	
	        // Assertion for Content-Type header
	        Assert.assertEquals(contentType, "application/json", "Expected content type is application/json.");
	        ExtentReportManager.test.pass("Content-Type header assertion passed");
	
	    } catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } catch (Exception e) {
	        logger.error("Unexpected exception occurred: {}", e.getMessage());
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } finally {
	        logger.info("***** Finished createUser_shouldReturnCorrectContentTypeHeader *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	@Test(priority = 4, description = "This test verifies the field types of the Create User response")
	public void createUser_shouldReturnValidResponseFieldTypes() {
	
	    logger.info("********************************************");
	    logger.info("***** Starting createUser_shouldReturnValidResponseFieldTypes *****");
	
	    try {
	        // Logging the response details
	    	this.postUserResponse.then().log().all();
	
	        // Extracting and validating field types from response
	        String jsonBody = this.postUserResponse.getBody().asString();
	        JSONObject jsonObj = new JSONObject(jsonBody);
	        Object codeField = jsonObj.get("code");
	        Object typeField = jsonObj.get("type");
	        Object messageField = jsonObj.get("message");
	
	        logger.info("Asserting response field types");
	        Assert.assertTrue(codeField instanceof Integer, "Code field should be Integer.");
	        Assert.assertTrue(typeField instanceof String, "Type field should be String.");
	        Assert.assertTrue(messageField instanceof String, "Message field should be String.");
	        ExtentReportManager.test.pass("Field type assertions passed");
	
	    } catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } catch (Exception e) {
	        logger.error("Unexpected exception occurred: {}", e.getMessage());
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } finally {
	        logger.info("***** Finished createUser_shouldReturnValidResponseFieldTypes *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	@Test(priority = 5, description = "This test verifies the field values of the Create User response")
	public void createUser_shouldReturnCorrectFieldValues() {
	
	    logger.info("********************************************");
	    logger.info("***** Starting createUser_shouldReturnCorrectFieldValues *****");
	
	    try {
	        // Logging the response details
	    	this.postUserResponse.then().log().all();
	
	        // Extracting and logging field values
	        int code = this.postUserResponse.then().extract().path("code");
	        String type = this.postUserResponse.then().extract().path("type");
	        logger.info("Code: {}, Type: {}", code, type);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>Code: " + code + ", Type: " + type + "</span></pre></b>");
	
	        // Assertion for field values
	        Assert.assertEquals(code, 200, "Expected code is 200.");
	        Assert.assertEquals(type, "unknown", "Expected type is 'unknown'.");
	        ExtentReportManager.test.pass("Field value assertions passed");
	
	    } catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } catch (Exception e) {
	        logger.error("Unexpected exception occurred: {}", e.getMessage());
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } finally {
	        logger.info("***** Finished createUser_shouldReturnCorrectFieldValues *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	@Test(priority = 6, description = "This test validates the Create User response against a JSON schema")
	public void createUser_shouldMatchJsonSchema() {
	
	    logger.info("********************************************");
	    logger.info("***** Starting createUser_shouldMatchJsonSchema *****");
	
	    try {
	        // Logging the response details
	    	this.postUserResponse.then().log().all();
	
	        // Path to JSON schema
	        String jsonFilePath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\jsonSchemaFiles\\postUserSchema.json";
	        File schemaFile = new File(jsonFilePath);
	
	        // Schema validation
	        this.postUserResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
	        logger.info("JSON schema validation passed");
	        ExtentReportManager.test.pass("JSON schema validation passed");
	
	    } catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } catch (Exception e) {
	        logger.error("Unexpected exception occurred: {}", e.getMessage());
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } finally {
	        logger.info("***** Finished createUser_shouldMatchJsonSchema *****");
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
		@Test(priority = 7, description = "This test verifies that GET user returns 200 status code")
	public void getUser_shouldReturn200StatusCode() 
	{
	    logger.info("***** Starting getUser_shouldReturn200StatusCode *****");
	    ExtentReportManager.test.info("Test for GET user 200 status code started");
	
	    try {
	        getUserResponse = UserEndpoints.readUser(userpayload.getUsername());
	        logger.info("GET response logged");
	        ExtentReportManager.test.info("GET response logged");
	
	        // Logging status code
	        int statusCode = this.getUserResponse.getStatusCode();
	        logger.info("StatusCode: {}", statusCode);
	        ExtentReportManager.test.info("<b>StatusCode: " + statusCode + "</b>");
	
	        // Assertion
	        Assert.assertEquals(statusCode, 200, "Expected status code is 200.");
	        ExtentReportManager.test.pass("Status code assertion passed");
	    } 
	    catch (Exception e) {
	        logger.error("Unexpected exception occurred: {}", e.getMessage());
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished getUser_shouldReturn200StatusCode *****");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	@Test(priority = 8, description = "This test verifies that GET user response time is within the expected range")
	public void getUser_shouldRespondWithinExpectedTime() 
	{
	    logger.info("***** Starting getUser_shouldRespondWithinExpectedTime *****");
	    ExtentReportManager.test.info("Test for GET user response time started");
	
	    try {
	        // Log and extract response time
	    	this.getUserResponse.then().log().all();
	        long responseTime = this.getUserResponse.getTime();
	        logger.info("Response time: {} ms", responseTime);
	        ExtentReportManager.test.info("<b>Response time: " + responseTime + " ms</b>");
	
	        // Assertion for response time
	        Assert.assertTrue(responseTime >= 200 && responseTime <= 1500, "Response time is outside the expected range.");
	        ExtentReportManager.test.pass("Response time assertion passed");
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
	
	@Test(priority = 9, description = "This test verifies that the GET user response contains the correct Content-Type header")
	public void getUser_shouldReturnCorrectContentTypeHeader() 
	{
	    logger.info("***** Starting getUser_shouldReturnCorrectContentTypeHeader *****");
	    ExtentReportManager.test.info("Test for GET user Content-Type started");
	
	    try {
	        // Log and extract Content-Type header
	    	this.getUserResponse.then().log().all();
	        String contentType = this.getUserResponse.getHeader("Content-Type");
	        logger.info("Content-Type: {}", contentType);
	        ExtentReportManager.test.info("<b>Content-Type: " + contentType + "</b>");
	
	        // Assertion for Content-Type
	        Assert.assertEquals(contentType, "application/json", "Expected Content-Type is application/json.");
	        ExtentReportManager.test.pass("Content-Type header assertion passed");
	    } 
	    catch (Exception e) {
	        logger.error("Unexpected exception occurred: {}", e.getMessage());
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished getUser_shouldReturnCorrectContentTypeHeader *****");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	@Test(priority = 10, description = "This test verifies that the GET user response has valid field types")
	public void getUser_shouldReturnValidResponseFieldTypes() 
	{
	    logger.info("***** Starting getUser_shouldReturnValidResponseFieldTypes *****");
	    ExtentReportManager.test.info("Test for GET user valid field types started");
	
	    try {
	        // Log and extract response body
	    	this.getUserResponse.then().log().all();
	        String jsonBody = this.getUserResponse.body().asString();
	        JSONObject jsonObj = new JSONObject(jsonBody);
	
	        // Extract fields and validate types
	        logger.info("Asserting field types in the response body");
	        Assert.assertTrue(jsonObj.get("id") instanceof Integer, "Id field should be integer.");
	        Assert.assertTrue(jsonObj.get("username") instanceof String, "Username field should be String.");
	        Assert.assertTrue(jsonObj.get("firstName") instanceof String, "FirstName field should be String.");
	        Assert.assertTrue(jsonObj.get("lastName") instanceof String, "LastName field should be String.");
	        Assert.assertTrue(jsonObj.get("email") instanceof String, "Email field should be String.");
	        Assert.assertTrue(jsonObj.get("password") instanceof String, "Password field should be String.");
	        Assert.assertTrue(jsonObj.get("phone") instanceof String, "Phone field should be String.");
	        Assert.assertTrue(jsonObj.get("userStatus") instanceof Integer, "UserStatus field should be Integer.");
	        ExtentReportManager.test.pass("Field type assertions passed");
	    } 
	    catch (Exception e) {
	        logger.error("Unexpected exception occurred: {}", e.getMessage());
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished getUser_shouldReturnValidResponseFieldTypes *****");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	@Test(priority = 11, description = "This test verifies that the GET user response contains correct field values")
	public void getUser_shouldReturnCorrectFieldValues() 
	{
	    logger.info("***** Starting getUser_shouldReturnCorrectFieldValues *****");
	    ExtentReportManager.test.info("Test for GET user correct field values started");
	
	    try {
	        // Log and extract response fields
	    	this.getUserResponse.then().log().all();
	        String username = this.getUserResponse.then().extract().path("username");
	        String firstName = this.getUserResponse.then().extract().path("firstName");
	        String lastName = this.getUserResponse.then().extract().path("lastName");
	        String email = this.getUserResponse.then().extract().path("email");
	        String password = this.getUserResponse.then().extract().path("password");
	        String phone = this.getUserResponse.then().extract().path("phone");
	        int userStatus = this.getUserResponse.then().extract().path("userStatus");
	
	        // Assertions for field values
	        Assert.assertEquals(username, userpayload.getUsername(), "Username mismatch.");
	        Assert.assertEquals(firstName, userpayload.getFirstName(), "FirstName mismatch.");
	        Assert.assertEquals(lastName, userpayload.getLastName(), "LastName mismatch.");
	        Assert.assertEquals(email, userpayload.getEmail(), "Email mismatch.");
	        Assert.assertEquals(password, userpayload.getPassword(), "Password mismatch.");
	        Assert.assertEquals(phone, userpayload.getPhone(), "Phone mismatch.");
	        Assert.assertEquals(userStatus, userpayload.getUserStatus(), "UserStatus mismatch.");
	        ExtentReportManager.test.pass("Field value assertions passed");
	    } 
	    catch (Exception e) {
	        logger.error("Unexpected exception occurred: {}", e.getMessage());
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished getUser_shouldReturnCorrectFieldValues *****");
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
		 		
        		
	@Test(priority = 13, description = "This test verifies the successful update of a user and returns 200 status code")
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
	        logger.info("Sending PUT request to update user.");
	        ExtentReportManager.test.info("Sending PUT request");
	        putUserResponse = UserEndpoints.updateUser(userpayload, userpayload.getUsername());
	
	        // Log and verify the response
	        this.putUserResponse.then().log().all();
	        int statusCode = this.putUserResponse.getStatusCode();
	        logger.info("StatusCode: {}", statusCode);
	        ExtentReportManager.test.info("StatusCode: " + statusCode);
	
	        // Assert status code
	        Assert.assertEquals(statusCode, 200, "Expected status code is 200.");
	        ExtentReportManager.test.pass("Status code assertion passed");
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
	
	@Test(priority = 14, description = "This test verifies that the update user request responds within the expected time frame")
	public void updateUser_shouldRespondWithinExpectedTime() 
	{
	    // Log and start the test
	    ExtentReportManager.test.info("Test for verifying response time started");
	    logger.info("********************************************");
	    logger.info("***** Starting updateUser_shouldRespondWithinExpectedTime *****");
	
	    try {
	        // Log response
	    	this.putUserResponse.then().log().all();
	
	        // Assert response time
	        long resTime = this.putUserResponse.getTime();
	        logger.info("Response Time: {}", resTime);
	        ExtentReportManager.test.info("Response Time: " + resTime);
	        Assert.assertTrue(resTime >= 200 && resTime <= 2000, "Response time is outside the expected range (200-2000ms).");
	
	        ExtentReportManager.test.pass("Response time assertion passed");
	    } 
	    catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished updateUser_shouldRespondWithinExpectedTime *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	@Test(priority = 15, description = "This test verifies the Content-Type header in the update user response")
	public void updateUser_shouldReturnCorrectContentTypeHeader() 
	{
	    // Log and start the test
	    ExtentReportManager.test.info("Test for verifying Content-Type header started");
	    logger.info("********************************************");
	    logger.info("***** Starting updateUser_shouldReturnCorrectContentTypeHeader *****");
	
	    try {
	        // Log response
	    	this.putUserResponse.then().log().all();
	
	        // Assert Content-Type header
	        String contentType = this.putUserResponse.getHeader("Content-Type");
	        logger.info("Content-Type: {}", contentType);
	        ExtentReportManager.test.info("Content-Type: " + contentType);
	        Assert.assertEquals(contentType, "application/json", "Expected Content-Type is application/json.");
	        
	        ExtentReportManager.test.pass("Content-Type header assertion passed");
	    } 
	    catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished updateUser_shouldReturnCorrectContentTypeHeader *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	@Test(priority = 16, description = "This test verifies the types of fields in the update user response")
	public void updateUser_shouldReturnValidResponseFieldTypes() 
	{
	    // Log and start the test
	    ExtentReportManager.test.info("Test for verifying field types started");
	    logger.info("********************************************");
	    logger.info("***** Starting updateUser_shouldReturnValidResponseFieldTypes *****");
	
	    try {
	        // Extract JSON response and fields
	        ResponseBody resBody = this.putUserResponse.body();
	        String jsonBody = resBody.asString();
	        JSONObject jsonObj = new JSONObject(jsonBody);
	
	        // Extract fields and assert their types
	        Assert.assertTrue(jsonObj.get("id") instanceof Integer, "Id field should be an integer.");
	        Assert.assertTrue(jsonObj.get("username") instanceof String, "Username field should be a string.");
	        Assert.assertTrue(jsonObj.get("firstName") instanceof String, "FirstName field should be a string.");
	        Assert.assertTrue(jsonObj.get("lastName") instanceof String, "LastName field should be a string.");
	        Assert.assertTrue(jsonObj.get("email") instanceof String, "Email field should be a string.");
	        Assert.assertTrue(jsonObj.get("password") instanceof String, "Password field should be a string.");
	        Assert.assertTrue(jsonObj.get("phone") instanceof String, "Phone field should be a string.");
	        Assert.assertTrue(jsonObj.get("userStatus") instanceof Integer, "UserStatus field should be an integer.");
	        
	        ExtentReportManager.test.pass("Field type assertions passed");
	    } 
	    catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished updateUser_shouldReturnValidResponseFieldTypes *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	@Test(priority = 17, description = "This test verifies that the field values returned in the update user response are correct")
	public void updateUser_shouldReturnCorrectFieldValues() 
	{
	    // Log and start the test
	    ExtentReportManager.test.info("Test for verifying field values started");
	    logger.info("********************************************");
	    logger.info("***** Starting updateUser_shouldReturnCorrectFieldValues *****");
	
	    try {
	        // Extract and assert field values
	        String username = this.putUserResponse.then().extract().path("username");
	        String firstName = this.putUserResponse.then().extract().path("firstName");
	        String lastName = this.putUserResponse.then().extract().path("lastName");
	        String email = this.putUserResponse.then().extract().path("email");
	        String password = this.putUserResponse.then().extract().path("password");
	        String phone = this.putUserResponse.then().extract().path("phone");
	        int userStatus = this.putUserResponse.then().extract().path("userStatus");
	
	        Assert.assertEquals(username, userpayload.getUsername());
	        Assert.assertEquals(firstName, userpayload.getFirstName());
	        Assert.assertEquals(lastName, userpayload.getLastName());
	        Assert.assertEquals(email, userpayload.getEmail());
	        Assert.assertEquals(password, userpayload.getPassword());
	        Assert.assertEquals(phone, userpayload.getPhone());
	        Assert.assertEquals(userStatus, userpayload.getUserStatus());
	
	        ExtentReportManager.test.pass("Field value assertions passed");
	    } 
	    catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished updateUser_shouldReturnCorrectFieldValues *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	@Test(priority = 18, description = "This test verifies that the update user response matches the expected JSON schema")
	public void updateUser_shouldMatchJsonSchema() 
	{
	    // Log and start the test
	    ExtentReportManager.test.info("Test for verifying JSON schema started");
	    logger.info("********************************************");
	    logger.info("***** Starting updateUser_shouldMatchJsonSchema *****");
	
	    try {
	        // Path to the JSON schema file
	        String jsonFilePath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\jsonSchemaFiles\\putUserSchema.json";
	        File file = new File(jsonFilePath);
	
	        // Assert that the response matches the schema
	        this.putUserResponse.then().log().all();
	        this.putUserResponse.then().body(JsonSchemaValidator.matchesJsonSchema(file));
	
	        ExtentReportManager.test.pass("JSON schema assertion passed");
	    } 
	    catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished updateUser_shouldMatchJsonSchema *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	@Test(priority = 19, description = "This test verifies how the system handles an update request with an invalid username")
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
	        this.putUserResponse = UserEndpoints.updateUser(userpayload, wrongUsername);
	        this.putUserResponse.then().log().all();
	
	        // Extract fields from response
	        int code = this.putUserResponse.then().extract().path("code");
	        String type = this.putUserResponse.then().extract().path("type");
	        long resTime = this.putUserResponse.getTime();
	        JSONObject jsonObj = new JSONObject(this.putUserResponse.body().asString());
	
	        // Assert field types and values
	        Assert.assertTrue(jsonObj.get("code") instanceof Integer, "Code field should be an integer.");
	        Assert.assertTrue(jsonObj.get("type") instanceof String, "Type field should be a string.");
	        Assert.assertTrue(jsonObj.get("message") instanceof String, "Message field should be a string.");
	        Assert.assertEquals(code, 200);
	        Assert.assertEquals(type, "unknown");
	        Assert.assertEquals(this.putUserResponse.getStatusCode(), 200);
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
	
	@Test(priority = 20, description = "This test verifies that logging in a user returns a 200 status code")
	public void loginUser_shouldReturn200StatusCode() {
	    logger.info("********************************************");
	    logger.info("***** Starting loginUser_shouldReturn200StatusCode *****");
	
	    try {
	        // Send login request
	        logger.info("Sending login request for user: {}", userpayload.getUsername());
	        getLoginUserResponse = UserEndpoints.loginUser(userpayload.getUsername(), userpayload.getPassword());
	        
	        // Log response status and body
	        this.getLoginUserResponse.then().log().status().and().log().body();
	        ExtentReportManager.test.info("Response: " + this.getLoginUserResponse.asPrettyString());
	
	        // Assertion for status code
	        logger.info("Asserting that the status code is 200");
	        Assert.assertEquals(this.getLoginUserResponse.getStatusCode(), 200);
	        ExtentReportManager.test.pass("Status code assertion passed: 200");
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
	
		
		
	@Test(priority = 21, description = "This test verifies that login user response is within the expected time range")
	public void loginUser_shouldRespondWithinExpectedTime() {
	    logger.info("********************************************");
	    logger.info("***** Starting loginUser_shouldRespondWithinExpectedTime *****");
	
	    try {
	        // Log complete response
	    	this.getLoginUserResponse.then().log().all();
	        
	        // Capture response time
	        long resTime = this.getLoginUserResponse.getTime();
	        logger.info("Response time: {} ms", resTime);
	        ExtentReportManager.test.info("Response time: " + resTime + " ms");
	
	        // Assert response time is within the range
	        logger.info("Asserting that the response time is between 200ms and 1500ms");
	        Assert.assertTrue(resTime >= 200 && resTime <= 1500, "Response time is outside the expected range");
	        ExtentReportManager.test.pass("Response time assertion passed: " + resTime + " ms");
	    } 
	    catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished loginUser_shouldRespondWithinExpectedTime *****");
	        logger.info("********************************************");
	    }
	}
		
	
	@Test(priority = 22, description = "This test verifies that the login response has the correct Content-Type header")
	public void loginUser_shouldReturnCorrectContentTypeHeader() {
	    logger.info("********************************************");
	    logger.info("***** Starting loginUser_shouldReturnCorrectContentTypeHeader *****");

	    try {
	        // Log the complete response
	    	this.getLoginUserResponse.then().log().all();

	        // Assert Content-Type header
	        logger.info("Asserting that the Content-Type is application/json");
	        Assert.assertEquals(this.getLoginUserResponse.getHeader("Content-Type"), "application/json", "Expected Content-Type is application/json");
	        ExtentReportManager.test.pass("Content-Type assertion passed: application/json");
	    } 
	    catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished loginUser_shouldReturnCorrectContentTypeHeader *****");
	        logger.info("********************************************");
	    }
	}


	@Test(priority = 23, description = "This test verifies that the login response fields have the correct types")
	public void loginUser_shouldReturnValidResponseFieldTypes() {
	    logger.info("********************************************");
	    logger.info("***** Starting loginUser_shouldReturnValidResponseFieldTypes *****");

	    try {
	        // Log complete response
	    	this.getLoginUserResponse.then().log().all();
	        String jsonBody = this.getLoginUserResponse.body().asString();

	        // Parse JSON and check field types
	        JSONObject jsonObj = new JSONObject(jsonBody);
	        Object codeField = jsonObj.get("code");
	        Object typeField = jsonObj.get("type");
	        Object messageField = jsonObj.get("message");

	        logger.info("Asserting that 'code' is an Integer, 'type' is a String, and 'message' is a String");
	        Assert.assertTrue(codeField instanceof Integer, "Code field should be an Integer.");
	        Assert.assertTrue(typeField instanceof String, "Type field should be a String.");
	        Assert.assertTrue(messageField instanceof String, "Message field should be a String.");
	        ExtentReportManager.test.pass("Field type assertions passed");
	    } 
	    catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished loginUser_shouldReturnValidResponseFieldTypes *****");
	        logger.info("********************************************");
	    }
	}

		
	@Test(priority = 24, description = "This test verifies that the login response fields contain the correct values")
	public void loginUser_shouldReturnCorrectFieldValues() {
	    logger.info("********************************************");
	    logger.info("***** Starting loginUser_shouldReturnCorrectFieldValues *****");
	
	    try {
	        // Log complete response
	    	this.getLoginUserResponse.then().log().all();
	
	        // Extract field values from the response
	        int code = this.getLoginUserResponse.then().extract().path("code");
	        String type = this.getLoginUserResponse.then().extract().path("type");
	        String message = this.getLoginUserResponse.then().extract().path("message");
	
	        // Assert field values
	        logger.info("Asserting that 'code' is 200, 'type' is 'unknown', and 'message' contains 'logged in'");
	        Assert.assertEquals(code, 200, "Expected code is 200");
	        Assert.assertEquals(type, "unknown", "Expected type is 'unknown'");
	        Assert.assertTrue(message.contains("logged in"), "Message should contain 'logged in'");
	        ExtentReportManager.test.pass("Field value assertions passed");
	    } 
	    catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished loginUser_shouldReturnCorrectFieldValues *****");
	        logger.info("********************************************");
	    }
	}
	
		
	@Test(priority = 25, description = "This test validates the login response against a predefined JSON schema")
	public void loginUser_shouldMatchJsonSchema() {
	    logger.info("********************************************");
	    logger.info("***** Starting loginUser_shouldMatchJsonSchema *****");
	
	    try {
	        // Log complete response
	    	this.getLoginUserResponse.then().log().all();
	
	        // Load JSON schema file and validate response
	        String jsonFilePath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\jsonSchemaFiles\\getUserLoginSchema.json";
	        File file = new File(jsonFilePath);
	        logger.info("Asserting that the response matches the JSON schema from {}", jsonFilePath);
	        this.getLoginUserResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(file));
	        ExtentReportManager.test.pass("JSON schema validation passed");
	    } 
	    catch (AssertionError e) {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally {
	        logger.info("***** Finished loginUser_shouldMatchJsonSchema *****");
	        logger.info("********************************************");
	    }
	}
		
		
	
	
//Logout user api	
	@Test(priority = 26, description = "This test verifies that the logout response returns a 200 status code")
	public void logoutUser_shouldReturn200StatusCode() 
	{
	    logger.info("********************************************");
	    logger.info("***** Starting logoutUser_shouldReturn200StatusCode *****");
	    
	    try {
	        // Log the start of the test
	        ExtentReportManager.test.info("Testing logout user for correct status code");
	        
	        // Sending the logout request
	        this.getLogoutUsertResponse = UserEndpoints.logoutUser();
	        logger.info("Sending logout request");
	        ExtentReportManager.test.info("Sending logout request");

	        // Extract and log response details
	        logger.info("Logging response data");
	        this.getLogoutUsertResponse.then().log().all();
	        ExtentReportManager.test.info("Response: " + this.getLogoutUsertResponse.asPrettyString());

	        // Asserting status code
	        Assert.assertEquals(this.getLogoutUsertResponse.getStatusCode(), 200, "Expected status code is 200.");
	        ExtentReportManager.test.pass("Status code is 200 as expected");
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

	@Test(priority = 27, description = "This test verifies the response time for user logout")
	public void logoutUser_shouldRespondWithinExpectedTime() 
	{
	    logger.info("********************************************");
	    logger.info("***** Starting logoutUser_shouldRespondWithinExpectedTime *****");

	    try {
	        // Log the start of the test
	        ExtentReportManager.test.info("Testing logout user for response time");

	        // Log the response data
	        this.getLogoutUsertResponse.then().log().all();
	        ExtentReportManager.test.info("Response: " + this.getLogoutUsertResponse.asPrettyString());

	        // Extract response time and assert
	        long resTime = this.getLogoutUsertResponse.getTime();
	        logger.info("Response time: {}", resTime);
	        ExtentReportManager.test.info("Response time: " + resTime + "ms");
	        Assert.assertTrue(resTime >= 200 && resTime <= 2000, "Expected response time between 200 and 2000ms.");
	        ExtentReportManager.test.pass("Response time within expected range");
	    } 
	    catch (AssertionError e) 
	    {
	        logger.error("Assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally 
	    {
	        logger.info("***** Finished logoutUser_shouldRespondWithinExpectedTime *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}

	@Test(priority = 28, description = "This test verifies the content type of the logout response")
	public void logoutUser_shouldReturnCorrectContentTypeHeader() 
	{
	    logger.info("********************************************");
	    logger.info("***** Starting logoutUser_shouldReturnCorrectContentTypeHeader *****");

	    try {
	        // Log the start of the test
	        ExtentReportManager.test.info("Testing logout user for correct content type header");

	        // Log the response data
	        this.getLogoutUsertResponse.then().log().all();
	        ExtentReportManager.test.info("Response: " + this.getLogoutUsertResponse.asPrettyString());

	        // Assert content type header
	        Assert.assertEquals(this.getLogoutUsertResponse.getHeader("Content-Type"), "application/json", "Expected content type is application/json.");
	        ExtentReportManager.test.pass("Content type is application/json as expected");
	    } 
	    catch (AssertionError e) 
	    {
	        logger.error("Assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally 
	    {
	        logger.info("***** Finished logoutUser_shouldReturnCorrectContentTypeHeader *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}

	@Test(priority = 29, description = "This test verifies the field types in the logout response")
	public void logoutUser_shouldReturnValidResponseFieldTypes() 
	{
	    logger.info("********************************************");
	    logger.info("***** Starting logoutUser_shouldReturnValidResponseFieldTypes *****");

	    try {
	        // Log the start of the test
	        ExtentReportManager.test.info("Testing logout user for correct response field types");

	        // Log the response data
	        this.getLogoutUsertResponse.then().log().all();
	        ExtentReportManager.test.info("Response: " + this.getLogoutUsertResponse.asPrettyString());

	        // Extract response body and assert field types
	        ResponseBody resBody = this.getLogoutUsertResponse.body();
	        String jsonBody = resBody.asString();
	        JSONObject jsonObj = new JSONObject(jsonBody);
	        
	        Assert.assertTrue(jsonObj.get("code") instanceof Integer, "Code field should be an integer.");
	        Assert.assertTrue(jsonObj.get("type") instanceof String, "Type field should be a string.");
	        Assert.assertTrue(jsonObj.get("message") instanceof String, "Message field should be a string.");
	        ExtentReportManager.test.pass("Response fields are of correct types");
	    } 
	    catch (AssertionError e) 
	    {
	        logger.error("Assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally 
	    {
	        logger.info("***** Finished logoutUser_shouldReturnValidResponseFieldTypes *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}

	@Test(priority = 30, description = "This test verifies the field values in the logout response")
	public void logoutUser_shouldReturnCorrectFieldValues() 
	{
	    logger.info("********************************************");
	    logger.info("***** Starting logoutUser_shouldReturnCorrectFieldValues *****");

	    try {
	        // Log the start of the test
	        ExtentReportManager.test.info("Testing logout user for correct response field values");

	        // Log the response data
	        this.getLogoutUsertResponse.then().log().all();
	        ExtentReportManager.test.info("Response: " + this.getLogoutUsertResponse.asPrettyString());

	        // Extract and assert specific field values
	        int code = this.getLogoutUsertResponse.then().extract().path("code");
	        String type = this.getLogoutUsertResponse.then().extract().path("type");

	        Assert.assertEquals(code, 200, "Expected code is 200.");
	        Assert.assertEquals(type, "unknown", "Expected type is 'unknown'.");
	        ExtentReportManager.test.pass("Field values are correct");
	    } 
	    catch (AssertionError e) 
	    {
	        logger.error("Assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally 
	    {
	        logger.info("***** Finished logoutUser_shouldReturnCorrectFieldValues *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}

	@Test(priority = 31, description = "This test verifies the logout response against a JSON schema")
	public void logoutUser_shouldMatchJsonSchema() 
	{
	    logger.info("********************************************");
	    logger.info("***** Starting logoutUser_shouldMatchJsonSchema *****");

	    try {
	        // Log the start of the test
	        ExtentReportManager.test.info("Testing logout user for JSON schema match");

	        // Log the response data
	        this.getLogoutUsertResponse.then().log().all();
	        ExtentReportManager.test.info("Response: " + this.getLogoutUsertResponse.asPrettyString());

	        // Validate JSON schema
	        String jsonFilePath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\jsonSchemaFiles\\getUserLogoutSchema.json";
	        File file = new File(jsonFilePath);
	        this.getLogoutUsertResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(file));
	        ExtentReportManager.test.pass("Response matches the expected JSON schema");
	    } 
	    catch (AssertionError e) 
	    {
	        logger.error("Assertion failed: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } 
	    finally 
	    {
	        logger.info("***** Finished logoutUser_shouldMatchJsonSchema *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}

	
	

	
//Delete user api	
	
	@Test(priority = 32, description = "This test verifies that the DELETE request for a user returns a 200 status code")
	public void deleteUser_shouldReturn200StatusCode() {
	    // Logging and starting test
	    logger.info("********************************************");
	    logger.info("***** Starting deleteUser_shouldReturn200StatusCode *****");

	    try {
	        ExtentReportManager.test.info("Sending DELETE request for user");
	        
	        // Sending DELETE request to delete a user
	        deleteUserResponse = UserEndpoints.deleteUser(userpayload.getUsername());
	        this.deleteUserResponse.then().log().all();
	        
	        // Verifying status code
	        int statusCode = this.deleteUserResponse.getStatusCode();
	        logger.info("Status Code: {}", statusCode);
	        Assert.assertEquals(statusCode, 200, "Expected status code is 200.");
	        ExtentReportManager.test.pass("Status code is 200 as expected");
	        
	    } catch (Exception e) {
	        logger.error("Unexpected error: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } finally {
	        logger.info("***** Finished deleteUser_shouldReturn200StatusCode *****");
	        logger.info("********************************************");
	    }
	}

	
	@Test(priority = 33, description = "This test verifies that the DELETE request response is within the expected time")
	public void deleteUser_shouldRespondWithinExpectedTime() {
	    // Logging and starting test
	    logger.info("********************************************");
	    logger.info("***** Starting deleteUser_shouldRespondWithinExpectedTime *****");

	    try {
	        ExtentReportManager.test.info("Checking response time for DELETE request");

	        this.deleteUserResponse.then().log().all();
	        long resTime = this.deleteUserResponse.getTime();
	        logger.info("Response Time: {}", resTime);
	        Assert.assertTrue(resTime >= 200 && resTime <= 2000, "Response time is outside the expected range (200-2000ms).");
	        ExtentReportManager.test.pass("Response time is within the expected range");

	    } catch (Exception e) {
	        logger.error("Unexpected error: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } finally {
	        logger.info("***** Finished deleteUser_shouldRespondWithinExpectedTime *****");
	        logger.info("********************************************");
	    }
	}

	
	@Test(priority = 34, description = "This test verifies the field types in the DELETE request response")
	public void deleteUser_shouldReturnValidResponseFieldTypes() {
	    // Logging and starting test
	    logger.info("********************************************");
	    logger.info("***** Starting deleteUser_shouldReturnValidResponseFieldTypes *****");

	    try {
	        ExtentReportManager.test.info("Checking field types in the DELETE response");

	        this.postUserResponse.then().log().all();
	        JSONObject jsonObj = new JSONObject(this.postUserResponse.body().asString());

	        Object codeField = jsonObj.get("code");
	        Object typeField = jsonObj.get("type");
	        Object messageField = jsonObj.get("message");

	        // Asserting field types
	        Assert.assertTrue(codeField instanceof Integer, "Code field should be an integer.");
	        Assert.assertTrue(typeField instanceof String, "Type field should be a string.");
	        Assert.assertTrue(messageField instanceof String, "Message field should be a string.");
	        ExtentReportManager.test.pass("Field types are as expected");

	    } catch (Exception e) {
	        logger.error("Unexpected error: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } finally {
	        logger.info("***** Finished deleteUser_shouldReturnValidResponseFieldTypes *****");
	        logger.info("********************************************");
	    }
	}

	
	@Test(priority = 35, description = "This test verifies the field values in the DELETE request response")
	public void deleteUser_shouldReturnCorrectFieldValues() {
	    // Logging and starting test
	    logger.info("********************************************");
	    logger.info("***** Starting deleteUser_shouldReturnCorrectFieldValues *****");

	    try {
	        ExtentReportManager.test.info("Checking field values in the DELETE response");

	        this.postUserResponse.then().log().all();
	        int code = this.postUserResponse.then().extract().path("code");
	        String type = this.postUserResponse.then().extract().path("type");

	        // Asserting field values
	        Assert.assertEquals(code, 200, "Expected code is 200.");
	        Assert.assertEquals(type, "unknown", "Expected type is 'unknown'.");
	        ExtentReportManager.test.pass("Field values are correct");

	    } catch (Exception e) {
	        logger.error("Unexpected error: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } finally {
	        logger.info("***** Finished deleteUser_shouldReturnCorrectFieldValues *****");
	        logger.info("********************************************");
	    }
	}

	
	
	@Test(priority = 36, description = "This test verifies the DELETE request response matches the expected JSON schema")
	public void deleteUser_shouldMatchJsonSchema() {
	    // Logging and starting test
	    logger.info("********************************************");
	    logger.info("***** Starting deleteUser_shouldMatchJsonSchema *****");

	    try {
	        ExtentReportManager.test.info("Validating DELETE response against JSON schema");

	        this.postUserResponse.then().log().all();
	        File schemaFile = new File("F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\jsonSchemaFiles\\deleteUserSchema.json");

	        this.postUserResponse.then().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
	        ExtentReportManager.test.pass("Response matches the expected JSON schema");

	    } catch (Exception e) {
	        logger.error("Unexpected error: {}", e.getMessage());
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;
	    } finally {
	        logger.info("***** Finished deleteUser_shouldMatchJsonSchema *****");
	        logger.info("********************************************");
	    }
	}

	
	@Test(priority = 37, description = "This test verifies that a deleted user cannot be fetched and returns a 404 error")
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
	        response.then().log().all();

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



package api.test;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import com.github.javafaker.Faker;

import api.endpoints.StoreEndpoints;
import api.path.Routes;
import api.payload.Store;
import api.utilities.ExtentReportManager;
import api.utilities.RequestResponseFilter;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;



@Listeners(api.utilities.ExtentReportManager.class)
public class StoreTest {
	
	Logger logger;
	Faker faker;
	Store storePayload;
	
	
	@BeforeClass
	public void setUpData()
	{
		logger = LogManager.getLogger(this.getClass());
		faker = new Faker();
		storePayload = new Store();
		
		storePayload.setId(faker.number().numberBetween(1, 10));
		storePayload.setPetId(faker.number().numberBetween(2000, 2010));
		storePayload.setQuantity(faker.number().numberBetween(20, 50));
		storePayload.setShipDate("2023-12-12T16:35:59.906+0000");
		storePayload.setStatus("placed");
		storePayload.setComplete(true);
	}
	
	
	
	@BeforeMethod()
	public void globalSetUP() 
	{
		RestAssured.baseURI=Routes.base_url;
//		RestAssured.basePath = "/v2";
		RestAssured.requestSpecification = new RequestSpecBuilder()
				.setConfig(RestAssured.config()
			    .httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", 5000)
                .setParam("http.socket.timeout", 5000)))
			    .setContentType(ContentType.JSON)
			    .setAccept(ContentType.JSON)
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

	
	@Test(priority = 1, description = "This test verifies the Post Store response for placing order of pet and validates the data")
	public void testPostStoreResponse() 
	{
    
	    // Start the test and log the initial info
	    ExtentReportManager.test.info("Test for placing an order in the store started");
	    
	    logger.info("********************************************");
	    logger.info("***** Starting testPostStoreResponse *****");
	
	    try {
	        // Send POST request to place order for a pet
	        logger.info("Sending POST request to place an order for the pet.");
	        ExtentReportManager.test.info("Sending POST request");
	
	        Response response = StoreEndpoints.placeOrderForPet(storePayload);
	
	        // Assertions for response status code, headers, and response time
	        logger.info("Asserting response status, headers, and time");
	        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200.");
	        Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Expected content type is application/json.");
	        Assert.assertTrue(response.getTime() >= 200 && response.getTime() <= 12000, "Response time is outside the expected range (200-2000ms).");
	        ExtentReportManager.test.pass("Assertions for status, headers, and time passed");
	
	        // Extracting and asserting values from the response
	        logger.info("Asserting store data between request and response");
	        int responseId = response.then().extract().path("id");
	        int responsePetId = response.then().extract().path("petId");
	        int responseQuantity = response.then().extract().path("quantity");
	        String responseShipDate = response.then().extract().path("shipDate");
	        String responseStatus = response.then().extract().path("status");
	        boolean responseComplete = response.then().extract().path("complete");
	
	        Assert.assertEquals(responseId, storePayload.getId(), "Store ID mismatch.");
	        Assert.assertEquals(responsePetId, storePayload.getPetId(), "Pet ID mismatch.");
	        Assert.assertEquals(responseQuantity, storePayload.getQuantity(), "Quantity mismatch.");
	        Assert.assertEquals(responseShipDate, storePayload.getShipDate(), "Ship Date mismatch.");
	        Assert.assertEquals(responseStatus, storePayload.getStatus(), "Status mismatch.");
	        Assert.assertEquals(responseComplete, storePayload.isComplete(), "Complete status mismatch.");
	        ExtentReportManager.test.pass("Assertions for store data passed");
	
	        // Schema validation
	        String jsonFilePath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\jsonSchemaFiles\\postStoreSchema.json";
	        File schemaFile = new File(jsonFilePath);
	        response.then().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
	        ExtentReportManager.test.pass("Schema validation passed");
	    }
	    catch (AssertionError e) 
	    {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        logger.debug("Assertion failure details", e);
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;  // Rethrow to ensure test fails properly
	    } 
	    catch (Exception e) 
	    {
	        logger.error("Unexpected exception occurred: {}", e.getMessage());
	        logger.debug("Exception details", e);
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } 
	    finally 
	    {
	        logger.info("***** Finished testPostStoreResponse *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }	
	}
	
	
	@Test(priority = 2, description = "This test verifies the Get Store Order response for finding purchanse order by id and validates the data")
	public void testGetStoreOrderResponse() {
	    // Start the test and log the initial info
	    ExtentReportManager.test.info("Test for retrieving a store order started");

	    logger.info("********************************************");
	    logger.info("***** Starting testGetStoreOrderResponse *****");

	    try {
	        // Log the input store order ID
	        logger.info("Retrieving store order with ID: {}", storePayload.getId());
	        ExtentReportManager.test.info("Store Order ID: " + storePayload.getId());

	        // Send the GET request to retrieve the store order
	        logger.info("Sending GET request to retrieve the store order.");
	        ExtentReportManager.test.info("Sending GET request");
	        Response response = StoreEndpoints.findPurchaseOrder(storePayload.getId());

	        // Assertions for the response status code, content type, and response time
	        logger.info("Asserting response status, headers, and time");
	        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200.");
	        Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Expected content type is application/json.");
	        Assert.assertTrue(response.getTime() >= 200 && response.getTime() <= 2000, "Response time is outside the expected range (200-2000ms).");
	        ExtentReportManager.test.pass("Assertions for status, headers, and time passed");

	        // Extract and log response data
	        int id = response.then().extract().path("id");
	        int petId = response.then().extract().path("petId");
	        int quantity = response.then().extract().path("quantity");
	        String shipDate = response.then().extract().path("shipDate");
	        String status = response.then().extract().path("status");
	        boolean complete = response.then().extract().path("complete");

	        // Assertions to verify that the response data matches the request payload
	        logger.info("Asserting order data between request and response");
	        Assert.assertEquals(id, storePayload.getId(), "Order ID mismatch.");
	        Assert.assertEquals(petId, storePayload.getPetId(), "Pet ID mismatch.");
	        Assert.assertEquals(quantity, storePayload.getQuantity(), "Quantity mismatch.");
	        Assert.assertEquals(shipDate, storePayload.getShipDate(), "Ship Date mismatch.");
	        Assert.assertEquals(status, storePayload.getStatus(), "Status mismatch.");
	        Assert.assertEquals(complete, storePayload.isComplete(), "Complete flag mismatch.");
	        ExtentReportManager.test.pass("Assertions for order data passed");

	        // Schema validation
	        String jsonFilePath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\jsonSchemaFiles\\getStoreSchema.json";
	        File file = new File(jsonFilePath);
	        response.then().body(JsonSchemaValidator.matchesJsonSchema(file));
	        logger.info("Response matches JSON schema");
	        ExtentReportManager.test.pass("Schema validation passed");

	        // Test with invalid order ID
	        int wrongOrderID = 101;
	        logger.info("Testing with invalid order ID: {}", wrongOrderID);
	        ExtentReportManager.test.info("Testing with invalid order ID: " + wrongOrderID);

	        Response invalidResponse = StoreEndpoints.findPurchaseOrder(wrongOrderID);
	        logger.info("Asserting response status for invalid order ID");
	        Assert.assertEquals(invalidResponse.getStatusCode(), 404, "Expected status code is 404 for invalid order ID.");
	        ExtentReportManager.test.pass("Assertions for invalid order ID passed");
	    } 
	    catch (AssertionError e) 
	    {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        logger.debug("Assertion failure details", e);
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;  // Rethrow to ensure the test fails properly
	    } 
	    catch (Exception e) 
	    {
	        logger.error("Unexpected exception occurred: {}", e.getMessage());
	        logger.debug("Exception details", e);
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } 
	    finally 
	    {
	        logger.info("***** Finished testGetStoreOrderResponse *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	
	
	//authorization needed for this request = API key authentication
	@Test(priority = 3, description = "This test verifies the Get Store Inventories response returned by statuswise and validates the data")
	public void testGetStoreInventoriesResponse() 
	{	
	    // Start the test and log the initial info
	    ExtentReportManager.test.info("Test for retrieving store inventories started");
	    
	    logger.info("********************************************");
	    logger.info("***** Starting testGetStoreInventoriesResponse *****");

	    try {
	        // Sending GET request to retrieve store inventories
	        logger.info("Sending GET request to retrieve store inventories.");
	        ExtentReportManager.test.info("Sending GET request");
	        Response response = StoreEndpoints.returnPetInventories();

	        // Assertions for the response status code and content type
	        logger.info("Asserting response status code and content type");
	        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200.");
	        Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Expected content type is application/json.");
	        ExtentReportManager.test.pass("Assertions for status code and content type passed");

	        // Extracting data from the response
	        logger.info("Extracting 'sold', 'pending', and 'available' quantities from the response.");
	        int sold = response.then().extract().path("sold");
	        int pending = response.then().extract().path("pending");
	        int available = response.then().extract().path("available");

	        // Asserting the extracted quantities
	        logger.info("Asserting 'sold', 'pending', and 'available' quantities");
	        Assert.assertTrue(sold > 1 && sold < 1000, "'Sold' quantity is out of range.");
	        Assert.assertTrue(pending > 1 && pending < 1000, "'Pending' quantity is out of range.");
	        Assert.assertTrue(available > 1 && available < 1000, "'Available' quantity is out of range.");
	        ExtentReportManager.test.pass("Assertions for 'sold', 'pending', and 'available' quantities passed");

	        // Validating the JSON response against the schema
	        String jsonFilePath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\jsonSchemaFiles\\getStoreInventories.json";
	        File file = new File(jsonFilePath);
	        logger.info("Validating response against the JSON schema located at: {}", jsonFilePath);
	        response.then().body(JsonSchemaValidator.matchesJsonSchema(file));
	        ExtentReportManager.test.pass("Response successfully validated against the JSON schema");
	    } 
	    catch (AssertionError e) 
	    {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        logger.debug("Assertion failure details", e);
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;  // Rethrow to ensure test fails properly
	    } 
	    catch (Exception e) 
	    {
	        logger.error("Unexpected exception occurred: {}", e.getMessage());
	        logger.debug("Exception details", e);
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } 
	    finally 
	    {
	        logger.info("***** Finished testGetStoreInventoriesResponse *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }	
	}

	
	
	@Test(priority = 4, description = "This test verifies the Delete Store Order DELETE response and validates the data")
	public void testDeleteStoreOrderResponse() 
	{
	    // Start the test and log the initial info
	    ExtentReportManager.test.info("Test for deleting store order started");

	    logger.info("********************************************");
	    logger.info("***** Starting testDeleteStoreOrderResponse *****");

	    try {
	        // Sending DELETE request to remove a store order
	        logger.info("Sending DELETE request to remove the store order with ID: {}", storePayload.getId());
	        ExtentReportManager.test.info("Sending DELETE request for Order ID: " + storePayload.getId());
	        Response response = StoreEndpoints.deletePutchaseOrder(storePayload.getId());

	        // Assertions for status code, headers, and response time
	        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200.");
	        Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Expected content type is application/json.");
	        Assert.assertTrue(response.getTime() >= 200 && response.getTime() <= 2000, "Response time is outside the expected range (200-2000ms).");
	        ExtentReportManager.test.pass("Assertions for status, headers, and time passed");
	        
	        // Extract and log response data
	        int code = response.then().extract().path("code");
	        String type = response.then().extract().path("type");
	        String message = response.then().extract().path("message");
	        
	        // Assertions to verify that the response data 
	        logger.info("Asserting response data for code, type, and message");	        
	        Assert.assertEquals(code, 200, "Code mismatch.");
	        Assert.assertEquals(type, "unknown", "Type mismatch.");
	        Assert.assertEquals(message, String.valueOf(storePayload.getId()), "Message mismatch.");

/*	        // Schema validation
	        String jsonFilePath = "";
	        File file = new File(jsonFilePath);
	        response.then().body(JsonSchemaValidator.matchesJsonSchema(file));
	        logger.info("Response matches JSON schema");
	        ExtentReportManager.test.pass("Schema validation passed");
*/	        
	        
	        
	        // Sending GET request to verify if the order was deleted
	        logger.info("Sending GET request to find the deleted store order with ID: {}", storePayload.getId());
	        ExtentReportManager.test.info("Sending GET request for Order ID: " + storePayload.getId() + " after deletion");
	        response = StoreEndpoints.findPurchaseOrder(storePayload.getId());

	        // Log the response details: Status Code, Headers, and JSON Body
	        int statusCode$ = response.getStatusCode();
	        String headers$ = response.getHeaders().toString();
	        String jsonResponse$ = response.asPrettyString();

	        logger.info("StatusCode: {}", statusCode$);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>StatusCode: " + statusCode$ + "</span></pre></b>");

	        logger.info("Headers: {}", headers$);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>Headers: " + headers$ + "</span></pre></b>");

	        logger.info("JSON response body: {}", jsonResponse$);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>JSON response: " + jsonResponse$ + "</span></pre></b>");

	        Assert.assertEquals(statusCode$, 200, "Expected status code is 200.");
	        Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Expected content type is application/json.");
	        Assert.assertTrue(response.getTime() >= 200 && response.getTime() <= 2000, "Response time is outside the expected range (200-2000ms).");
	        ExtentReportManager.test.pass("Assertions for status, headers, and time passed");
	        
	        // Extract and log response data
	        int code$ = response.then().extract().path("code");
	        String type$ = response.then().extract().path("type");
	        String message$ = response.then().extract().path("message");
	        
	        // Assertions to verify that the response data matches the request payload
	        logger.info("Asserting order data between request and response");
	        Assert.assertEquals(code$, 1, "Code mismatch.");
	        Assert.assertEquals(type$, "error", "Type mismatch.");
	        Assert.assertEquals(message$, "Order not found", "Type mismatch.");
	        
/*	        // Schema validation
	        String jsonFilePath = "";
	        File file = new File(jsonFilePath);
	        response.then().body(JsonSchemaValidator.matchesJsonSchema(file));
	        logger.info("Response matches JSON schema");
	        ExtentReportManager.test.pass("Schema validation passed");
*/	        
	    } 
	    catch (AssertionError e) 
	    {
	        logger.error("Test assertion failed: {}", e.getMessage());
	        logger.debug("Assertion failure details", e);
	        ExtentReportManager.test.fail("Test failed: " + e.getMessage());
	        throw e;  // Rethrow to ensure test fails properly
	    } 
	    catch (Exception e) 
	    {
	        logger.error("Unexpected exception occurred: {}", e.getMessage());
	        logger.debug("Exception details", e);
	        ExtentReportManager.test.fail("Unexpected exception: " + e.getMessage());
	        throw e;
	    } 
	    finally 
	    {
	        logger.info("***** Finished testDeleteStoreOrderResponse *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}


}

/*
  ResponseBody resBody = response.body();
		String jsonBody = response.asString();
		JSONObject jsonObj = new JSONObject(jsonBody);
		Object soldField = jsonObj.get("sold");
		Object pendingField = jsonObj.get("pending");
		Object availableField = jsonObj.get("available");

		Assert.assertTrue(soldField instanceof Integer);
		Assert.assertTrue(pendingField instanceof Integer);
		Assert.assertTrue(availableField instanceof Integer);
*/





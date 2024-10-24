package api.test;


import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.endpoints.PetEndpoints;
import api.payload.Pet;
import api.utilities.DataProviders;
import api.utilities.ExtentReportManager;
import api.utilities.PojoSetter;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;


@Listeners(api.utilities.ExtentReportManager.class)
public class PetTest {
	
	Logger logger;
	
	@BeforeClass
	public void setUp()
	{
		logger = LogManager.getLogger(this.getClass());
	}
	
	
	@Test(priority = 1,groups="Regression",dataProvider = "PetData", dataProviderClass = DataProviders.class, description = "This test verifies the Add New Pet POST response and validates the data")
	public void testAddNewPetPostResponse(String petId, String categoryId, String categoryName, String petName, String petPhotoUrl1, String petPhotoUrl2, String tagId, String tagName, String petStatus) 
			throws JsonProcessingException, NoSuchMethodException, SecurityException 
	{
		
		// Start the test and log the initial info
		ExtentReportManager.test.info("Test for adding new pet started");
        
        logger.info("********************************************");
        logger.info("***** Starting testAddNewPetPostResponse *****");

        try {
            // Logging input data for clarity
            logger.info("Test Input Data: Pet ID: {}, Category ID: {}, Category Name: {}, Pet Name: {}, Photo URLs: [{}, {}], Tag ID: {}, Tag Name: {}, Pet Status: {}", 
                        petId, categoryId, categoryName, petName, petPhotoUrl1, petPhotoUrl2, tagId, tagName, petStatus);
	       
            ExtentReportManager.test.info(String.format(
            	    "Test Input Data: Pet ID: %s, Category ID: %s, Category Name: %s, Pet Name: %s, Photo URLs: [%s, %s], Tag ID: %s, Tag Name: %s, Pet Status: %s",
            	    petId, categoryId, categoryName, petName, petPhotoUrl1, petPhotoUrl2, tagId, tagName, petStatus
            	));

            // Create the Pet object using the data
            Pet petPayload = PojoSetter.newPetData(petId, categoryId, categoryName, petName, petPhotoUrl1, petPhotoUrl2, tagId, tagName, petStatus);
            logger.info("Pet object created successfully");
            ExtentReportManager.test.pass("Pet object created successfully");

            // Sending POST request to add a new pet
            logger.info("Sending POST request to add a new pet.");
            ExtentReportManager.test.info("Sending POST request");
            Response response = PetEndpoints.addNewPet(petPayload);
          
            // Extract and log the StatusCode, Headers, JSON response
	        int statusCode = response.getStatusCode();
	        String headers = response.getHeaders().toString();
	        String jsonResponse = response.asPrettyString();
	        logger.info("StatusCode: {}", statusCode);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>StatusCode: " + statusCode + "</span></pre></b>");
	        logger.info("Headers: {}", headers);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>Headers: " + headers + "</span></pre></b>");
	        logger.info("JSON response body: {}", jsonResponse);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>JSON response: " + jsonResponse + "</span></pre></b>");
	
            // Mapping JSON response to Pet object
            ObjectMapper objMap = new ObjectMapper();
            Pet petResponse = objMap.readValue(jsonResponse, Pet.class);
            logger.info("Response mapped to Pet object");
            ExtentReportManager.test.pass("Response successfully mapped to Pet object");

            // Assertions for the response status code, content type, and response time
            logger.info("Asserting response status, headers, and time");
            Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200.");
            Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Expected content type is application/json.");
            Assert.assertTrue(response.getTime() >= 200 && response.getTime() <= 3000, "Response time is outside the expected range (200-3000ms).");
            ExtentReportManager.test.pass("Assertions for status, headers, and time passed");

            // Schema validation
            String jsonFilePath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\jsonSchemaFiles\\putPetSchema.json";
    		File file = new File(jsonFilePath);
    		response.then().body(JsonSchemaValidator.matchesJsonSchema(file));
            
            // Assertions for the pet object data
            logger.info("Asserting pet data between request and response");
            Assert.assertEquals(petPayload.getiD(), petResponse.getiD(), "Pet ID mismatch.");
            Assert.assertEquals(petPayload.getCategORY().getId(), petResponse.getCategORY().getId(), "Category ID mismatch.");
            Assert.assertEquals(petPayload.getCategORY().getName(), petResponse.getCategORY().getName(), "Category Name mismatch.");
            Assert.assertEquals(petPayload.getNaME(), petResponse.getNaME(), "Pet Name mismatch.");
            ExtentReportManager.test.pass("Assertions for pet object data passed");

            // Asserting pet tags
            logger.info("Asserting pet tags");
            for (int i = 0; i < petPayload.getTaGS().size(); i++) 
            {
                Assert.assertEquals(petPayload.getTaGS().get(i).getId(), petResponse.getTaGS().get(i).getId(), "Tag ID mismatch at index " + i);
                Assert.assertEquals(petPayload.getTaGS().get(i).getName(), petResponse.getTaGS().get(i).getName(), "Tag Name mismatch at index " + i);
            }
            ExtentReportManager.test.pass("Tag assertions passed");

            // Asserting pet status
            logger.info("Asserting pet status");
            Assert.assertEquals(petPayload.getStatUS(), petResponse.getStatUS(), "Pet status mismatch.");
            ExtentReportManager.test.pass("Pet status assertion passed");

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
            logger.info("***** Finished testAddNewPetPostResponse *****");
            logger.info("********************************************");
            ExtentReportManager.test.info("Test finished");
        }	
    }
	
	
	@Test(priority = 2, dataProvider = "UpdatedPetData", dataProviderClass = DataProviders.class, description = "This test verifies the Update Existing Pet PUT response and validates the updated data")
	public void testUpdateExistingPetPutResponse(String petId, String categoryId, String categoryName, String petName, String petPhotoUrl1, String petPhotoUrl2, String tagId, String tagName, String petStatus) 
			throws JsonProcessingException 
	{
	    // Start the test and log the initial info
	    ExtentReportManager.test.info("Test for updating existing pet started");
	    
	    logger.info("********************************************");
	    logger.info("***** Starting testUpdateExistingPetPutResponse *****");
	
	    try {
	        // Logging input data for clarity
	        logger.info("Test Input Data: Pet ID: {}, Category ID: {}, Category Name: {}, Pet Name: {}, Photo URLs: [{}, {}], Tag ID: {}, Tag Name: {}, Pet Status: {}", 
	                    petId, categoryId, categoryName, petName, petPhotoUrl1, petPhotoUrl2, tagId, tagName, petStatus);
	
	        ExtentReportManager.test.info(String.format(
	        	    "Test Input Data: Pet ID: %s, Category ID: %s, Category Name: %s, Pet Name: %s, Photo URLs: [%s, %s], Tag ID: %s, Tag Name: %s, Pet Status: %s",
	        	    petId, categoryId, categoryName, petName, petPhotoUrl1, petPhotoUrl2, tagId, tagName, petStatus
	        	));
	
	        // Create the Pet object using the updated data
	        Pet pet1 = PojoSetter.updatePetData(petId, categoryId, categoryName, petName, petPhotoUrl1, petPhotoUrl2, tagId, tagName, petStatus);
	        logger.info("Pet object created with updated data");
	        ExtentReportManager.test.pass("Pet object created with updated data");
	
	        // Sending PUT request to update the existing pet
	        logger.info("Sending PUT request to update the existing pet.");
	        ExtentReportManager.test.info("Sending PUT request");
	        Response response = PetEndpoints.updateExistingPet(pet1);
	
	        

	        // Extract and log the StatusCode, Headers, JSON response
	        int statusCode = response.getStatusCode();
	        String headers = response.getHeaders().toString();
	        String jsonResponse = response.asPrettyString();
	        logger.info("StatusCode: {}", statusCode);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>StatusCode: " + statusCode + "</span></pre></b>");
	        logger.info("Headers: {}", headers);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>Headers: " + headers + "</span></pre></b>");
	        logger.info("JSON response body: {}", jsonResponse);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>JSON response: " + jsonResponse + "</span></pre></b>");
	
	        // Mapping JSON response to Pet object
	        ObjectMapper objMap = new ObjectMapper();
	        Pet pet2 = objMap.readValue(jsonResponse, Pet.class);
	        logger.info("Response mapped to Pet object");
	        ExtentReportManager.test.pass("Response successfully mapped to Pet object");
	
	        // Assertions for the response status code, content type, and response time
	        logger.info("Asserting response status, headers, and time");
	        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200.");
	        Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Expected content type is application/json.");
	        Assert.assertTrue(response.getTime() >= 200 && response.getTime() <= 3000, "Response time is outside the expected range (200-3000ms).");
	        ExtentReportManager.test.pass("Assertions for status, headers, and time passed");
	
	        // Assertions for the updated pet object data
	        logger.info("Asserting updated pet data between request and response");
	        Assert.assertEquals(pet1.getiD(), pet2.getiD(), "Pet ID mismatch.");
	        Assert.assertEquals(pet1.getCategORY().getId(), pet2.getCategORY().getId(), "Category ID mismatch.");
	        Assert.assertEquals(pet1.getCategORY().getName(), pet2.getCategORY().getName(), "Category Name mismatch.");
	        Assert.assertEquals(pet1.getNaME(), pet2.getNaME(), "Pet Name mismatch.");
	        Assert.assertEquals(pet1.getPhotoURLS().get(0), pet2.getPhotoURLS().get(0), "Photo URL 1 mismatch.");
	        Assert.assertEquals(pet1.getPhotoURLS().get(1), pet2.getPhotoURLS().get(1), "Photo URL 2 mismatch.");
	        ExtentReportManager.test.pass("Assertions for pet object data passed");
	
	        // Asserting pet tags
	        logger.info("Asserting pet tags");
	        for (int i = 0; i < pet1.getTaGS().size(); i++) 
	        {
	            Assert.assertEquals(pet1.getTaGS().get(i).getId(), pet2.getTaGS().get(i).getId(), "Tag ID mismatch at index " + i);
	            Assert.assertEquals(pet1.getTaGS().get(i).getName(), pet2.getTaGS().get(i).getName(), "Tag Name mismatch at index " + i);
	        }
	        ExtentReportManager.test.pass("Tag assertions passed");
	
	        // Asserting pet status
	        logger.info("Asserting pet status");
	        Assert.assertEquals(pet1.getStatUS(), pet2.getStatUS(), "Pet status mismatch.");
	        ExtentReportManager.test.pass("Pet status assertion passed");
	
	    } 
	    catch (AssertionError e) 
	    {
	    	logger.error("Test failed due to exception: {}", e.getMessage());
	        logger.debug("Exception details", e);
	        ExtentReportManager.test.fail("Test failed due to exception: " + e.getMessage());
	        Assert.fail();
	        throw e;
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
	        logger.info("***** Finished testUpdateExistingPetPutResponse *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }    
	}

	
	@Test(priority = 3, description = "This test verifies the Get Pet by Status response and checks for the presence of pet with name 'Lucky' and status 'pending'")
	public void testFindPetByStatusGetResponse() 
	{
	    // Start the test and log the initial info
	    ExtentReportManager.test.info("Test for finding pet by status started");
	    
	    logger.info("********************************************");
	    logger.info("***** Starting testFindPetByStatusGetResponse *****");

	    try {
	        String status = "pending";

	        // Sending GET request to find pet by status
	        logger.info("Sending GET request to find pets with status: {}", status);
	        ExtentReportManager.test.info("Sending GET request for status: " + status);
	        Response response = PetEndpoints.findPetByStatus(status);

	        // Extract and log the StatusCode, Headers, JSON response
	        int statusCode = response.getStatusCode();
	        String headers = response.getHeaders().toString();
	        String jsonResponse = response.asPrettyString();
	        logger.info("StatusCode: {}", statusCode);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>StatusCode: " + statusCode + "</span></pre></b>");
	        logger.info("Headers: {}", headers);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>Headers: " + headers + "</span></pre></b>");
	        logger.info("JSON response body: {}", jsonResponse);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>JSON response: " + jsonResponse + "</span></pre></b>");
	
	        // Convert response body to JSON array and validate pet data
	        JSONArray jo = new JSONArray(response.body().asString());
	        boolean nameFound = false;
	        boolean statusCorrect = true;

	        logger.info("Checking for pet with name 'Lucky' and status 'pending'");
	        ExtentReportManager.test.info("Checking for pet name 'Lucky'");

	        for (int i = 0; i < jo.length(); i++) 
	        {
	            String petName = jo.getJSONObject(i).get("name").toString();
	            if (petName.equals("Lucky")) 
	            {
	                nameFound = true;
	                break;
	            }

	            String resStatus = jo.getJSONObject(i).get("status").toString();
	            if (!resStatus.equalsIgnoreCase("pending")) 
	            {
	                statusCorrect = false;
	                break;
	            }
	        }

	        // Assertions for the response status code, content type, and response time
	        logger.info("Asserting response status, headers, and time");
	        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200.");
	        Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Expected content type is application/json.");
	        Assert.assertTrue(response.getTime() >= 200 && response.getTime() <= 3000, "Response time is outside the expected range (200-3000ms).");
	        ExtentReportManager.test.pass("Assertions for status, headers, and time passed");

	        // Assertions for pet name and status
	        Assert.assertTrue(nameFound, "Pet 'Lucky' not found in response.");
	        Assert.assertTrue(statusCorrect, "Some pets found with status other than 'pending'.");
	        ExtentReportManager.test.pass("Pet 'Lucky' and status 'pending' found in response");

	    } 
	    catch (AssertionError e) 
	    {
	    	logger.error("Test failed due to exception: {}", e.getMessage());
	        logger.debug("Exception details", e);
	        ExtentReportManager.test.fail("Test failed due to exception: " + e.getMessage());
	        Assert.fail();
	        throw e;
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
	        logger.info("***** Finished testUpdateExistingPetPutResponse *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}

	
	
	
	
	
	
	
	
/*Deprecated	
	
	@Test(priority = 4)
	public void testFindPetByTagsGetResponse() 
	{
		String tagName = "Red";
		Response response = PetEndpoints.findPetByTags(tagName);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
*/
	
	
	@Test(priority = 5, dataProvider = "PetData", dataProviderClass = DataProviders.class, description = "This test verifies the GET response for finding a pet by its ID.")
	public void testFindPetByPetIDGetResponse(String petId, String categoryId, String categoryName, String petName, String petPhotoUrl1, String petPhotoUrl2, String tagId, String tagName, String petStatus) 
	{
	    // Start the test and log the initial info
	    ExtentReportManager.test.info("Test for finding pet by ID started");
	
	    logger.info("********************************************");
	    logger.info("***** Starting testFindPetByPetIDGetResponse *****");
	
	    try {
	        // Parsing pet ID from String to integer
	        int petId$ = Integer.parseInt(petId);
	        logger.info("Sending GET request to find pet with ID: {}", petId$);
	        ExtentReportManager.test.info("Sending GET request for Pet ID: " + petId$);
	
	        // Sending GET request to find the pet by ID
	        Response response = PetEndpoints.findPet(petId$);
	
	        // Extract and log the StatusCode, Headers, JSON response
	        int statusCode = response.getStatusCode();
	        String headers = response.getHeaders().toString();
	        String jsonResponse = response.asPrettyString();
	        logger.info("StatusCode: {}", statusCode);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>StatusCode: " + statusCode + "</span></pre></b>");
	        logger.info("Headers: {}", headers);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>Headers: " + headers + "</span></pre></b>");
	        logger.info("JSON response body: {}", jsonResponse);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>JSON response: " + jsonResponse + "</span></pre></b>");
	
	        // Assertions for the pet ID in the response and other response details
	        logger.info("Asserting response data for pet ID: {}", petId);
	        Assert.assertEquals(response.then().extract().path("id").toString(), petId, "Pet ID mismatch.");
	        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200.");
	        Assert.assertTrue(response.getTime() >= 200 && response.getTime() <= 3000, "Response time is outside the expected range (200-3000ms).");
	        Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Expected content type is application/json.");
	        ExtentReportManager.test.pass("Assertions for status, headers, and pet ID passed");
	
	    } 
	    catch (AssertionError e) 
	    {
	    	logger.error("Test failed due to exception: {}", e.getMessage());
	        logger.debug("Exception details", e);
	        ExtentReportManager.test.fail("Test failed due to exception: " + e.getMessage());
	        Assert.fail();
	        throw e;
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
	        logger.info("***** Finished testUpdateExistingPetPutResponse *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	
	@Test(priority = 6, description = "This test verifies the POST response for updating an existing pet.")
	public void testUpdatePetPostResponse() 
	{ 
	    // Start the test and log the initial info
	    ExtentReportManager.test.info("Test for updating pet started");

	    logger.info("********************************************");
	    logger.info("***** Starting testUpdatePetPostResponse *****");

	    try {
	        // Define pet details for the update
	        String petId = "30";
	        String petName = "Luna";
	        String status = "pending";

	        // Sending POST request to update the pet
	        logger.info("Sending POST request to update pet with ID: {} to name: {} and status: {}", petId, petName);
	        ExtentReportManager.test.info("Updating pet with ID: {petId},  Name: {petName},  Status: {status}");
	        Response response = PetEndpoints.updatePet(petId, petName, status);

	        // Extract and log the StatusCode, Headers, JSON response
	        int statusCode = response.getStatusCode();
	        String headers = response.getHeaders().toString();
	        String jsonResponse = response.asPrettyString();
	        logger.info("StatusCode: {}", statusCode);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>StatusCode: " + statusCode + "</span></pre></b>");
	        logger.info("Headers: {}", headers);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>Headers: " + headers + "</span></pre></b>");
	        logger.info("JSON response body: {}", jsonResponse);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>JSON response: " + jsonResponse + "</span></pre></b>");
	
	        // Assertions for the response details
	        long resTime = response.getTime();
	        logger.info("Asserting response details for pet ID: {}", petId);
	        Assert.assertEquals(response.then().extract().path("message").toString(), petId, "Expected message does not match the pet ID.");
	        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200.");
	        Assert.assertTrue(resTime >= 200 && resTime <= 3000, "Response time is outside the expected range (200-3000ms).");
	        Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Expected content type is application/json.");
	        ExtentReportManager.test.pass("Assertions for update response passed.");

	        // Verifying the updated pet name
	        Assert.assertEquals(PetEndpoints.findPet(Integer.parseInt(petId)).then().extract().path("name").toString(), petName, "Updated pet name does not match.");

	    } 
	    catch (AssertionError e) 
	    {
	    	logger.error("Test failed due to exception: {}", e.getMessage());
	        logger.debug("Exception details", e);
	        ExtentReportManager.test.fail("Test failed due to exception: " + e.getMessage());
	        Assert.fail();
	        throw e;
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
	        logger.info("***** Finished testUpdateExistingPetPutResponse *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	
	@Test(priority = 7, description = "This test verifies the POST response for uploading an image for an existing pet.")
	public void testUploadPetImagePostResponse() 
	{ 
	    // Start the test and log the initial info
	    ExtentReportManager.test.info("Test for uploading pet image started");

	    logger.info("********************************************");
	    logger.info("***** Starting testUploadPetImagePostResponse *****");

	    try {
	        // Define pet details and image file for the upload
	        String petId = "30";
	        String additionalMetaData = "Tiger Image";
	        File myFile = new File("F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\images\\2....jpg");

	        // Sending POST request to upload the pet image
	        logger.info("Sending POST request to upload image for pet ID: {} with metadata: {}", petId, additionalMetaData);
	        ExtentReportManager.test.info("Uploading image for pet ID: {petId}  with additional metadata: { additionalMetaData}");
	        Response response = PetEndpoints.uploadPetImage(petId, additionalMetaData, myFile);

	        // Extract and log the StatusCode, Headers, JSON response
	        int statusCode = response.getStatusCode();
	        String headers = response.getHeaders().toString();
	        String jsonResponse = response.asPrettyString();
	        logger.info("StatusCode: {}", statusCode);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>StatusCode: " + statusCode + "</span></pre></b>");
	        logger.info("Headers: {}", headers);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>Headers: " + headers + "</span></pre></b>");
	        logger.info("JSON response body: {}", jsonResponse);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>JSON response: " + jsonResponse + "</span></pre></b>");
	
	        // Assertions for the response details
	        logger.info("Asserting response details for uploaded image of pet ID: {}", petId);
	        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200.");
	        Assert.assertTrue(response.then().extract().path("message").toString().contains(additionalMetaData), "Response message does not contain expected metadata.");
	        Assert.assertTrue(response.getTime() >= 50 && response.getTime() <= 15000, "Response time is outside the expected range (50-15000ms).");
	        Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Expected content type is application/json.");
	        ExtentReportManager.test.pass("Assertions for upload response passed.");

	       } 
	    catch (AssertionError e) 
	    {
	    	logger.error("Test failed due to exception: {}", e.getMessage());
	        logger.debug("Exception details", e);
	        ExtentReportManager.test.fail("Test failed due to exception: " + e.getMessage());
	        Assert.fail();
	        throw e;
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
	        logger.info("***** Finished testUpdateExistingPetPutResponse *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}

	
	@Test(priority = 8, description = "This test verifies the DELETE response for removing a pet and checks the status after deletion.")
	public void testDeletePetDeleteResponse() 
	{
	    // Start the test and log the initial info
	    ExtentReportManager.test.info("Test for deleting a pet started");
	    
	    logger.info("********************************************");
	    logger.info("***** Starting testDeletePetDeleteResponse *****");

	    try {
	        String petId = "20";
	        String api_key = "special-key";

	        // Sending DELETE request to delete a pet
	        logger.info("Sending DELETE request to remove pet with ID: {}", petId);
	        ExtentReportManager.test.info("Sending DELETE request for pet ID: " + petId);
	        Response response = PetEndpoints.deletePet(petId, api_key);
	        
	        // Extract and log the StatusCode, Headers, JSON response
	        int statusCode = response.getStatusCode();
	        String headers = response.getHeaders().toString();
	        String jsonResponse = response.asPrettyString();
	        logger.info("StatusCode: {}", statusCode);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>StatusCode: " + statusCode + "</span></pre></b>");
	        logger.info("Headers: {}", headers);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>Headers: " + headers + "</span></pre></b>");
	        logger.info("JSON response body: {}", jsonResponse);
	        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>JSON response: " + jsonResponse + "</span></pre></b>");
	
	        // Assertions for the response status code and message
	        logger.info("Asserting response status, time, and headers");
	        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200.");
	        long resTime = response.getTime();
	        Assert.assertEquals(response.then().extract().path("message").toString(), "20", "Expected message is '20'.");
	        Assert.assertTrue(resTime >= 50 && resTime <= 5000, "Response time is outside the expected range (50-5000ms).");
	        Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Expected content type is application/json.");
	        ExtentReportManager.test.pass("Assertions for delete response passed");

	        // Verify the pet has been deleted
	        logger.info("Verifying that the pet has been deleted.");
	        Response deletedResponse = PetEndpoints.deletePet(petId, api_key);
	        Assert.assertEquals(deletedResponse.statusCode(), 404, "Expected status code after deletion is 404.");
	        ExtentReportManager.test.pass("Pet deletion verified, status code is 404.");

	    } 
	    catch (AssertionError e) 
	    {
	    	logger.error("Test failed due to exception: {}", e.getMessage());
	        logger.debug("Exception details", e);
	        ExtentReportManager.test.fail("Test failed due to exception: " + e.getMessage());
	        Assert.fail();
	        throw e;
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
	        logger.info("***** Finished testDeletePetDeleteResponse *****");
	        logger.info("********************************************");
	        ExtentReportManager.test.info("Test finished");
	    }
	}
	
	
	@Test(priority = 9)
	public void deleberatlyFailTest() throws InterruptedException 
	{
		Assert.fail();
	}
	
	
	@Test(priority = 10, dependsOnMethods= "deleberatlyFailTest", timeOut = 100)
	public void deleberatlySkippedTest1() throws InterruptedException 
	{
		Thread.sleep(500);
	}
	
	
	@Test(priority = 11, dependsOnMethods= "deleberatlyFailTest", timeOut = 100)
	public void deleberatlySkippedTest2() throws InterruptedException 
	{
		Thread.sleep(500);
	}

}

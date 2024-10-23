package api.endpoints;

import java.io.File;
import api.path.Routes;
import api.payload.Pet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetEndpoints {
	
	public static Response addNewPet(Pet petload)
	{
		Response response = RestAssured.given()
				.accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(petload)
			.when()
				.post(Routes.pet_post_url);
		
		return response;
	}

	public static Response updateExistingPet(Pet petload)
	{
		Response response = RestAssured.given()
				.accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(petload)
			.when()
				.put(Routes.pet_put_url);
		
		return response;
	}
	
	public static Response updatePet(String petId, String petName, String status)
	{
		Response response = RestAssured.given()
		        .pathParam("petId", petId)
		        .formParam("name", petName)
		        .formParam("status", status)
			.when()
				.post(Routes.pet_post_url1);
		
		return response;
	}
	
	public static Response findPetByStatus(String status)
	{
		Response response = RestAssured.given()
				.queryParam("status", status)
			.when()
				.get(Routes.pet_get_url);
		
		return response;
	}
	
	
	
	public static Response findPetByTags(String tag)
	{
		Response response = RestAssured.given()
				.queryParam("tags", tag)
			.when()
				.get(Routes.pet_get_url2);
		
		return response;
	}
	

	public static Response findPet(int petId)
	{
		Response response = RestAssured.given()
		        .pathParam("petId", petId)
			.when()
				.get(Routes.pet_get_url1);
		
		return response;
	}
	

	
	public static Response uploadPetImage(String petId, String additionalMetadata, File file)
	{
		Response response = RestAssured.given()
				.pathParam("petId", petId)
			    .formParam("additionalMetadata", additionalMetadata)
				.contentType(ContentType.MULTIPART) //this content type of any file.
				.multiPart("file", file) //this is because in postman in body , form-data radio butoon is selected
			.when()
				.post(Routes.pet_post_url2);
		
		return response;
	}
	
	
	
	public static Response deletePet(String petId, String api_key)
	{
		Response response = RestAssured.given()
				.header("api_key", api_key)
		        .pathParam("petId", petId)
			.when()
				.delete(Routes.pet_delete_url);
		
		return response;
	}

}

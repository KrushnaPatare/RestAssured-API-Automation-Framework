package api.endpoints;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONTokener;

import api.path.Routes;
import api.payload.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndpoints {
	
	public static Response createUser(User uspayload)
	{
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(uspayload)
		.when()
			.post(Routes.user_post_url);
		
		return response;
	}
	
	
	public static Response readUser(String userName)
	{
		Response response = RestAssured.given()
				.pathParam("username", userName)
		.when()
			.get(Routes.user_get_url);
		
		return response;
	}
	
	
	public static Response updateUser(User payload, String userName)
	{
		Response response = RestAssured.given()
				.accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(payload)
				.pathParam("username", userName)
		.when()
			.put(Routes.user_put_url);
		
		return response;
	}
	
	
	public static Response deleteUser(String userName)
	{
		Response response = RestAssured.given()
				.pathParam("username", userName)
		.when()
			.delete(Routes.user_delete_url);
		
		return response;
	}

	public static Response createUsers(String filePath) throws IOException 
	{	
		File f = new File (filePath);
		FileReader fr = new FileReader(f);
		JSONTokener jt = new JSONTokener(fr);
		JSONArray data = new JSONArray(jt);
		
		Response response = RestAssured.given()
			.contentType(ContentType.JSON)
			.body(data.toString())
		.when()
			.post(Routes.user_post_url1);
		
		return response;
	}
	
	public static Response loginUser(String userName, String password)
	{
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("username", userName);
		data.put("password", password);

		Response response = RestAssured.given()
			.queryParam("api_key", "special-key")
			.contentType(ContentType.JSON)
			.body(data)
		.when()
			.get(Routes.user_get_url1); 
		
		return response;
	}
	
	public static Response logoutUser()
	{
		Response response = RestAssured.given()	
		.when()
			.get(Routes.user_get_url2); 

		return response;
	}
	



}

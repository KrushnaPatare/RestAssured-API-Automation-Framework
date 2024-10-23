package api.endpoints;

import api.path.Routes;
import api.payload.Store;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class StoreEndpoints {
	

	
	public static Response placeOrderForPet(Store stpayload) 
	{
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(stpayload)
			.when()
				.post(Routes.store_post_url);
		
		return response;
	}
	
	public static Response findPurchaseOrder(int orderId)
	{
		Response response = RestAssured.given()
				.pathParam("orderId", orderId)
			.when()
				.get(Routes.store_get_url);
		
		return response;
	}
	
	public static Response returnPetInventories()
	{
		Response response = RestAssured.given()
				.queryParam("api_key", "special-key")
			.when()
				.get(Routes.store_get_url1);
		
		return response;
	}
	
	public static Response deletePutchaseOrder(int orderId)
	{
		Response response = RestAssured.given()
				.pathParam("orderId", orderId)
			.when()
				.delete(Routes.store_delete_url);
		
		return response;
	}
	

}

package api.payloadManager;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;


	

	public String getId() 
	{
		return id;
	}

	public void setId(String categoryId) 
	{
		this.id = categoryId;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	

}

package api.payloadManager;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tag {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;




	public String getId() 
	{
		return id;
	}

	public void setId(String tagId) 
	{
		this.id = tagId;
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

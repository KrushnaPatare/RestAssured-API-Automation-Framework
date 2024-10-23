package api.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import api.payloadManager.Category;
import api.payloadManager.Tag;

public class Pet 
{
	@JsonProperty("id")
	private String iD;
	
	@JsonProperty("category")
	private Category categORY;
	
	@JsonProperty("name")
	private String naME;
	
	@JsonProperty("photoUrls")
	private List<String> photoURLS;
	
	@JsonProperty("tags")
	private List<Tag> taGS;
	
	@JsonProperty("status")
	private String statUS;

	

	
	public String getiD() 
	{
		return iD;
	}

	public void setiD(String iD) 
	{
		this.iD = iD;
	}

	public Category getCategORY() 
	{
		return categORY;
	}

	public void setCategORY(Category categORY) 
	{
		this.categORY = categORY;
	}

	public String getNaME() 
	{
		return naME;
	}

	public void setNaME(String naME) 
	{
		this.naME = naME;
	}

	public List<String> getPhotoURLS() 
	{
		return photoURLS;
	}

	public void setPhotoURLS(List<String> photoURLS) 
	{
		this.photoURLS = photoURLS;
	}

	public List<Tag> getTaGS() 
	{
		return taGS;
	}

	public void setTaGS(List<Tag> taGS) 
	{
		this.taGS = taGS;
	}

	public String getStatUS() 
	{
		return statUS;
	}

	public void setStatUS(String statUS) 
	{
		this.statUS = statUS;
	}
	

	


}

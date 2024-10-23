package api.utilities;

import java.util.ArrayList;
import java.util.List;

import api.payload.Pet;
import api.payloadManager.Category;
import api.payloadManager.Tag;

public class PojoSetter {
	
	public static Pet newPetData(String petId, String categoryId, String categoryName, String petName, String petPhotoUrl1, String petPhotoUrl2, String tagId, String tagName, String petStatus ) 
	{
		 // Create a Pet object
        Pet pet = new Pet();

        // Calling setter methods on the Pet object
        pet.setiD(petId);
        
        // Set Category
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        pet.setCategORY(category);

        pet.setNaME(petName);

        // Set Photo URLs
        List<String> photoUrls = new ArrayList<>();
        photoUrls.add(petPhotoUrl1);
        photoUrls.add(petPhotoUrl2);
        pet.setPhotoURLS(photoUrls);

        // Set Tags
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setId(tagId);
        tag.setName(tagName);
        tags.add(tag);
        pet.setTaGS(tags);

        // Set status
        pet.setStatUS(petStatus);
        
        
        
        return pet;
	}
	
	public static Pet updatePetData(String petId, String categoryId, String categoryName, String petName, String petPhotoUrl1, String petPhotoUrl2, String tagId, String tagName, String petStatus ) 
	{
		 // Create a Pet object
        Pet pet = new Pet();

        // Calling setter methods on the Pet object
        pet.setiD(petId);
        
        // Set Category
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        pet.setCategORY(category);

        pet.setNaME(petName);

        // Set Photo URLs
        List<String> photoUrls = new ArrayList<>();
        photoUrls.add(petPhotoUrl1);
        photoUrls.add(petPhotoUrl2);
        pet.setPhotoURLS(photoUrls);

        // Set Tags
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setId(tagId);
        tag.setName(tagName);
        tags.add(tag);
        pet.setTaGS(tags);

        // Set status
        pet.setStatUS(petStatus);
        
        
        
        return pet;
	}
	
	
	
	

}

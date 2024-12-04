package api.utilities;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

public class DynamicDTO {
	
    private Map<String, Object> properties = new HashMap<>();

    public void setProperty(String key, Object value) 
    {
        properties.put(key, value);
    }

    public Object getProperty(String key) 
    {
        return properties.get(key);
    }

    @JsonAnyGetter // Ensures map contents are serialized directly
    public Map<String, Object> getProperties() 
    {
        return properties;
    }
    
    
}


/*
 * public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        // Create and populate DynamicDTO
        DynamicDTO dto = new DynamicDTO();
        dto.setProperty("name", "Krushna");
        dto.setProperty("age", 28);

        // Convert to JSON
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dto);

        System.out.println(json); // Output: {"name":"Krushna","age":28}
    }
}
 * */


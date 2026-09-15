package cute.bril;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.InputStream;



public class BrilJsonReader {

    static JsonNode readTree(InputStream input) {
        JsonNode root = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            root = mapper.readTree(input);
        } catch(IOException e) {
            System.err.println("error: " + e.getMessage());
        }
        return root;
    }

}

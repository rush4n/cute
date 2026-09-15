package cute.bril;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import java.io.InputStream;

public class BrilJsonReaderTest {
    @Test
    void testReadTree() {
        InputStream input = BrilJsonReaderTest.class
                .getResourceAsStream("/bril/add.json");
        assertNotNull(input);

        JsonNode root = BrilJsonReader.readTree(input);
        JsonNode functions = root.get("functions");
        JsonNode name = functions.get(0).get("name");
        JsonNode instructions = functions.get(0).get("instrs");

        /* ASSERTIONS */
        assertTrue(root.isObject());
        assertTrue(functions.isArray());
        assertEquals(1, functions.size());
        assertEquals("main", name.asText());
        assertEquals(4, instructions.size());

    }
}

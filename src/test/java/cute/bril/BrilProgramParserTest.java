package cute.bril;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class BrilProgramParserTest {

    @Test
    void parsesFunctionMetadata() {
        InputStream input = BrilProgramParserTest.class
                .getResourceAsStream("/bril/add.json");
        assertNotNull(input);

        JsonNode root = BrilJsonReader.readTree(input);
        BrilProgram program = new BrilProgramParser().parse(root);

        assertEquals(1, program.functions().size());

        BrilFunction function = program.functions().get(0);
        assertEquals("main", function.name());
        assertEquals(4, function.instructions().size());
    }
}

package cute.bril;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.InputStream;
import java.util.List;
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
        assertEquals(List.of(), function.arguments());
        assertNull(function.returnType());
        assertEquals(4, function.instructions().size());

        BrilInstruction constant = function.instructions().get(0);
        assertEquals("v0", constant.destination());
        assertEquals("const", constant.op());
        assertEquals("int", constant.type());
        assertEquals(1, constant.value().asInt());
        assertEquals(List.of(), constant.arguments());
        assertEquals(List.of(), constant.labels());
        assertNull(constant.label());

        BrilInstruction add = function.instructions().get(2);
        assertEquals("add", add.op());
        assertEquals(List.of("v0", "v1"), add.arguments());
    }

    @Test
    void parsesBranchesAndLabels() {
        InputStream input = BrilProgramParserTest.class
                .getResourceAsStream("/bril/branch.json");
        assertNotNull(input);

        JsonNode root = BrilJsonReader.readTree(input);
        BrilFunction function = new BrilProgramParser().parse(root).functions().get(0);

        BrilInstruction branch = function.instructions().get(2);
        assertEquals("br", branch.op());
        assertEquals(List.of("b"), branch.arguments());
        assertEquals(List.of("there", "here"), branch.labels());

        BrilInstruction label = function.instructions().get(3);
        assertEquals("here", label.label());
        assertNull(label.op());
    }
}

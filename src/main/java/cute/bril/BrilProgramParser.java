package cute.bril;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class BrilProgramParser {

    BrilProgram parse(JsonNode root) {
        List<BrilFunction> functions = new ArrayList<>();
        int i = 0;
        JsonNode node;

        while ((node = root.get("functions").get(i)) != null) {
            String name = node.get("name").asText();
            List<JsonNode> instructions = getInstructionsList(node.get("instrs"));
            BrilFunction function = new BrilFunction(name, instructions);
            i++;
            functions.add(function);
        }

        return  new BrilProgram(functions);

    }

    List<JsonNode> getInstructionsList(JsonNode node) {
        int i = 0;
        List<JsonNode> instructions = new ArrayList<>();
        JsonNode instruction;

        while ((instruction = node.get(i)) != null) {
            instructions.add(instruction);
            i++;
        }

        return instructions;
    }
}

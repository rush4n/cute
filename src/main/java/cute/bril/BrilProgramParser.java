package cute.bril;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class BrilProgramParser {

    BrilProgram parse(JsonNode root) {
        List<BrilFunction> functions = new ArrayList<>();
        int i = 0;
        JsonNode node;

        while ((node = root.get("functions").get(i++)) != null) {
            String name = node.get("name").asText();
            List<BrilFunctionArgument> arguments = getFunctionArgumentsList(node.path("args"));
            String returnType = node.path("type").asText(null);
            List<BrilInstruction> instructions = getInstructionsList(node.get("instrs"));
            BrilFunction function = new BrilFunction(name, arguments, returnType, instructions);
            functions.add(function);
        }

        return  new BrilProgram(functions);

    }

    List<BrilFunctionArgument> getFunctionArgumentsList(JsonNode node) {
        List<BrilFunctionArgument> arguments = new ArrayList<>();

        for (JsonNode argument : node) {
            arguments.add(new BrilFunctionArgument(
                    argument.path("name").asText(null),
                    argument.path("type").asText(null)));
        }

        return arguments;
    }

    List<BrilInstruction> getInstructionsList(JsonNode node) {
        int i = 0;
        List<BrilInstruction> instructions = new ArrayList<>();
        JsonNode jsonInstruction;

        while ((jsonInstruction = node.get(i++)) != null) {
            String destination = jsonInstruction.path("dest").asText(null);
            String op = jsonInstruction.path("op").asText(null);
            String type = jsonInstruction.path("type").asText(null);
            JsonNode value = jsonInstruction.path("value");
            String label = jsonInstruction.path("label").asText(null);
            List<String> labels = new ArrayList<>();
            List<String> arguments = new ArrayList<>();

            int j = 0;
            int k = 0;
            JsonNode val;

            while((val = jsonInstruction.path("labels").get(j++)) != null) labels.add(val.asText());
            while((val = jsonInstruction.path("args").get(k++)) != null) arguments.add(val.asText());

            BrilInstruction instruction = new BrilInstruction(destination,
                    op,
                    type,
                    value,
                    arguments,
                    label,
                    labels);
            instructions.add(instruction);
        }

        return instructions;
    }
}

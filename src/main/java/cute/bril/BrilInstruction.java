package cute.bril;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

public record BrilInstruction(String destination,
                              String op,
                              String type,
                              JsonNode value,
                              List<String> arguments,
                              String label,
                              List<String> labels) {}


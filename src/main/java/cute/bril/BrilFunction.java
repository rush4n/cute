package cute.bril;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

public record BrilFunction(String name, List<JsonNode> instructions) {}


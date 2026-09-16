package cute.bril;

import java.util.List;

public record BrilFunction(
        String name,
        List<BrilFunctionArgument> arguments,
        String returnType,
        List<BrilInstruction> instructions) {}

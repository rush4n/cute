package cute.bril.cfg;

import cute.bril.BrilInstruction;
import java.util.List;

public record BasicBlock(int id, List<BrilInstruction> instructions) {}

package cute.bril.cfg;

import cute.bril.BrilFunction;
import cute.bril.BrilInstruction;

import java.util.ArrayList;
import java.util.List;

public class BasicBlockBuilder {

    List<BasicBlock> build(BrilFunction function) {
        int id = 0;
        List<BrilInstruction> instructions = new ArrayList<>();
        List<BasicBlock> blocks = new ArrayList<>();

        for (BrilInstruction instruction : function.instructions()) {
           if (instruction.op() != null) {
               String curr_op = instruction.op();
               if (curr_op.equals("br") ||
                       curr_op.equals("jmp") ||
                       curr_op.equals("ret")) {
                   instructions.add(instruction);
                   blocks.add(new BasicBlock(id++, instructions));
                   instructions = new ArrayList<>();
               } else instructions.add(instruction);
           } else {
               if (!instructions.isEmpty()) blocks.add(new BasicBlock(id++, instructions));
               instructions = new ArrayList<>();
               instructions.add(instruction);
           }
        }

        if (!instructions.isEmpty()) blocks.add(new BasicBlock(id++, instructions));

        return blocks;
    }
}

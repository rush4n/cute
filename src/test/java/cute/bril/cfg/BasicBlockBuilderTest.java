package cute.bril.cfg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cute.bril.BrilFunction;
import cute.bril.BrilInstruction;
import java.util.List;
import org.junit.jupiter.api.Test;

class BasicBlockBuilderTest {

    private final BasicBlockBuilder builder = new BasicBlockBuilder();

    @Test
    void keepsStraightLineCodeInOneBlock() {
        List<BasicBlock> blocks = builder.build(function(
                operation("const"),
                operation("const"),
                operation("add"),
                operation("print")));

        assertEquals(List.of(4), blockSizes(blocks));
    }

    @Test
    void startsANewBlockAtALabel() {
        List<BasicBlock> blocks = builder.build(function(
                operation("const"),
                operation("const"),
                operation("jmp"),
                label("label"),
                operation("add"),
                operation("print")));

        assertEquals(List.of(3, 3), blockSizes(blocks));
    }

    @Test
    void splitsAtBranchesAndLabels() {
        List<BasicBlock> blocks = builder.build(function(
                operation("const"),
                operation("const"),
                operation("br"),
                label("here"),
                operation("const"),
                label("there"),
                operation("print")));

        assertEquals(List.of(3, 2, 2), blockSizes(blocks));
    }

    @Test
    void endsABlockAtATerminatorWithoutNeedingALabel() {
        List<BasicBlock> blocks = builder.build(function(
                operation("const"),
                operation("br"),
                operation("print")));

        assertEquals(List.of(2, 1), blockSizes(blocks));
    }

    @Test
    void keepsAnEntryLabelInTheFirstBlock() {
        List<BasicBlock> blocks = builder.build(function(
                label("entry"),
                operation("print")));

        assertEquals(List.of(2), blockSizes(blocks));
    }

    @Test
    void doesNotCreateAnEmptyBlockAfterAFinalTerminator() {
        List<BasicBlock> blocks = builder.build(function(
                operation("const"),
                operation("ret")));

        assertEquals(List.of(2), blockSizes(blocks));
    }

    @Test
    void returnsNoBlocksForAnEmptyFunction() {
        List<BasicBlock> blocks = builder.build(function());

        assertEquals(List.of(), blockSizes(blocks));
    }

    @Test
    void assignsSequentialBlockIdsInSourceOrder() {
        List<BasicBlock> blocks = builder.build(function(
                operation("const"),
                operation("br"),
                label("here"),
                operation("const"),
                label("there"),
                operation("print")));

        assertEquals(List.of(0, 1, 2), blockIds(blocks));
    }

    private BrilFunction function(BrilInstruction... instructions) {
        return new BrilFunction("main", List.of(), null, List.of(instructions));
    }

    private BrilInstruction operation(String op) {
        return new BrilInstruction(null, op, null, null, List.of(), null, List.of());
    }

    private BrilInstruction label(String name) {
        return new BrilInstruction(null, null, null, null, List.of(), name, List.of());
    }

    private List<Integer> blockSizes(List<BasicBlock> blocks) {
        return blocks.stream().map(block -> block.instructions().size()).toList();
    }

    private List<Integer> blockIds(List<BasicBlock> blocks) {
        return blocks.stream().map(BasicBlock::id).toList();
    }
}

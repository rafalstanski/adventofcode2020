package com.hurricane.kata.adventofcode2020.day8


import com.hurricane.kata.adventofcode2020.Puzzle
import com.hurricane.kata.adventofcode2020.PuzzleIntTestSupport
import spock.lang.Unroll

import static com.hurricane.kata.adventofcode2020.day8.OperationType.ACC
import static com.hurricane.kata.adventofcode2020.day8.OperationType.JMP
import static com.hurricane.kata.adventofcode2020.day8.OperationType.NOP

@Unroll
class Day8Test extends PuzzleIntTestSupport {

    @Override
    protected Puzzle puzzlePart1() {
        new Puzzle(
                inputLocation: 'test_entries_day8.txt',
                solution: new Day8Part1Runner(),
                expectedAnswer: 5
        )
    }

    @Override
    protected Puzzle puzzlePart2() {
        new Puzzle(
                inputLocation: 'test_entries_day8.txt',
                solution: new Day8Part2Runner(),
                expectedAnswer: 8
        )
    }

    def "should parser instruction string"() {
        when:
            def instruction = parseInstruction(instructionString)

        then:
            instruction.operation == expectedOperation
            instruction.originalValue == expectedOriginalValue
            instruction.value == expectedValue

        where:
            instructionString || expectedOperation | expectedOriginalValue | expectedValue
            'nop +0'          || NOP               | '+0'                  | 0
            'acc +1'          || ACC               | '+1'                  | 1
            'jmp +41'         || JMP               | '+41'                 | 41
            'nop -1'          || NOP               | '-1'                  | -1
            'acc -20'         || ACC               | '-20'                 | -20
            'jmp -500'        || JMP               | '-500'                | -500
    }

    def "instruction acc should increase/decrease accumulator"() {
        when:
            def program = executeProgram(instructions)

        then:
            program.accumulator == expectedAccumulator

        where:
            instructions || expectedAccumulator
            ['acc +1']   || 1
            ['acc +5']   || 5
            ['acc +40']  || 40
            ['acc -1']   || -1
            ['acc -5']   || -5
            ['acc -40']  || -40
    }

    def "chain of acc instructions should cumulate increase/decrease operations"() {
        when:
            def result = executeProgram(instructions)

        then:
            result.accumulator == expectedAccumulator

        where:
            instructions                      || expectedAccumulator
            ['acc +1', 'acc +3']              || 4
            ['acc -1', 'acc -3']              || -4
            ['acc +1', 'acc +10', 'acc +100'] || 111
            ['acc -1', 'acc -10', 'acc -100'] || -111
    }

    def "instruction nop should not change accumulator"() {
        when:
            def result = executeProgram(instructions)

        then:
            result.accumulator == 0

        where:
            instructions || _
            ['nop +1']   || _
            ['nop +5']   || _
            ['nop +40']  || _
            ['nop -1']   || _
            ['nop -5']   || _
            ['nop -40']  || _
    }

    def "instruction nop should not interrupt acc chain"() {
        when:
            def result = executeProgram(instructions)

        then:
            result.accumulator == expectedAccumulator

        where:
            instructions                                          || expectedAccumulator
            ['acc +1', 'nop +1', 'acc +3']                        || 4
            ['nop +1', 'acc -1', 'acc -3']                        || -4
            ['acc +1', 'nop +1', 'acc +10', 'nop +1', 'acc +100'] || 111
            ['acc -1', 'acc -10', 'nop +1', 'acc -100', 'nop +1'] || -111
    }

    def "instruction jmp with positive value should jump forward specified offset of instructions"() {
        given:
            def baseInstructions = ['acc +1', 'acc +10', 'acc +100', 'acc +1000']

        when:
            def result = executeProgram(jumpInstruction + baseInstructions)

        then:
            result.accumulator == expectedAccumulator

        where:
            jumpInstruction || expectedAccumulator
            ['jmp +1']      || 1111
            ['jmp +2']      || 1110
            ['jmp +3']      || 1100
            ['jmp +4']      || 1000
            ['jmp +5']      || 0
    }

    def "instruction jmp with negative value should jump backwards specified offset of instructions"() {
        given:
            def instructions = ['jmp +2', 'jmp +2', 'jmp -1', 'acc +5']

        when:
            def result = executeProgram(instructions)

        then:
            result.accumulator == 5
    }

    def "program should end when visiting already executed instruction"() {
        given:
            def infiniteLoop = ['acc +5', 'jmp -1']

        when:
            def result = executeProgram(infiniteLoop)

        then:
            result.accumulator == 5
    }

    def "program should detect infinite loop"() {
        when:
            def result = executeProgram(instructins)

        then:
            result.infiniteLoopDetected == shouldDetectInfiniteLoop

        where:
            instructins                    | shouldDetectInfiniteLoop
            ['jmp +0']                     | true
            ['acc +5', 'jmp -1']           | true
            ['acc +5', 'acc +1']           | false
            ['nop +5', 'jmp +1', 'acc +1'] | false
    }

    def "program should find where to change jmp to nop to avoid infinite loop"() {
        given:
            def infiniteLoop = ['acc +5', 'jmp +1', 'acc +4', 'jmp -1', 'acc +100']

        when:
            def result = executeSelfFixableProgram(infiniteLoop)

        then:
            result.accumulator == 109
            !result.infiniteLoopDetected
    }

    def "program should find where to change nop to jmp to avoid infinite loop"() {
        given:
            def infiniteLoop = ['acc +5', 'nop +4', 'acc +4', 'jmp -2', 'jmp -3', 'acc +100']

        when:
            def result = executeSelfFixableProgram(infiniteLoop)

        then:
            result.accumulator == 105
            !result.infiniteLoopDetected
    }

    private static Instruction parseInstruction(String instructionString) {
        def parser = new InstructionParser()

        parser.parse(instructionString)
    }

    private static ProgramResult executeProgram(List<String> instructionStrings) {
        def programRunner = new ProgramRunner(new InstructionParser())

        programRunner.run(instructionStrings)
    }

    private static ProgramResult executeSelfFixableProgram(List<String> instructionStrings) {
        def programRunner = new SelfFixableProgramRunner(new InstructionParser())

        programRunner.run(instructionStrings)
    }

}
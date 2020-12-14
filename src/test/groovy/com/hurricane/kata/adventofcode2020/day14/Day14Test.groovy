package com.hurricane.kata.adventofcode2020.day14

import com.hurricane.kata.adventofcode2020.Puzzle
import com.hurricane.kata.adventofcode2020.PuzzleIntTestSupport
import spock.lang.Unroll

import static com.hurricane.kata.adventofcode2020.day14.CommandAssertions.assertThat
import static com.hurricane.kata.adventofcode2020.day14.ProgramResultAssertions.assertThatResult

@Unroll
class Day14Test extends PuzzleIntTestSupport {

    private static final long MAX_VALUE = 2**36 - 1

    @Override
    protected Puzzle puzzlePart1() {
        new Puzzle(
                inputLocation: 'test_entries_day14.txt',
                solution: new Day14Part1Runner(),
                expectedAnswer: 165
        )
    }

    @Override
    protected Puzzle puzzlePart2() {
        new Puzzle(
                inputLocation: 'test_entries_day14.txt',
                solution: new Day14Part2Runner(),
                expectedAnswer: 208
        )
    }

    def "mask with only X should leave value unchanged"() {
        given:
            def mask = maskWithAll('X')

        when:
            def afterValue = modifyValueUsingMask(value, mask)

        then:
            afterValue == value

        where:
            value << [0, 5, 10_000, MAX_VALUE]
    }

    def "mask with only 0 should reset value to 0"() {
        given:
            def mask = maskWithAll('0')

        when:
            def afterValue = modifyValueUsingMask(value, mask)

        then:
            afterValue == 0

        where:
            value << [0, 5, 10_000, MAX_VALUE]
    }

    def "mask with only 1 should set up all value's bits"() {
        given:
            def mask = maskWithAll('1')

        when:
            def afterValue = modifyValueUsingMask(value, mask)

        then:
            afterValue == MAX_VALUE

        where:
            value << [0, 5, 10_000, MAX_VALUE]
    }

    def "bit-mask should set specified value 's bits"() {
        when:
            def valueAfter = modifyValueUsingMask(value, mask)

        then:
            valueAfter == expectedValueAfter

        where:
            value    | mask                  || expectedValueAfter
            0        | maskWith1At(1)        || 1
            0        | maskWith1At(36)       || 2**35
            0        | maskWith1At(10, 3, 1) || 517
            0b101000 | maskWith1At(5, 2, 1)  || 59
    }

    def "bit-mask should reset specified value's bits"() {
        when:
            def valueAfter = modifyValueUsingMask(value, mask)

        then:
            valueAfter == expectedValueAfter

        where:
            value  | mask                    || expectedValueAfter
            0b0001 | maskWith0At(1)          || 0
            2**35L | maskWith0At(36)         || 0
            0b1111 | maskWith0At(4, 3, 2, 1) || 0
            0b1111 | maskWith0At(3, 2)       || 9
    }

    def "should detect mask command"() {
        given:
            def commandsInput = [('mask = ' + maskValue)]

        when:
            def commands = parseCommands(commandsInput)

        then:
            commands.size() == 1
        and:
            assertThat(commands[0])
                    .isMaskCommand()
                    .hasMask(maskValue)

        where:
            maskValue << [
                    'XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX',
                    '000000000000000000000000000000000000',
                    '111111111111111111111111111111111111',
                    '111111111XX11XXXXXXXXX11111100000000',
            ]
    }

    def "should detect store command"() {
        given:
            def commandsInput = [('mem[' + memoryAddress + '] = ' + value)]

        when:
            def commands = parseCommands(commandsInput)

        then:
            commands.size() == 1
        and:
            assertThat(commands[0])
                    .isStoreCommand()
                    .hasMemoryAddress(memoryAddress)
                    .hasValue(value)

        where:
            memoryAddress | value
            0             | 0
            8             | 11
            5690          | 2130
    }

    def "should detect multiple commands"() {
        given:
            def commandsInput = [
                    'mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X',
                    'mem[8] = 11',
                    'mem[7] = 101',
                    'mask = 000000000000000000000000000001XXXX0X',
                    'mem[8] = 0']

        when:
            def commands = parseCommands(commandsInput)

        then:
            commands.size() == 5
        and:
            assertThat(commands[0]).isMaskCommand().hasMask('XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X')
            assertThat(commands[1]).isStoreCommand().hasMemoryAddress(8).hasValue(11)
            assertThat(commands[2]).isStoreCommand().hasMemoryAddress(7).hasValue(101)
            assertThat(commands[3]).isMaskCommand().hasMask('000000000000000000000000000001XXXX0X')
            assertThat(commands[4]).isStoreCommand().hasMemoryAddress(8).hasValue(0)
    }

    def "should store value in memory"() {
        when:
            def programResult = runProgram(commandsInput)

        then:
            assertThatResult(programResult)
                    .hasInMemory(expectedMemory)

        where:
            commandsInput                                 | expectedMemory
            ['mem[8] = 11']                               | [8: 11]
            ['mem[100] = 6000']                           | [100: 6000]
            ['mem[90000] = 1236000']                      | [90000: 1236000]
            ['mem[1] = 1', 'mem[2] = 12', 'mem[3] = 123'] | [1: 1, 2: 12, 3: 123]
    }

    def "should modify values in memory"() {
        when:
            def programResult = runProgram(commandsInput)

        then:
            assertThatResult(programResult)
                    .hasInMemory(expectedMemory)

        where:
            commandsInput                                  | expectedMemory
            ['mem[1] = 1', 'mem[1] = 10']                  | [1: 10]
            ['mem[1] = 100', 'mem[10] = 3', 'mem[10] = 9'] | [1: 100, 10: 9]
            ['mem[6] = 7', 'mem[3] = 9', 'mem[6] = 11']    | [6: 11, 3: 9]
    }

    def "should sum all values in memory"() {
        when:
            def programResult = runProgram(commandsInput)

        then:
            assertThatResult(programResult)
                    .hasSum(expectedSum)

        where:
            commandsInput                                      | expectedSum
            ['mem[1] = 1']                                     | 1
            ['mem[1] = 100', 'mem[5] = 10']                    | 110
            ['mem[10] = 7', 'mem[100] = 9', 'mem[10000] = 11'] | 27
    }

    def "should use mask to modify values before storing them in memory"() {
        given:
            def commandsInput = [
                    'mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX0',
                    'mem[1] = 11',
                    'mem[2] = 101',
                    'mask = 00000000000000000000000000000XXX1XXX',
                    'mem[3] = 7']

        when:
            def programResult = runProgram(commandsInput)

        then:
            assertThatResult(programResult)
                    .hasInMemory(1: 10, 2: 100, 3: 15)
                    .hasSum(125)
    }

    private static String maskWithAll(String bit) {
        return (1..36).collect { bit }.join('')
    }

    private static String maskWith1At(Integer... bitsToSet) {
        maskBits(bitsToSet.collectEntries { [(it): '1'] })
    }

    private static String maskWith0At(Integer... bitsToSet) {
        maskBits(bitsToSet.collectEntries { [(it): '0'] })
    }

    private static String maskBits(Map<Integer, String> bits) {
        def allX = (1..36).collect { 'X' }
        bits.forEach { pos, bit ->
            allX.set(35 - (pos - 1), bit)
        }

        return allX.join('')
    }


    private static long modifyValueUsingMask(long value, String mask) {
        def modifier = new ValueMaskModifier(mask)

        return modifier.modify(value)
    }

    private static List<Command> parseCommands(List<String> commandsInput) {
        def commandsParser = new CommandsParser()

        return commandsParser.parse(commandsInput)
    }

    private static ProgramResult runProgram(List<String> commandsInput) {
        def program = new Program(commandsInput)

        return program.run()
    }

}


class CommandAssertions {

    Command command

    static def assertThat(Command target) {
        new CommandAssertions(command: target)
    }

    CommandAssertions isMaskCommand() {
        assert command instanceof MaskCommand
        return this
    }

    CommandAssertions isStoreCommand() {
        assert command instanceof StoreCommand
        return this
    }

    CommandAssertions hasMask(String expectedMask) {
        assert (command as MaskCommand).value == expectedMask
        return this
    }

    CommandAssertions hasMemoryAddress(int expectedMemoryAddress) {
        assert (command as StoreCommand).memoryAddress == expectedMemoryAddress
        return this
    }

    CommandAssertions hasValue(int expectedValue) {
        assert (command as StoreCommand).value == expectedValue
        return this
    }
}

class ProgramResultAssertions {

    ProgramResult programResult


    static def assertThatResult(ProgramResult target) {
        new ProgramResultAssertions(programResult: target)
    }

    ProgramResultAssertions hasInMemory(Map<Integer, Integer> expectedMemory) {
        expectedMemory.forEach { address, expectedValue ->
            assert programResult.readMemoryAt(address) == expectedValue
        }
        return this
    }

    ProgramResultAssertions hasSum(int expectedSum) {
        assert programResult.memorySum == expectedSum
        return this
    }
}
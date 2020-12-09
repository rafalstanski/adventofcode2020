package com.hurricane.kata.adventofcode2020.day9

import com.hurricane.kata.adventofcode2020.Puzzle
import com.hurricane.kata.adventofcode2020.PuzzleIntTestSupport
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import spock.lang.Unroll

@Unroll
class Day9Test extends PuzzleIntTestSupport {

    private Window window

    @Override
    protected Puzzle puzzlePart1() {
        new Puzzle(
                inputLocation: 'test_entries_day9.txt',
                solution: new Day9Part1Runner(5),
                expectedAnswer: 127
        )
    }

    @Override
    protected Puzzle puzzlePart2() {
        new Puzzle(
                inputLocation: 'test_entries_day9.txt',
                solution: new Day9Part2Runner(5),
                expectedAnswer: 62
        )
    }

    def "should return current number in port output"() {
        given:
            def portOutput = definePortOutput {
                withNumbers(numbers)
                withPreambleSize(preambleSize)
            }

        expect:
            takeCurrentNumber(portOutput) == expectedNumber

        where:
            numbers               | preambleSize || expectedNumber
            [1, 2, 3]             | 2            || 3
            [1, 2, 3, 4, 5]       | 3            || 4
            [21, 3, 10, 12, 5, 7] | 5            || 7
    }

    def "should take next number from port output"() {
        given:
            def portOutput = definePortOutput {
                withNumbers([21, 3, 10, 12, 5, 7])
                withPreambleSize(3)
            }

        expect:
            takeCurrentNumber(portOutput) == 12
            takeNextNumber(portOutput) == 5
            takeNextNumber(portOutput) == 7
    }

    def "should determine if (initial) current number is valid"() {
        given:
            def portOutput = definePortOutput {
                withNumbers([21, 3, 10, 13, 5, 7])
                withPreambleSize(3)
            }

        expect:
            isCurrentNumberValid(portOutput)
        and:
            takeCurrentNumber(portOutput) == 13
    }

    def "should determine validity of all numbers from port output"() {
        given:
            def portOutput = definePortOutput {
                withNumbers([21, 3, 10, 13, 5, 7, 20])
                withPreambleSize(3)
            }

        expect:
            isCurrentNumberValid(portOutput)
            takeCurrentNumber(portOutput) == 13
        and:
            !isNextNumberValid(portOutput)
            takeCurrentNumber(portOutput) == 5
        and:
            !isNextNumberValid(portOutput)
            takeCurrentNumber(portOutput) == 7
        and:
            isNextNumberValid(portOutput)
            takeCurrentNumber(portOutput) == 20
    }


    def "should determine if number is valid within window"() {
        given:
            createWindowOf([1L, 2L, 3L])

        expect:
            isNumberValidWithinCreatedWindow(numerToValidate) == isValid

        where:
            numerToValidate || isValid
            3L              || true
            4L              || true
            5L              || true
            1L              || false
            2L              || false
            6L              || false
    }

    def "should be able to modify window by new number"() {
        given:
            createWindowOf([1L, 10L, 100L])

        expect:
            isNumberValidWithinCreatedWindow(101L)

        when:
            modifyWindowByNumber(1000L)

        then:
            !isNumberValidWithinCreatedWindow(101L)
        and:
            isNumberValidWithinCreatedWindow(1010L)
    }

    def "should be able to find invalid number"() {
        when:
            def invalidNumber = findInvalidNumberIn(numbers, windowSize)

        then:
            invalidNumber == expectedInvalidNumber

        where:
            windowSize | numbers                                  || expectedInvalidNumber
            2          | [1L, 2L, 3L, 5L, 7L, 12L]                || 7L
            2          | [1L, 2L, 3L, 5L, 7L, 12L]                || 7L
            4          | [1L, 2L, 3L, 4L, 5L, 7L, 13L]            || 13L
            5          | [5L, 4L, 1L, 2L, 3L, 8L, 11L, 100L, 10L] || 100L
    }

    def "should find encryption weakness numbers"() {
        given:
            def numbers = [1L, 2L, 3L, 5L, 7L, 12L]

        when:
            def encryptionWeakness = findEncryptionWeaknessNumbers(numbers, invalidNumber)

        then:
            encryptionWeakness == expectedEncryptionWeakness

        where:
            invalidNumber || expectedEncryptionWeakness
            12L           || [5l, 7L]
            3L            || [1L, 2L]
            15L           || [3L, 5L, 7L]
            30L           || [1L, 2L, 3L, 5L, 7L, 12L]
    }

    def "should find encryption weakness"() {
        given:
            def numbers = [5L, 1L, 2L, 12L, 3L, 9L, 4L]

        when:
            def encryptionWeakness = findAndCalculateEncryptionWeakness(numbers, invalidNumber)

        then:
            encryptionWeakness == expectedEncryptionWeakness

        where:
            invalidNumber || expectedEncryptionWeakness
            6L            || 6L
            8L            || 6L
            16L           || 12L
    }

    private void createWindowOf(List<Long> numbers) {
        window = new Window(numbers)
    }

    private boolean isNumberValidWithinCreatedWindow(long numberToValidate) {
        window.isNumberValid(numberToValidate)
    }

    private void modifyWindowByNumber(long newNumber) {
        window = window.removeFirstAndAdd(newNumber)
    }

    private static Long findInvalidNumberIn(List<Long> numbers, int windowSize) {
        def finder = new InvalidNumberFinder(windowSize)

        finder.find(numbers)
    }

    private static List<Long> findEncryptionWeaknessNumbers(List<Long> numbers, long invalidNumber) {
        def finder = new EncryptionWeaknessSequenceFinder(numbers)

        finder.find(invalidNumber)
    }

    private static Long findAndCalculateEncryptionWeakness(List<Long> numbers, long invalidNumber) {
        def calculator = new EncryptionWeaknessCalculator(numbers)

        calculator.findAndCalculate(invalidNumber)
    }

    private static PortOutput definePortOutput(@DelegatesTo(PortOutputBuilder) Closure builderDef) {
        def builder = new PortOutputBuilder()
        builder.with(builderDef)

        return builder.build()
    }

    private static Long takeCurrentNumber(PortOutput portOutput) {
        portOutput.currentNumber
    }

    private static Long takeNextNumber(PortOutput portOutput) {
        portOutput.shift()

        return portOutput.currentNumber
    }

    private static boolean isCurrentNumberValid(PortOutput portOutput) {
        portOutput.isCurrentValid()
    }

    private static boolean isNextNumberValid(PortOutput portOutput) {
        portOutput.shift()

        return portOutput.isCurrentValid()
    }
}

@Builder(builderStrategy = SimpleStrategy, prefix = 'with')
class PortOutputBuilder {
    List<Integer> numbers
    Integer preambleSize

    PortOutput build() {
        new PortOutput(numbers.collect { it.toLong() }, preambleSize)
    }
}
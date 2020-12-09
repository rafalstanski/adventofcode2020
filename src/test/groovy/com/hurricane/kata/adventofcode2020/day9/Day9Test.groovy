package com.hurricane.kata.adventofcode2020.day9

import com.hurricane.kata.adventofcode2020.Puzzle
import com.hurricane.kata.adventofcode2020.PuzzleIntTestSupport
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

    private Long findInvalidNumberIn(List<Long> numbers, int windowSize) {
        def finder = new InvalidNumberFinder(windowSize)

        finder.find(numbers)
    }

    private List<Long> findEncryptionWeaknessNumbers(List<Long> numbers, long invalidNumber) {
        def finder = new EncryptionWeaknessFinder(numbers)

        finder.find(invalidNumber)
    }

    private Long findAndCalculateEncryptionWeakness(List<Long> numbers, long invalidNumber) {
        def calculator = new EncryptionWeaknessCalculator(numbers)

        calculator.calculate(invalidNumber)
    }
}
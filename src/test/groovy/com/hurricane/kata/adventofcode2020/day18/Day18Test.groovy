package com.hurricane.kata.adventofcode2020.day18

import com.hurricane.kata.adventofcode2020.Puzzle
import com.hurricane.kata.adventofcode2020.PuzzleIntTestSupport
import spock.lang.Unroll

@Unroll
class Day18Test extends PuzzleIntTestSupport {

    @Override
    protected Puzzle puzzlePart1() {
        new Puzzle(
                inputLocation: 'test_entries_day18.txt',
                solution: new Day18Part1Runner(),
                expectedAnswer: 13632
        )
    }

    @Override
    protected Puzzle puzzlePart2() {
        new Puzzle(
                inputLocation: 'test_entries_day18.txt',
                solution: new Day18Part2Runner(),
                expectedAnswer: 208
        )
    }

    def "should be able to add two numbers"() {
        expect:
            calculate(equation) == expectedResult

        where:
            equation    || expectedResult
            '1 + 2'     || 3
            '10 + 20'   || 30
            '566 + 114' || 680
    }

    def "should be able to multiply two numbers"() {
        expect:
            calculate(equation) == expectedResult

        where:
            equation    || expectedResult
            '1 * 2'     || 2
            '10 * 20'   || 200
            '566 * 114' || 64524
    }

    def "should be able to mix operations add and multiply with the same priority"() {
        expect:
            calculate(equation) == expectedResult

        where:
            equation         || expectedResult
            '2 * 3 + 4'      || 10
            '2 + 3 * 4'      || 20
            '2 * 10 + 3'     || 23
            '2 * 10 + 3 * 5' || 115
    }

    def "should be able to detect parentheses"() {
        expect:
            calculate(equation) == expectedResult

        where:
            equation            || expectedResult
            '(2 + 3)'           || 5
            '(2 * 3)'           || 6
            '(2 * 3) + (5 * 2)' || 16
    }

    private static long calculate(String equation) {
        def calculator = new EquationCalculator()

        return calculator.calculate(equation)
    }
}
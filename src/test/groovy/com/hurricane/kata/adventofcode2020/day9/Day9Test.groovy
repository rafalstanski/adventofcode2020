package com.hurricane.kata.adventofcode2020.day9

import com.hurricane.kata.adventofcode2020.Puzzle
import com.hurricane.kata.adventofcode2020.PuzzleIntTestSupport
import spock.lang.Unroll

@Unroll
class Day9Test extends PuzzleIntTestSupport {

    @Override
    protected Puzzle puzzlePart1() {
        new Puzzle(
                inputLocation: 'test_entries_day9.txt',
                solution: new Day9Part1Runner(),
                expectedAnswer: 5
        )
    }

    @Override
    protected Puzzle puzzlePart2() {
        new Puzzle(
                inputLocation: 'test_entries_day9.txt',
                solution: new Day9Part2Runner(),
                expectedAnswer: 8
        )
    }

    def "should"() {
        expect:
            false
    }
}
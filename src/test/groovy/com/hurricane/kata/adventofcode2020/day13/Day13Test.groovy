package com.hurricane.kata.adventofcode2020.day13

import com.hurricane.kata.adventofcode2020.Puzzle
import com.hurricane.kata.adventofcode2020.PuzzleIntTestSupport
import spock.lang.Unroll

@Unroll
class Day13Test extends PuzzleIntTestSupport {

    @Override
    protected Puzzle puzzlePart1() {
        new Puzzle(
                inputLocation: 'test_entries_day13.txt',
                solution: new Day13Part1Runner(),
                expectedAnswer: 295
        )
    }

    @Override
    protected Puzzle puzzlePart2() {
        new Puzzle(
                inputLocation: 'test_entries_day13.txt',
                solution: new Day13Part2Runner(),
                expectedAnswer: 26
        )
    }

    def "should"() {
        expect:
            false
    }

}

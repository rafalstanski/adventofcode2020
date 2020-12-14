package com.hurricane.kata.adventofcode2020.day10

import com.hurricane.kata.adventofcode2020.Puzzle
import com.hurricane.kata.adventofcode2020.PuzzleIntTestSupport
import spock.lang.Unroll

@Unroll
class Day10Test extends PuzzleIntTestSupport {

    @Override
    protected Puzzle puzzlePart1() {
        new Puzzle(
                inputLocation: 'test_entries_day10.txt',
                solution: new Day10Part1Runner(),
                expectedAnswer: 220
        )
    }

    @Override
    protected Puzzle puzzlePart2() {
        new Puzzle(
                inputLocation: 'test_entries_day10.txt',
                solution: new Day10Part2Runner(),
                expectedAnswer: 19208
        )
    }

}
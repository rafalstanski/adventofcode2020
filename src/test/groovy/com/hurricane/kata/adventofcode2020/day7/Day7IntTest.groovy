package com.hurricane.kata.adventofcode2020.day7


import com.hurricane.kata.adventofcode2020.Puzzle
import com.hurricane.kata.adventofcode2020.PuzzleIntTestSupport

class Day7IntTest extends PuzzleIntTestSupport {

    @Override
    protected Puzzle puzzlePart1() {
        new Puzzle(
                inputLocation: 'test_entries_day7part1.txt',
                solution: new Day7Part1Runner(),
                expectedAnswer: 4
        )
    }

    @Override
    protected Puzzle puzzlePart2() {
        new Puzzle(
                inputLocation: 'test_entries_day7part2.txt',
                solution: new Day7Part2Runner(),
                expectedAnswer: 126
        )
    }
}


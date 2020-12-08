package com.hurricane.kata.adventofcode2020


import com.hurricane.kata.adventofcode2020.shared.EntriesLoader
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution
import spock.lang.Specification

abstract class PuzzleIntTestSupport extends Specification {

    def "should solve example for part1"() {
        given:
            def input = loadPuzzleInput(puzzlePart1().inputLocation)

        expect:
            solvePart1(input) == puzzlePart1().expectedAnswer
    }

    def "should solve example for part2"() {
        given:
            def input = loadPuzzleInput(puzzlePart2().inputLocation)

        expect:
            solvePart2(input) == puzzlePart2().expectedAnswer
    }

    private static PuzzleInput loadPuzzleInput(String inputLocation) {
        new PuzzleInput(EntriesLoader.INSTANCE.load(inputLocation))
    }

    private Object solvePart1(PuzzleInput puzzleInput) {
        puzzlePart1().solution
                .solve(puzzleInput)
                .solution
    }

    private Object solvePart2(PuzzleInput puzzleInput) {
        puzzlePart2().solution
                .solve(puzzleInput)
                .solution
    }

    protected abstract Puzzle puzzlePart1()

    protected abstract Puzzle puzzlePart2()

}

class Puzzle {
    String inputLocation
    PuzzleSolution solution
    Object expectedAnswer
}


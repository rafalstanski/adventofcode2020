package com.hurricane.kata.adventofcode2020.day9

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day9Part1Runner(windowSize: Int = 25) : PuzzleSolution {

    private val invalidNumberFinder = InvalidNumberFinder(windowSize)

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val invalidNumber = invalidNumberFinder.find(puzzleInput.toLongNumbers())

        return PuzzleAnswer(invalidNumber)
    }

}
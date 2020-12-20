package com.hurricane.kata.adventofcode2020.day18

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day18Part2Runner : PuzzleSolution {
    private val calculator = EquationCalculator(DifferentPrioritiesCalculator())

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val sum = puzzleInput.entries
                .map { calculator.calculate(it) }
                .sum()

        return PuzzleAnswer(sum)
    }

}
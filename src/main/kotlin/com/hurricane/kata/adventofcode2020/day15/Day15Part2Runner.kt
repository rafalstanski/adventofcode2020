package com.hurricane.kata.adventofcode2020.day15

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day15Part2Runner : PuzzleSolution {

    private val solver = Solver(30000000)

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val input = puzzleInput.entries[0]

        return PuzzleAnswer(solver.solve(input))
    }

}
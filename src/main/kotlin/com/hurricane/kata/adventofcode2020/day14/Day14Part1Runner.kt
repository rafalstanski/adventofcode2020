package com.hurricane.kata.adventofcode2020.day14

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day14Part1Runner : PuzzleSolution {

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val program = Program(puzzleInput.entries)
        val programResult = program.run()

        return PuzzleAnswer(programResult.memorySum)
    }

}
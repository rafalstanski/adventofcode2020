package com.hurricane.kata.adventofcode2020.day8

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day8Part2Runner : PuzzleSolution {

    private val programRunner = SelfFixableProgramRunner(InstructionParser())

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val programResult = programRunner.run(puzzleInput.entries)

        return PuzzleAnswer(programResult.accumulator)
    }

}
package com.hurricane.kata.adventofcode2020.day8

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day8Part1Runner : PuzzleSolution {

    private val programRunner = ProgramRunner(InstructionParser())

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val programResult = programRunner.run(puzzleInput.entries)

        return PuzzleAnswer(programResult.accumulator)
    }

}
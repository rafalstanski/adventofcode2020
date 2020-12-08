package com.hurricane.kata.adventofcode2020.day2

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution


class Day2Part1Runner : PuzzleSolution {

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val counter = ValidPasswordCounter(PasswordDefinitionParser(), PasswordRangeValidator())

        return PuzzleAnswer(counter.count(puzzleInput.entries))
    }

}
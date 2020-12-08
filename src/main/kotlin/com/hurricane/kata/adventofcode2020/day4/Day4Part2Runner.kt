package com.hurricane.kata.adventofcode2020.day4

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution


class Day4Part2Runner : PuzzleSolution {

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val validPassportCounter = ValidPassportCounter(
                PassportsParser(),
                RequiredAndValidFieldsPassportValidator(
                        RequiredFieldsPassportValidator(),
                        PassportFieldValidator()))

        return PuzzleAnswer(validPassportCounter.count(puzzleInput.entries))
    }

}
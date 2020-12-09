package com.hurricane.kata.adventofcode2020.day9

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day9Part2Runner(windowSize: Int = 25) : PuzzleSolution {

    private val finder = InvalidNumberFinder(windowSize)

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val numbers = toNumbers(puzzleInput)

        val invalidNumber = finder.find(numbers)

        val encryptionWeakness = if (invalidNumber != null) {
            val calculator = EncryptionWeaknessCalculator(numbers)
            calculator.calculate(invalidNumber)
        } else {
            null
        }

        return PuzzleAnswer(encryptionWeakness)
    }

    private fun toNumbers(puzzleInput: PuzzleInput): List<Long> =
            puzzleInput.entries.map { it.toLong() }

}
package com.hurricane.kata.adventofcode2020.day9

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day9Part2Runner(windowSize: Int = 25) : PuzzleSolution {

    private val invalidNumberFinder = InvalidNumberFinder(windowSize)

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val numbers = puzzleInput.toLongNumbers()

        val encryptionWeakness = invalidNumberFinder.find(numbers)
                ?.let { findAndCalculateEncryptionWeakness(numbers, it) }

        return PuzzleAnswer(encryptionWeakness)
    }

    private fun findAndCalculateEncryptionWeakness(numbers: List<Long>, it: Long): Long? {
        val calculator = EncryptionWeaknessCalculator(numbers)
        return calculator.findAndCalculate(it)
    }

}
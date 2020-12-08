package com.hurricane.kata.adventofcode2020.day1

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution


class Day1Part1Runner : PuzzleSolution {

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val entries = puzzleInput.entries.map { it.toInt() }

        val solution = TwoNumberSolutionFinder().find(entries)

        return PuzzleAnswer(solution?.result)
    }

}
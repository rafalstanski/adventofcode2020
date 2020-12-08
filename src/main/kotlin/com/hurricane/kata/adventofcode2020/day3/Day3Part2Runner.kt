package com.hurricane.kata.adventofcode2020.day3

import com.hurricane.kata.adventofcode2020.day3.PointType.TREE
import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution


class Day3Part2Runner : PuzzleSolution {

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val board = Board(puzzleInput.entries)
        val traverser = Traverser(board)
        val counter = PointsCounter(TREE, traverser)

        val multiple = listOf(
                1 to 1,
                1 to 3,
                1 to 5,
                1 to 7,
                2 to 1)
                .map { counter.countTraveling(it.first, it.second) }
                .reduce { acc, count -> acc * count }

        return PuzzleAnswer(multiple)
    }

}
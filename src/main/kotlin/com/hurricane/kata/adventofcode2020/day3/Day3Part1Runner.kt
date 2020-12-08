package com.hurricane.kata.adventofcode2020.day3

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution


class Day3Part1Runner : PuzzleSolution {

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val board = Board(puzzleInput.entries)
        val traverser = Traverser(board)
        val counter = PointsCounter(PointType.TREE, traverser)

        return PuzzleAnswer(counter.countTraveling(1, 3))
    }

}
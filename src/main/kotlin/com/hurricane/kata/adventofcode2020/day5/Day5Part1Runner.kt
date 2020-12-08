package com.hurricane.kata.adventofcode2020.day5

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day5Part1Runner : PuzzleSolution {

    private val seatNumberDecoder = SeatNumberDecoder()

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val maxId = puzzleInput.entries
                .map { seatNumberDecoder.decode(it) }
                .map { it.id }
                .max()

        return PuzzleAnswer(maxId)
    }

}

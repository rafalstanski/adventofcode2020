package com.hurricane.kata.adventofcode2020.day11

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day11Part2Runner : PuzzleSolution {

    private val seatGridParser = SeatGridParser()

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        var seatGrid = seatGridParser.parse(puzzleInput.entries)
        var occupiedCount: Int

        do {
            occupiedCount = seatGrid.countOccupied()
            seatGrid = seatGrid.changeSits()
        } while (occupiedCount != seatGrid.countOccupied())

        return PuzzleAnswer(occupiedCount)
    }

}
package com.hurricane.kata.adventofcode2020.day11

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day11Part1Runner : PuzzleSolution {

    private val seatGridParser = SeatGridParser()
    private val seatGridChanger = SeatGridChanger(Part1SeatChanger(), AdjacentOccupiedNeighboursCounterFactory())

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        var seatGrid = seatGridParser.parse(puzzleInput.entries)

        var occupiedCount: Int
        do {
            occupiedCount = seatGrid.countOccupied()
            seatGrid = seatGridChanger.change(seatGrid)
        } while (occupiedCount != seatGrid.countOccupied())

        return PuzzleAnswer(occupiedCount)
    }

}
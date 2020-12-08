package com.hurricane.kata.adventofcode2020.day5

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day5Part2Runner : PuzzleSolution {

    private val seatNumberDecoder = SeatNumberDecoder()

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val allSeats = generateAllSeats()

        val readSeats = puzzleInput.entries
                .map { seatNumberDecoder.decode(it) }

        val missingSeats = allSeats - readSeats

        val foundMySeat = findMySeatCandidates(missingSeats)

        return PuzzleAnswer(foundMySeat.map { it.id })
    }

    private fun generateAllSeats(): List<SeatNumber> {
        return (0..127).map { row ->
            (0..7).map { col -> row to col }
        }.flatten()
                .map { SeatNumber(it.first, it.second) }
    }

    private fun findMySeatCandidates(missingSeats: List<SeatNumber>): List<SeatNumber> {
        val missingSeatsIds = missingSeats.map { it.id }

        return missingSeats
                .filter { !missingSeatsIds.contains(it.id - 1) && !missingSeatsIds.contains(it.id + 1) }
    }

}

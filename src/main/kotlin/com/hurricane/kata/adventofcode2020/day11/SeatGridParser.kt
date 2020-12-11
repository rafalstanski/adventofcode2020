package com.hurricane.kata.adventofcode2020.day11

class SeatGridParser {

    fun parse(rowLayouts: List<String>): SeatGrid {
        val rows = rowLayouts.map { parseRow(it) }

        return SeatGrid(rows)
    }

    private fun parseRow(rowLayout: String) =
            rowLayout.map { parseSeat(it) }

    private fun parseSeat(seatSymbol: Char): Seat =
            when (seatSymbol) {
                '#' -> Seat.OCCUPIED
                '.' -> Seat.FLOOR
                'L' -> Seat.EMPTY
                else -> throw IllegalArgumentException("Unknown sign: $seatSymbol")
            }

}
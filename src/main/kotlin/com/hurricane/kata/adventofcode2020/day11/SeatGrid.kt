package com.hurricane.kata.adventofcode2020.day11

import com.hurricane.kata.adventofcode2020.day11.Seat.OCCUPIED

class SeatGrid(private val rows: List<List<Seat>>) {

    fun seatAt(rowNum: Int, colNum: Int): Seat? {
        if (rows.indices.contains(rowNum)) {
            val row = rows[rowNum]
            if (row.indices.contains(colNum)) {
                return row[colNum]
            }
        }

        return null
    }

    fun countOccupied(): Int =
            rows.flatten().count { it == OCCUPIED }

    fun copyAndTransform(transformation: (Int, Int, Seat) -> Seat): SeatGrid {
        val changedSeats = rows.mapIndexed { rowNum, rowSeats ->
            rowSeats.mapIndexed { colNum, seat ->
                transformation(rowNum, colNum, seat)
            }
        }

        return SeatGrid(changedSeats)
    }
}
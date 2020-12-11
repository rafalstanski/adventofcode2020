package com.hurricane.kata.adventofcode2020.day11

import com.hurricane.kata.adventofcode2020.day11.Seat.OCCUPIED

class SeatGrid(private val rows: List<List<Seat>>) {

    private val seatChanger = SeatChanger()

    fun seatAt(row: Int, col: Int): Seat? {
        if (rows.indices.contains(row)) {
            val row = rows[row]
            if (row.indices.contains(col)) {
                return row[col]
            }
        }

        return null
    }

    fun changeSits(): SeatGrid {
        val changedSeats = rows.mapIndexed { rowNum, rowSeats ->
            rowSeats.mapIndexed { colNum, seat ->
                val occupiedNeighboursCount = countOccupiedNeighbours(rowNum, colNum)
                seatChanger.change(seat, occupiedNeighboursCount)
            }
        }

        return SeatGrid(changedSeats)
    }

    private fun countOccupiedNeighbours(rowNum: Int, colNum: Int): Int =
            neighboursSeats(rowNum, colNum)
                    .count { it == OCCUPIED }

    private fun neighboursSeats(rowNum: Int, colNum: Int): List<Seat> {
        return (rowNum - 1..rowNum + 1).map { row ->
            (colNum - 1..colNum + 1).mapNotNull { col ->
                if (row != rowNum || col != colNum) seatAt(row, col) else null
            }
        }.flatten()
    }

    fun countOccupied(): Int =
            rows.flatten().count { it == OCCUPIED }
}
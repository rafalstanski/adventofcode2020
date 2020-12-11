package com.hurricane.kata.adventofcode2020.day11

class OccupiedNeighboursCounter(private val seatGrid: SeatGrid) {

    fun count(row: Int, col: Int): Int {
        return countOccupiedAdjacentNeighbours(row, col)
    }

    private fun countOccupiedAdjacentNeighbours(rowNum: Int, colNum: Int): Int =
            adjacentNeighbours(rowNum, colNum)
                    .count { it == Seat.OCCUPIED }

    private fun adjacentNeighbours(rowNum: Int, colNum: Int): List<Seat> {
        return (rowNum - 1..rowNum + 1).map { row ->
            (colNum - 1..colNum + 1).mapNotNull { col ->
                if (row != rowNum || col != colNum) seatGrid.seatAt(row, col) else null
            }
        }.flatten()
    }

}
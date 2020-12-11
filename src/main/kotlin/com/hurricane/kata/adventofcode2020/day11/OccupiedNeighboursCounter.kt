package com.hurricane.kata.adventofcode2020.day11

import com.hurricane.kata.adventofcode2020.day11.Seat.FLOOR

interface OccupiedNeighboursCounter {
    fun count(row: Int, col: Int): Int
}

class AdjacentOccupiedNeighboursCounter(private val seatGrid: SeatGrid) : OccupiedNeighboursCounter {

    override fun count(row: Int, col: Int): Int {
        return countAdjacentOccupiedNeighbours(row, col)
    }

    private fun countAdjacentOccupiedNeighbours(rowNum: Int, colNum: Int): Int =
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

class VisibleOccupiedNeighboursCounter(private val seatGrid: SeatGrid) : OccupiedNeighboursCounter {

    override fun count(row: Int, col: Int): Int {
        return countVisibleOccupiedNeighbours(row, col)
    }

    private fun countVisibleOccupiedNeighbours(rowNum: Int, colNum: Int): Int =
            visibleNeighbours(Coordinates(rowNum, colNum))
                    .count { it == Seat.OCCUPIED }

    private fun visibleNeighbours(currentPosition: Coordinates): List<Seat> {
        return (-1..1).map { row ->
            (-1..1).mapNotNull { col ->
                if (row != 0 || col != 0) Coordinates(row, col) else null
            }.mapNotNull {direction ->
                seekSeatInDirection(
                        currentPosition = currentPosition,
                        direction = direction)
            }
        }.flatten()
    }

    private fun seekSeatInDirection(currentPosition: Coordinates, direction: Coordinates): Seat? {
        val seekPosition = currentPosition + direction
        val seat = seatGrid.seatAt(seekPosition.row, seekPosition.col)
        return when {
            seat == null -> null
            seat != FLOOR -> seat
            else -> seekSeatInDirection(seekPosition, direction)
        }
    }

}

private data class Coordinates(
        val row: Int,
        val col: Int
) {
    operator fun plus(coordinates: Coordinates) = Coordinates(row + coordinates.row, col + coordinates.col)
}
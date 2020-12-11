package com.hurricane.kata.adventofcode2020.day11

class SeatGridChanger(private val seatChanger: SeatChanger) {

    fun change(seatGrid: SeatGrid): SeatGrid {
        val neighboursCounter = OccupiedNeighboursCounter(seatGrid)

        return seatGrid.copyAndTransform { row, col, seat ->
            val occupiedNeighboursCount = neighboursCounter.count(row, col)

            seatChanger.change(seat, occupiedNeighboursCount)
        }
    }

}
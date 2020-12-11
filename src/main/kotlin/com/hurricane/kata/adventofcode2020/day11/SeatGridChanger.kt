package com.hurricane.kata.adventofcode2020.day11

class SeatGridChanger(
        private val seatChanger: SeatChanger,
        private val counterFactory: OccupiedNeighboursCounterFactory) {

    fun change(seatGrid: SeatGrid): SeatGrid {
        val neighboursCounter = counterFactory.create(seatGrid)

        return seatGrid.copyAndTransform(changeSeatUsing(neighboursCounter))
    }

    private fun changeSeatUsing(neighboursCounter: OccupiedNeighboursCounter): (Int, Int, Seat) -> Seat =
            { row, col, seat -> seatChanger.change(seat, neighboursCounter.count(row, col)) }

}
package com.hurricane.kata.adventofcode2020.day11

import com.hurricane.kata.adventofcode2020.day11.Seat.*

class SeatChanger {

    fun change(seatToChange: Seat, occupiedNeighbours: Int): Seat =
            when (seatToChange) {
                EMPTY -> changeWhenEmpty(occupiedNeighbours)
                OCCUPIED -> changeWhenOccupied(occupiedNeighbours)
                FLOOR -> changeWhenFloor(occupiedNeighbours)
            }

    private fun changeWhenOccupied(occupiedNeighbours: Int): Seat =
            if (occupiedNeighbours >= 4) {
                EMPTY
            } else {
                OCCUPIED
            }

    private fun changeWhenEmpty(occupiedNeighbours: Int): Seat =
            if (occupiedNeighbours == 0) {
                OCCUPIED
            } else {
                EMPTY
            }

    private fun changeWhenFloor(occupiedNeighbours: Int): Seat =
            FLOOR

}
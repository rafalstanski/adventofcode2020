package com.hurricane.kata.adventofcode2020.day11

import com.hurricane.kata.adventofcode2020.day11.Seat.*

interface SeatChanger {

    fun change(seatToChange: Seat, occupiedNeighbours: Int): Seat
}

class Part1SeatChanger : SeatChanger {

    override fun change(seatToChange: Seat, occupiedNeighbours: Int): Seat =
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


class Part2SeatChanger : SeatChanger {

    override fun change(seatToChange: Seat, occupiedNeighbours: Int): Seat =
            when (seatToChange) {
                EMPTY -> changeWhenEmpty(occupiedNeighbours)
                OCCUPIED -> changeWhenOccupied(occupiedNeighbours)
                FLOOR -> changeWhenFloor(occupiedNeighbours)
            }

    private fun changeWhenOccupied(occupiedNeighbours: Int): Seat =
            if (occupiedNeighbours >= 5) {
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
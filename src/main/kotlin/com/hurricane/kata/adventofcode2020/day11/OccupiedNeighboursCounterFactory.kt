package com.hurricane.kata.adventofcode2020.day11

interface OccupiedNeighboursCounterFactory {

    fun create(seatGrid: SeatGrid): OccupiedNeighboursCounter
}

class AdjacentOccupiedNeighboursCounterFactory : OccupiedNeighboursCounterFactory {

    override fun create(seatGrid: SeatGrid) = AdjacentOccupiedNeighboursCounter(seatGrid)

}

class VisibleOccupiedNeighboursCounterFactory : OccupiedNeighboursCounterFactory {

    override fun create(seatGrid: SeatGrid) = VisibleOccupiedNeighboursCounter(seatGrid)

}
package com.hurricane.kata.adventofcode2020.day13

import kotlin.math.ceil

class DepartTimeCalculator(private val bus: Int) {

    fun calculate(arrivalTime: Int): DepartTime {
        val cycles = calculateBusCyclesFor(arrivalTime)
        val earliestDepartTime = bus * cycles

        return DepartTime(
                bus = bus,
                value = earliestDepartTime,
                waitTime = earliestDepartTime - arrivalTime)
    }

    private fun calculateBusCyclesFor(arrivalTime: Int) =
            ceil(arrivalTime.toDouble() / bus).toInt()
}
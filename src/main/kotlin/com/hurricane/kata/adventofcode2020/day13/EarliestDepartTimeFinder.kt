package com.hurricane.kata.adventofcode2020.day13

class EarliestDepartTimeFinder(private val buses: List<Int>) {

    fun find(arrivalTime: Int): DepartTime =
            buses
                    .map { calculateEarliestDepartTime(it, arrivalTime) }
                    .minBy { it.waitTime }!!

    private fun calculateEarliestDepartTime(bus: Int, arrivalTime: Int) =
            DepartTimeCalculator(bus).calculate(arrivalTime)
}
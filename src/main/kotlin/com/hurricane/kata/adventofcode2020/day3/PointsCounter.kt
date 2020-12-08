package com.hurricane.kata.adventofcode2020.day3

class PointsCounter(
        private val pointType: PointType,
        private val traverser: Traverser) {

    fun countTraveling(down: Int, right: Int): Int = traverser.travel(down, right).count { it == pointType }

}
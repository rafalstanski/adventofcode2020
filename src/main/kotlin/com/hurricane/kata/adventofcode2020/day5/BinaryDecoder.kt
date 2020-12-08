package com.hurricane.kata.adventofcode2020.day5

import com.hurricane.kata.adventofcode2020.shared.logger
import kotlin.math.ceil
import kotlin.math.floor

open class BinaryDecoder(
        private val maxPositions: Int,
        private val lowerDirectionCode: Char,
        private val upperDirectionCode: Char) {

    fun decode(directions: String): Int {
        var start = 0.0
        var end = maxPositions.toDouble()

        directions.map { direction ->
            if (isLowerDirection(direction)) { //lower
                end = start + floor(calculateMiddle(start, end))
            } else if (isUpperDirection(direction)) { //upper
                start = start + ceil(calculateMiddle(start, end))
            }

            logger.info("after {} start = {}, end = {}", direction, start, end)
        }

        require(start.toInt() == end.toInt()) { "start and end should be equal" }

        return start.toInt()
    }

    private fun calculateMiddle(start: Double, end: Double) =
            (end - start) / 2.0

    private fun isUpperDirection(direction: Char) =
            direction == upperDirectionCode

    private fun isLowerDirection(direction: Char) =
            direction == lowerDirectionCode

    companion object {
        private val logger by logger()
    }
}
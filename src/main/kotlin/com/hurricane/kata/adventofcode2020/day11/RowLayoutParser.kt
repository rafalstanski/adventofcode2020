package com.hurricane.kata.adventofcode2020.day11

import com.hurricane.kata.adventofcode2020.day11.Seat.*

class RowLayoutParser {

    fun parse(rowLayout: String): List<Seat> =
            rowLayout.map { translateSign(it) }

    private fun translateSign(sign: Char): Seat {
        return when (sign) {
            '#' -> OCCUPIED
            '.' -> FLOOR
            'L' -> EMPTY
            else -> throw IllegalArgumentException("Unknown sign: $sign")
        }
    }

}
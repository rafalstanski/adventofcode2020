package com.hurricane.kata.adventofcode2020.day20

class BorderHashCalculator {

    fun calculate(border: String): Int {
        val asBitsString = border
                .replace('.', '0')
                .replace('#', '1')

        return asBitsString.toInt(2)
    }
}
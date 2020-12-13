package com.hurricane.kata.adventofcode2020.day13

class MagicNumberFinder {

    fun find(buses: List<Bus>): Long {
        var magicNumber = 0L

        while (!foundMagicNumber(magicNumber, buses)) {
            magicNumber += buses[0].busId
        }

        return magicNumber
    }

    private fun foundMagicNumber(magicNumber: Long, buses: List<Bus>): Boolean =
            buses.all { (magicNumber + it.offset.toLong() ) % it.busId == 0L }
}
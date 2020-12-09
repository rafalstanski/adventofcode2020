package com.hurricane.kata.adventofcode2020.day9

class InvalidNumberFinder(private val windowSize: Int) {

    fun find(numbers: List<Long>): Long? {
        val portOutput = PortOutput(numbers, windowSize)

        while(portOutput.isCurrentValid()) {
            portOutput.shift()
        }

        return portOutput.currentNumber
    }

}
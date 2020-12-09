package com.hurricane.kata.adventofcode2020.day9

class PortOutput(private val numbers: List<Long>, private val preambleSize: Int) {
    private var currentNumberIndex: Int = preambleSize
    private var window = Window(numbers.take(preambleSize))

    val currentNumber: Long
        get() = numbers[currentNumberIndex]

    fun shift() {
        window = window.removeFirstAndAdd(currentNumber)
        currentNumberIndex++
    }

    fun isCurrentValid(): Boolean {
        return window.isNumberValid(currentNumber)
    }

}
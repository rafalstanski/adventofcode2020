package com.hurricane.kata.adventofcode2020.day9

class InvalidNumberFinder(private val windowSize: Int) {

    fun find(numbers: List<Long>): Long? {
        var window = createPreambleWindow(numbers)

        return numbersWithoutPreamble(numbers)
                .find { invalidNumberCandidate ->
                    val isValid = window.isNumberValid(invalidNumberCandidate)
                    if (isValid) {
                        window = window.removeFirstAndAdd(invalidNumberCandidate)
                    }

                    !isValid
                }
    }

    private fun createPreambleWindow(numbers: List<Long>) =
            Window(numbers.take(windowSize))

    private fun numbersWithoutPreamble(numbers: List<Long>) = numbers
            .takeLast(numbers.size - windowSize)
}
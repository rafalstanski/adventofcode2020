package com.hurricane.kata.adventofcode2020.day9

class InvalidNumberFinder(private val windowSize: Int) {

    fun find(numbers: List<Long>): Long? {
        var window = createPreambleWindow(numbers)

        for (invalidNumberCandidate in numbersWithoutPreamble(numbers)) {
            if (window.isNumberValid(invalidNumberCandidate)) {
                window = window.removeFirstAndAdd(invalidNumberCandidate)
            } else {
                return invalidNumberCandidate
            }
        }

        return numbersWithoutPreamble(numbers)
                .find { numberToCheck ->
                    val isValid = window.isNumberValid(numberToCheck)
                    if (isValid) {
                        window = window.removeFirstAndAdd(numberToCheck)
                    }

                    !isValid
                }
    }

    private fun createPreambleWindow(numbers: List<Long>) =
            Window(numbers.take(windowSize))

    private fun numbersWithoutPreamble(numbers: List<Long>) = numbers
            .takeLast(numbers.size - windowSize)
}
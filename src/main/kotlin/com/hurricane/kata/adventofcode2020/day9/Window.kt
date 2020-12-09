package com.hurricane.kata.adventofcode2020.day9

class Window(private val numbers: List<Long>) {

    private val validNumbers: Set<Long> = calculateValidNumbers()

    private fun calculateValidNumbers(): Set<Long> =
            generateCombinationOfPairs()
                    .map { it.first + it.second }
                    .toSet()

    private fun generateCombinationOfPairs(): List<Pair<Long, Long>> =
            numbers
                    .withoutLastOne()
                    .mapIndexed { indexOfFirstNumber, firstNumber ->
                        numbers
                                .subList(indexOfFirstNumber + 1, numbers.size)
                                .map { secondNumber -> firstNumber to secondNumber }
                    }
                    .flatten()

    fun isNumberValid(numberToValidate: Long): Boolean =
            validNumbers.contains(numberToValidate)

    fun removeFirstAndAdd(newNumber: Long): Window =
            Window(numbers.withoutFirstOne() + newNumber)
}

private fun <E> List<E>.withoutLastOne(): List<E> = this.dropLast(1)

private fun <E> List<E>.withoutFirstOne(): List<E> = this.drop(1)
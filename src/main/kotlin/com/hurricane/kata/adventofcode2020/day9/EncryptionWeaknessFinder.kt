package com.hurricane.kata.adventofcode2020.day9

class EncryptionWeaknessFinder(private val numbers: List<Long>) {

    fun find(invalidNumber: Long): List<Long>? {
        return (0..numbers.size - 2)
                .map { tryToFindInvalidNumberStartingFrom(invalidNumber, it) }
                .find { it != null }
    }

    private fun tryToFindInvalidNumberStartingFrom(invalidNumber: Long, startIndex: Int): List<Long>? {
        var index = startIndex
        var sum = 0L
        val foundNumbers = mutableListOf<Long>()

        do {
            val foundNumber = numbers[index]
            foundNumbers += foundNumber
            sum += foundNumber

            index++
        } while (index < numbers.size && (sum < invalidNumber || foundNumbers.size < 2))

        return when (sum) {
            invalidNumber -> foundNumbers
            else -> null
        }
    }

}
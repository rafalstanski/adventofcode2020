package com.hurricane.kata.adventofcode2020.day9

class EncryptionWeaknessSequenceFinder(private val numbers: List<Long>) {

    fun find(invalidNumber: Long): List<Long>? {
        return (0..numbers.size - 2)
                .map { tryToFindEncryptionWeaknessSequence(invalidNumber, it) }
                .firstOrNull { it.isNotEmpty() }
    }

    private fun tryToFindEncryptionWeaknessSequence(invalidNumber: Long, startingFromIndex: Int): List<Long> {
        var index = startingFromIndex
        var sum = 0L
        val candidates = mutableListOf<Long>()

        do {
            val foundNumber = numbers[index]
            candidates += foundNumber
            sum += foundNumber

            index++
        } while (index < numbers.size && (sum < invalidNumber || candidates.size < 2))

        return when (sum) {
            invalidNumber -> candidates
            else -> listOf()
        }
    }

}
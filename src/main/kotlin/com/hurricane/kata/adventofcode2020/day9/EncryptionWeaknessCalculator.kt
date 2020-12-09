package com.hurricane.kata.adventofcode2020.day9

class EncryptionWeaknessCalculator(numbers: List<Long>) {

    private val finder = EncryptionWeaknessSequenceFinder(numbers)

    fun findAndCalculate(invalidNumber: Long): Long? =
            finder
                    .find(invalidNumber)
                    ?.let { calculateSequenceValue(it) }

    private fun calculateSequenceValue(sequence: List<Long>): Long =
            sequence
                    .sorted()
                    .let { it.first() + it.last() }
}
package com.hurricane.kata.adventofcode2020.day9

class EncryptionWeaknessCalculator(numbers: List<Long>) {

    private val encryptionWeaknessFinder = EncryptionWeaknessFinder(numbers)

    fun calculate(invalidNumber: Long): Long? =
            encryptionWeaknessFinder
                    .find(invalidNumber)
                    ?.sorted()
                    ?.let { it.first() + it.last() }
}
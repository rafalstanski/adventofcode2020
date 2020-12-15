package com.hurricane.kata.adventofcode2020.day15

class Solver(private val turns: Int) {

    fun solve(input: String): Int {
        val extractedInput = input.asNumbers()

        val previousSpoken = mutableMapOf<Int, Int>()
        val recentlySpoken = speakFirstNumbers(extractedInput)

        var recentNumber = extractedInput.last()

        (extractedInput.size + 1..turns).forEach { turn ->
            recentNumber = when {
                previousSpoken.containsKey(recentNumber) -> recentlySpoken[recentNumber]!! - previousSpoken[recentNumber]!!
                else -> 0
            }

            recentlySpoken[recentNumber]?.let {previousSpoken[recentNumber] = it }
            recentlySpoken[recentNumber] = turn
        }

        return recentNumber
    }

    private fun String.asNumbers(): List<Int> =
            this
                    .split(',')
                    .map { it.toInt() }

    private fun speakFirstNumbers(extractedInput: List<Int>): MutableMap<Int, Int> =
            extractedInput
                    .mapIndexed { index, number -> number to index + 1 }
                    .toMap()
                    .toMutableMap()
}
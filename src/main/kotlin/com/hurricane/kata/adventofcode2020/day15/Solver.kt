package com.hurricane.kata.adventofcode2020.day15

class Solver(private val turns: Int) {

    fun solve(input: String): Int {
        val extractedInput = extractNumbers(input)

        val previousSpoken = mutableMapOf<Int,Int>()
        val recentlySpoken = speakFirstNumbers(extractedInput)
        var recentNumber = extractedInput.last()

        (extractedInput.size + 1..turns).forEach { turn ->
            recentNumber = when {
                previousSpoken.containsKey(recentNumber) -> recentlySpoken[recentNumber]!! - previousSpoken[recentNumber]!!
                else -> 0
            }

            recentNumber.let {
                if (recentlySpoken.containsKey(it)) {
                    previousSpoken[it] = recentlySpoken[it]!!
                    recentlySpoken[it] = turn
                } else {
                    recentlySpoken[it] = turn
                }
            }
        }

        return recentNumber
    }

    private fun extractNumbers(input: String): List<Int> {
        return input
                .split(',')
                .map { it.toInt() }
    }

    private fun speakFirstNumbers(extractedInput: List<Int>): MutableMap<Int, Int> {
        return extractedInput
                .mapIndexed { index, number -> number to index + 1 }
                .toMap()
                .toMutableMap()
    }
}
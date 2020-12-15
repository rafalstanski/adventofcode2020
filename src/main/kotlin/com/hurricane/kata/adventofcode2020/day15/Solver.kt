package com.hurricane.kata.adventofcode2020.day15

class Solver(private val turns: Int) {

    fun solve(input: String): Int {
        val extractedInput = extractNumbers(input)

        val previousSpoken = mutableMapOf<Int,Int>()
        val recentlySpoken = speakFirstNumbers(extractedInput)
        var mostRecentlySpokenNumber = extractedInput.last()

        (extractedInput.size + 1..turns).forEach { turn ->
            if (previousSpoken.containsKey(mostRecentlySpokenNumber)) {
                mostRecentlySpokenNumber = recentlySpoken[mostRecentlySpokenNumber]!! - previousSpoken[mostRecentlySpokenNumber]!!
            } else {
                mostRecentlySpokenNumber = 0
            }

            if (recentlySpoken.containsKey(mostRecentlySpokenNumber)) {
                previousSpoken[mostRecentlySpokenNumber] = recentlySpoken[mostRecentlySpokenNumber]!!
                recentlySpoken[mostRecentlySpokenNumber] = turn
            } else {
                recentlySpoken[mostRecentlySpokenNumber] = turn
            }
        }

        return mostRecentlySpokenNumber
    }

    private fun speakFirstNumbers(extractedInput: List<Int>): MutableMap<Int, Int> {
        return extractedInput
                .mapIndexed { index, number -> number to index + 1 }
                .toMap()
                .toMutableMap()
    }

    private fun extractNumbers(input: String): List<Int> {
        return input
                .split(',')
                .map { it.toInt() }
    }
}
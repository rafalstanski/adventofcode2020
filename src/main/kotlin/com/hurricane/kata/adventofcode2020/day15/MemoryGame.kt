package com.hurricane.kata.adventofcode2020.day15

class MemoryGame(private val turns: Int) {

    fun play(input: String): Int {
        val extractedNumbers = input.asNumbers()

        return playUsing(extractedNumbers)
    }

    private fun playUsing(gameNumbers: List<Int>): Int {
        val previousSpoken = mutableMapOf<Int, Int>()
        val recentlySpoken = speakFirstNumbers(gameNumbers)

        var recentNumber = gameNumbers.last()

        (firstTurnAfterSpeaking(gameNumbers)..turns).forEach { turn ->
            recentNumber = when {
                previousSpoken.containsKey(recentNumber) -> recentlySpoken[recentNumber]!! - previousSpoken[recentNumber]!!
                else -> 0
            }

            recentlySpoken[recentNumber]?.let { previousSpoken[recentNumber] = it }
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

    private fun firstTurnAfterSpeaking(gameNumbers: List<Int>) =
            gameNumbers.size + 1
}
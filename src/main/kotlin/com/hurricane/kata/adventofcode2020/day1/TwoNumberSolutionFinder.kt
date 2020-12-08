package com.hurricane.kata.adventofcode2020.day1

class TwoNumberSolutionFinder {

    fun find(source: List<Int>): TwoNumberSolution? {
        if (source.size < 2) return null

        return source
                .take(source.size - 1)
                .firstOrNull { source.contains(2020 - it) }
                ?.let { TwoNumberSolution(it, 2020 - it) }
    }

}
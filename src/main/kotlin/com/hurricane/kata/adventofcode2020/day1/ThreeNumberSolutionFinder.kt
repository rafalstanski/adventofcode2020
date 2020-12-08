package com.hurricane.kata.adventofcode2020.day1

class ThreeNumberSolutionFinder {

    fun find(source: List<Int>): ThreeNumberSolution? {
        if (source.size < 3) return null

        for (first in 0 until source.size - 2) {
            for (second in first + 1 until source.size - 1) {
                for (third in second + 1 until source.size) {
                    if (source[first] + source[second] + source[third] == 2020) {
                        return ThreeNumberSolution(source[first], source[second], source[third])
                    }
                }
            }
        }

        return null
    }

}
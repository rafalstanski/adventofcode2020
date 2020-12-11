package com.hurricane.kata.adventofcode2020.day10

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day10Part1Runner : PuzzleSolution {

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val numbers = puzzleInput.toIntNumbers().toMutableList()
        val x = ((numbers.max()?:0) + 3)
        numbers.add(0)
        numbers.add(x)
        val adapters = numbers.toList()
        val sortedAdapters = adapters.sorted()

        println(sortedAdapters)

        val divs = sortedAdapters
                .drop(1)
                .mapIndexed { index, adapter -> adapter - sortedAdapters[index] }

        println(divs)

        val div1 = divs.count { it == 1 }
        val div3 = divs.count { it == 3 }

        println("1 = $div1 and 3 = $div3")

        return PuzzleAnswer(div1 * div3)
    }

}
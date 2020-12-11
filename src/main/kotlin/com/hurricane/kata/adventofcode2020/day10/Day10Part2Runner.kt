package com.hurricane.kata.adventofcode2020.day10

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day10Part2Runner : PuzzleSolution {

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val numbers = puzzleInput.toIntNumbers().toMutableList()
        val x = ((numbers.max()?:0) + 3)
        numbers.add(0)
        numbers.add(x)
        val adapters = numbers.toList()
        val sortedAdapters = adapters.sorted()

//        A1(0), A2(1), A3(2), A4(5)
//                  1 ,    1,    3

        val divs = sortedAdapters
                .drop(1)
                .mapIndexed { index, adapter -> adapter - sortedAdapters[index] }

        val index3 = divs.mapIndexed { index, div -> index to div }.filter { it.second == 3 }.map { it.first }

        var previousIndex = 0

        val ranges = index3.map {
            val range = previousIndex to it
            previousIndex = it + 1

            range
        }

        val subDivs = ranges.filter { it.second - it.first > 1 }.map { divs.subList(it.first, it.second) }

        println(subDivs)

        val result = subDivs.map {
            println("----------")
            val usedArrangement = mutableSetOf<String>()
            calculateChangesCount(usedArrangement, it)
        }.multi()

        return PuzzleAnswer(result)
    }

    private fun calculateChangesCount(usedArrangement: MutableSet<String>, subDiv: List<Int>): Int {
        if (usedArrangement.contains(toArrangement(subDiv))) return 0

        usedArrangement += toArrangement(subDiv)
        println("$subDiv, used: $usedArrangement")

        if (subDiv.size == 1) return 1

        val innerChangesCount = subDiv.indices
                .filter { it < subDiv.size - 1 && (subDiv[it] < 3 && (subDiv[it] + subDiv[it + 1]) <= 3) }
                .map {
                    val removedSubDiv = subDiv.toMutableList()
                    removedSubDiv[it + 1] = removedSubDiv[it + 1] + removedSubDiv[it]
                    removedSubDiv.removeAt(it)
                    removedSubDiv
                }.map { calculateChangesCount(usedArrangement, it) }
                .sum()

        return 1 + innerChangesCount
    }

    private fun toArrangement(subDiv: List<Int>): String =
            subDiv.joinToString(separator = "-")

}


private fun Iterable<Int>.multi(): Long {
    var sum: Long = 1L
    for (element in this) {
        sum = sum * element.toLong()
    }
    return sum
}
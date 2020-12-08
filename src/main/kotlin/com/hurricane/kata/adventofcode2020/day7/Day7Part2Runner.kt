package com.hurricane.kata.adventofcode2020.day7

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day7Part2Runner : PuzzleSolution {

    private val bagRuleParser = BagRuleParser()

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val rules = puzzleInput.entries.map { bagRuleParser.parse(it) }
        val counter = InnerBagCounter(rules)

        val bagThatCanStore = counter.count("shiny gold")

        return PuzzleAnswer(bagThatCanStore)
    }
}
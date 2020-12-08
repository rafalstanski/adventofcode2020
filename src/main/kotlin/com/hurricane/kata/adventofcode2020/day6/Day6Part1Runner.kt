package com.hurricane.kata.adventofcode2020.day6

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day6Part1Runner : PuzzleSolution {

    private val answersCounter = AnswersCounter(UniqueAnswersExtractor())
    private val answersGroupsParser = AnswersGroupsParser()

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        return answersGroupsParser.parse(puzzleInput.entries)
                .map { answersCounter.count(it) }
                .sum()
                .let { PuzzleAnswer(it) }
    }

}
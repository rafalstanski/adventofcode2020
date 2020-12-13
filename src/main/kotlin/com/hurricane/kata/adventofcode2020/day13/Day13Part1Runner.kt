package com.hurricane.kata.adventofcode2020.day13

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day13Part1Runner : PuzzleSolution {

    private val notesParser = NotesParser()

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val notes = notesParser.parse(puzzleInput.entries)

        val finder = EarliestDepartTimeFinder(notes.buses)
        val departTime = finder.find(notes.arrivalTime)

        return PuzzleAnswer(departTime.bus * departTime.waitTime)
    }

}
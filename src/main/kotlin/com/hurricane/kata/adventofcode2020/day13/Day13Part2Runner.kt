package com.hurricane.kata.adventofcode2020.day13

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day13Part2Runner : PuzzleSolution {

    private val notesParser = NotesParser()
    private val finder = MagicNumberFinder()

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val notes = notesParser.parse(puzzleInput.entries)

        val magicNumber = finder.find(notes.buses)

        return PuzzleAnswer(magicNumber)
    }

}
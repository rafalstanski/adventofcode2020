package com.hurricane.kata.adventofcode2020.day16

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day16Part1Runner : PuzzleSolution {

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val note = NoteParser.parse(puzzleInput.entries)

        val errorRate = note.nearbyTickets
                .map { invalidValues(it, note.fields) }
                .flatten()
                .sum()

        return PuzzleAnswer(errorRate)
    }

    private fun invalidValues(ticket: List<Int>, fields: List<Field>): List<Int> =
            ticket.filter { isInvalidValue(it, fields) }

    private fun isInvalidValue(value: Int, fields: List<Field>): Boolean =
            fields.all { it.isNotValid(value) }

}

/*
class: 1-3 or 5-7
row: 6-11 or 33-44
seat: 13-40 or 45-50

your ticket:
7,1,14

nearby tickets:
7,3,47
40,4,50
55,2,20
38,6,12
 */
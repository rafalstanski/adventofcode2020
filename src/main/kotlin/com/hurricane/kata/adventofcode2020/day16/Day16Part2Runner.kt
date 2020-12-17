package com.hurricane.kata.adventofcode2020.day16

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day16Part2Runner : PuzzleSolution {

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val note = NoteParser.parse(puzzleInput.entries)

        val correctTickets = note.nearbyTickets
                .filter { !hasInvalidField(it, note.fields) }


        val allFieldsNames = note.fields.map { it.name }.toSet()
        val matchingFieldsForAllValues = note.fields
                .map { allFieldsNames }
                .toMutableList()

        correctTickets
                .map { ticket -> ticket.map { value -> collectMatchingFields(note, value) } }
                .forEach { ticketWithMatchingFields -> ticketWithMatchingFields.mapIndexed { valueIndex, matchingFields -> matchingFieldsForAllValues[valueIndex] = matchingFieldsForAllValues[valueIndex] intersect matchingFields }  }

        val fromSmallestToBiggestIndexes = matchingFieldsForAllValues
                .mapIndexed { index, set -> index to set.size }
                .sortedBy { it.second }
                .map { it.first }

        fromSmallestToBiggestIndexes.map { currentIndex ->
            matchingFieldsForAllValues.indices
                    .filter { it != currentIndex }
                    .forEach {matchingFieldsForAllValues[it] = matchingFieldsForAllValues[it] - matchingFieldsForAllValues[currentIndex] }
        }

        val departureFields = matchingFieldsForAllValues
                .map { it.first() }
                .mapIndexed { index, fieldName -> index to fieldName }
                .filter { it.second.startsWith("departure") }
                .map { it.first }

        val answerValue = note.myTicket
                .filterIndexed { index, _ -> departureFields.contains(index) }
                .map { it.toLong() }
                .map {
                    println(it)
                    it
                }
                .reduce { acc, value -> acc * value }

        return PuzzleAnswer(answerValue)
    }

    private fun hasInvalidField(ticket: List<Int>, fields: List<Field>): Boolean =
            ticket.any { isInvalidValue(it, fields) }

    private fun isInvalidValue(value: Int, fields: List<Field>): Boolean =
            fields.all { it.isNotValid(value) }

    private fun collectMatchingFields(note: Note, value: Int) =
            note.fields
                    .filter { it.isValid(value) }
                    .map { it.name }

}
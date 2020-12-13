package com.hurricane.kata.adventofcode2020.day13

class NotesParser {

    fun parse(notesInput: List<String>): Notes {
        return Notes(
                arrivalTime = extractArrivalTime(notesInput),
                buses = extractBuses(notesInput))
    }

    private fun extractArrivalTime(notesInput: List<String>) =
            notesInput[0].toInt()

    private fun extractBuses(notesInput: List<String>) =
            notesInput[1]
                    .split(',')
                    .mapIndexed { index, busId -> index to busId }
                    .filter { isOutOfService(it.second) }
                    .map { Bus(it.second.toInt(), it.first) }

    private fun isOutOfService(busId: String) =
            busId != "x"
}
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
                    .filter { isOutOfService(it) }
                    .map { Bus(it.toInt(), 0) }

    private fun isOutOfService(bus: String) =
            bus != "x"
}
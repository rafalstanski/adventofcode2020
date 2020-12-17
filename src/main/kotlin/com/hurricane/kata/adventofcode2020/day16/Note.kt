package com.hurricane.kata.adventofcode2020.day16

data class Note (
    val fields: List<Field>,
    val myTicket: List<Int>,
    val nearbyTickets: List<List<Int>>
) {

    fun findFieldNamed(name: String):Field {
        return fields.first { it.name == name }
    }
}

data class Field(val index: Int, val name: String, val ranges: List<IntRange>) {

    fun isNotValid(value: Int): Boolean = ! isValid(value)

    fun isValid(value: Int): Boolean =
            ranges.any { it.contains(value) }
}

object NoteParser {

    fun parse(entries: List<String>): Note {
        val entriesIt = entries.iterator()

        return Note(
                fields = parseFields(entriesIt),
                myTicket = parseMyTicket(entriesIt),
                nearbyTickets = parseOtherTickets(entriesIt)
        )
    }

    private fun parseFields(entriesIt: Iterator<String>) =
            extractSection(entriesIt).mapIndexed { index, fieldEntry ->  toField(index, fieldEntry) }

    private fun toField(fieldIndex: Int, fieldEntry: String): Field {
        val fieldParts = fieldEntry.split(':')

        val name = fieldParts[0]
        val ranges = "[0-9]+\\-[0-9]+".toRegex().findAll(fieldParts[1]).toList()
                .map { it.value.split('-') }
                .map { IntRange(it[0].toInt(), it[1].toInt()) }

        return Field(
                index = fieldIndex,
                name = name,
                ranges = ranges)
    }

    private fun parseMyTicket(entriesIt: Iterator<String>) =
            toListOfValues(extractSection(entriesIt)[1])

    private fun parseOtherTickets(entriesIt: Iterator<String>) =
            extractSection(entriesIt)
                    .drop(1)
                    .map { toListOfValues(it) }

    private fun toListOfValues(ticket: String): List<Int> {
        return ticket.split(',')
                .map { it.toInt() }
    }

    private fun extractSection(entriesIt: Iterator<String>): List<String> {
        val fieldsEntries = mutableListOf<String>()

        do {
            val entry = entriesIt.next()
            if (entry.isNotBlank()) fieldsEntries.add(entry)
        } while (entry.isNotBlank() && entriesIt.hasNext())

        return fieldsEntries
    }
}
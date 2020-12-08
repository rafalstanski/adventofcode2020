package com.hurricane.kata.adventofcode2020.day6

class AnswersGroupsParser {

    fun parse(answersLines: List<String>): List<List<String>> {
        val groups = mutableListOf<List<String>>()

        val groupLines = mutableListOf<String>()

        answersLines.forEach { answersLine ->
            if (answersLine.isNotBlank()) {
                groupLines += answersLine
            } else {
                groups.add(groupLines.toList())
                groupLines.clear()
            }
        }

        groups.add(groupLines.toList())

        return groups
    }
}
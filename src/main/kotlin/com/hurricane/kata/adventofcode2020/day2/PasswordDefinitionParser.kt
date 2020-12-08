package com.hurricane.kata.adventofcode2020.day2

class PasswordDefinitionParser {

    fun parse(passwordDefinitionEntry: String): PasswordDefinition {
        val parts = passwordDefinitionEntry.split(' ')

        val restrictions = parseRestrictions(parts[0])
        val requiredLetter = parseLetter(parts[1])
        val password = parsePassword(parts[2])

        return PasswordDefinition(requiredLetter, restrictions.first, restrictions.second, password)
    }

    private fun parseRestrictions(rangePart: String): Pair<Int, Int> {
        val rangeParts = rangePart.split('-')

        return rangeParts[0].toInt() to rangeParts[1].toInt()
    }

    private fun parseLetter(letterPart: String): Char {
        return letterPart.first()
    }

    private fun parsePassword(passwordPart: String): String {
        return passwordPart
    }

}
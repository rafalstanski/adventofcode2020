package com.hurricane.kata.adventofcode2020.day2

class ValidPasswordCounter(
        private val passwordDefinitionParser: PasswordDefinitionParser,
        private val passwordValidator: PasswordValidator
) {

    fun count(passwordDefinitionEntries: List<String>): Int {
        return passwordDefinitionEntries
                .map { passwordDefinitionParser.parse(it) }
                .map { passwordValidator.validate(it) }
                .count { it }
    }

}
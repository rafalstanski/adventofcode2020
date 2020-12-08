package com.hurricane.kata.adventofcode2020.day2

data class PasswordDefinition(
        val requiredLetter: Char,
        val firstRestriction: Int,
        val secondRestriction: Int,
        val password: String
)
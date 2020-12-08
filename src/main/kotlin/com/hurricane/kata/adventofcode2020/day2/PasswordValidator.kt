package com.hurricane.kata.adventofcode2020.day2

interface PasswordValidator {

    fun validate(passwordDefinition: PasswordDefinition): Boolean
}

class PasswordRangeValidator : PasswordValidator {

    override fun validate(passwordDefinition: PasswordDefinition): Boolean = with(passwordDefinition) {
        val requiredLetterCount = password.count { it == requiredLetter }

        return requiredLetterCount in firstRestriction..secondRestriction
    }

}

class PasswordIndexValidator : PasswordValidator {

    override fun validate(passwordDefinition: PasswordDefinition): Boolean = with(passwordDefinition) {
        val firstLetter = password.elementAt(firstRestriction - 1)
        val secondLetter = password.elementAt(secondRestriction - 1)

        return (firstLetter == requiredLetter) xor (secondLetter == requiredLetter)
    }

}
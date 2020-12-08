package com.hurricane.kata.adventofcode2020.day4

class ValidPassportCounter(
        private val passportsParser: PassportsParser,
        private val passportValidator: PassportValidator) {

    fun count(inputLines: List<String>): Int =
            passportsParser
                    .parse(inputLines)
                    .map { passportValidator.valid(it) }
                    .count { it }

}
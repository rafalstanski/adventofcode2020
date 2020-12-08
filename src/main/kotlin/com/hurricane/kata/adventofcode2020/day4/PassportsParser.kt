package com.hurricane.kata.adventofcode2020.day4

class PassportsParser {

    fun parse(inputLines: List<String>): List<Passport> {
        val sectionsExtractor = PassportSectionsExtractor(inputLines)

        return sectionsExtractor.extractSections()
                .map { createPassport(it) }
    }

    private fun createPassport(passwordSection: PassportSection): Passport {
        val fields = passwordSection.passportLines
                .flatMap { line -> line.split(' ') }
                .map { field -> field.split(':') }
                .map { fieldParts -> createPassportField(fieldParts) }

        return Passport(fields)
    }

    private fun createPassportField(fieldParts: List<String>) =
            PassportField(
                    PassportFieldType.valueOf(fieldParts[0].toUpperCase()),
                    fieldParts[1])

}

class PassportSectionsExtractor(private val inputLines: List<String>) {

    private val passportLines = mutableListOf<String>()
    private val sections = mutableListOf<PassportSection>()

    fun extractSections(): List<PassportSection> {
        inputLines.forEach { line ->
            if (lineIsNotNewSectionMarker(line)) {
                pushPassportLine(line)
            } else {
                createSectionFromCurrentlyPushedLines()
            }
        }
        createSectionFromCurrentlyPushedLines()

        return sections
    }

    private fun lineIsNotNewSectionMarker(line: String): Boolean =
            line.isNotBlank()

    private fun pushPassportLine(line: String) {
        passportLines += line
    }

    private fun createSectionFromCurrentlyPushedLines() {
        sections += PassportSection(createCopyOfCurrentPassportLines())
        passportLines.clear()
    }

    private fun createCopyOfCurrentPassportLines() =
            passportLines.toList()

}

data class PassportSection(val passportLines: List<String>)

package com.hurricane.kata.adventofcode2020.day20

class TileParser {

    private val borderExtractor = BorderExtractor()
    private val hashCalculator = BorderHashCalculator()

    fun parse(tileLines: List<String>): Tile =
            Tile(
                    id = extractId(tileLines),
                    lines = extractLines(tileLines),
                    borders = extractBorders(tileLines))

    private fun extractId(tileLines: List<String>): Int =
            "Tile ([0-9]+)\\:".toRegex()
                    .matchEntire(tileLines.first())!!
                    .groupValues[1]
                    .toInt()

    private fun extractLines(tileLines: List<String>): List<String> =
            tileLines.drop(1)

    private fun extractBorders(tileLines: List<String>): List<Border> =
            tileLines.drop(1).let { borderExtractor.extract(it) }

    private fun extractBorderHashes(tileLines: List<String>): List<Int> =
            tileLines.drop(1).let { lines ->
                listOf(
                        topBorder(lines),
                        rightBorder(lines),
                        bottomBorder(lines),
                        leftBorder(lines)
                ).map { hashCalculator.calculate(it) }
            }

    private fun topBorder(tileLines: List<String>): String =
            tileLines.first()

    private fun rightBorder(tileLines: List<String>): String =
            tileLines
                    .map { it.last() }
                    .joinToString("")
                    .reversed()

    private fun bottomBorder(tileLines: List<String>): String =
            tileLines.last()

    private fun leftBorder(tileLines: List<String>): String =
            tileLines
                    .map { it.first() }
                    .joinToString("")
                    .reversed()

}
package com.hurricane.kata.adventofcode2020.day20

class BorderExtractor {

    fun extract(tileLines: List<String>): List<Border> {
        return listOf(
                topBorder(tileLines),
                rightBorder(tileLines),
                bottomBorder(tileLines),
                leftBorder(tileLines)
        )
    }

    private fun topBorder(tileLines: List<String>): Border =
            TopBorder(tileLines.first())

    private fun rightBorder(tileLines: List<String>): Border =
            tileLines
                    .map { it.last() }
                    .joinToString("")
                    .let { RightBorder(it) }

    private fun bottomBorder(tileLines: List<String>): Border =
            BottomBorder(tileLines.last())

    private fun leftBorder(tileLines: List<String>): Border =
            tileLines
                    .map { it.first() }
                    .joinToString("")
                    .let { LeftBorder(it) }

}
package com.hurricane.kata.adventofcode2020.day11

class SeatGridParser {

    private val rowParser = RowLayoutParser()

    fun parse(rowLayouts: List<String>): SeatGrid {
        val rows = rowLayouts.map { rowParser.parse(it) }

        return SeatGrid(rows)
    }
}
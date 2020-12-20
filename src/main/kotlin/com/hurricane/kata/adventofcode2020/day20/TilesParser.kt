package com.hurricane.kata.adventofcode2020.day20

class TilesParser {

    private val tileParser = TileParser()

    fun parse(tilesLines: List<String>): List<Tile> {
        val tiles = mutableListOf<Tile>()
        val tileLines = mutableListOf<String>()

        tilesLines.forEach { tileLine ->
            if (isTileSeparator(tileLine)) {
                tiles += createTileUsing(tileLines)
                tileLines.clear()
            } else {
                tileLines += tileLine
            }
        }

        tiles += createTileUsing(tileLines)

        return tiles
    }

    private fun isTileSeparator(tileLine: String) =
            tileLine.isBlank()

    private fun createTileUsing(tileLines: MutableList<String>) =
            tileParser.parse(tileLines.toList())
}
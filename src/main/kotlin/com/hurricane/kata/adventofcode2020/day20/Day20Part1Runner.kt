package com.hurricane.kata.adventofcode2020.day20

import com.hurricane.kata.adventofcode2020.day13.product
import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day20Part1Runner : PuzzleSolution {
    private val tileParser = TilesParser()

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val tiles = tileParser.parse(puzzleInput.entries)

//        val tile1427 = tiles.first { it.id == 3079 }
//        tile1427.borders.map { border ->
//            tiles.filter { it.id != tile1427.id }
//                    .any { tile -> tile.matchesAnyBorder(border) }
//        }
//                .count { it }
//                .let { println("matching: $it") }
//

        val corners = tiles.map { tile ->
            tile to tile.borders.map { border ->
                tiles.filter { it.id != tile.id }
                        .any { it.matchesAnyBorder(border) }
            }.count { it }
        }
                .filter { it.second == 2 }
                .map { it.first.id to it.second }
                .map { it.first.toLong() }
                .product()


        return PuzzleAnswer(corners)
    }

}
package com.hurricane.kata.adventofcode2020.day12

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution
import kotlin.math.abs

class Day12Part1Runner : PuzzleSolution {

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val data = puzzleInput.entries
                .map { it.toCharArray()[0] to it.toCharArray().drop(1).joinToString("").toInt() }

        var directions = mapOf(
                0 to (0 to 1),
                90 to (1 to 0),
                180 to (0 to -1),
                270 to (-1 to 0))
        var angle = 90
        var x = 0
        var y = 0

        data.forEach { (action, value) ->
            when (action) {
                'N' -> y += value
                'S' -> y -= value
                'E' -> x += value
                'W' -> x -= value
                'L' -> angle = roundTo360(angle - value)
                'R' -> angle = roundTo360(angle + value)
                'F' -> {
                    val (directionX, directionY) = directions[angle]!!

                    x += directionX * value
                    y += directionY * value
                }
            }
//            println("x: $x, y: $y, angel: $angle")
        }

        return PuzzleAnswer(abs(x) + abs(y))
    }

}

private fun roundTo360(angle: Int): Int =
        (360 + angle) % 360
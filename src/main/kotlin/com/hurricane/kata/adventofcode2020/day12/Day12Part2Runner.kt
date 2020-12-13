package com.hurricane.kata.adventofcode2020.day12

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution
import kotlin.math.abs

class Day12Part2Runner : PuzzleSolution {

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val data = puzzleInput.entries
                .map { it.toCharArray()[0] to it.toCharArray().drop(1).joinToString("").toInt() }
        var x = 0
        var y = 0
        var waypointX = 10
        var waypointY = 1

        data.forEach { (action, value) ->
            when (action) {
                'N' -> waypointY += value
                'S' -> waypointY -= value
                'E' -> waypointX += value
                'W' -> waypointX -= value
                'L' -> {
                    val rotated = rotate(waypointX, waypointY, value)
                    waypointX = rotated.first
                    waypointY = rotated.second
                }
                'R' -> {
                    val rotated = rotate(waypointX, waypointY, -value)
                    waypointX = rotated.first
                    waypointY = rotated.second
                }
                'F' -> {
                    x += waypointX * value
                    y += waypointY * value
                }
            }
//            println("x: $x, y: $y, waypointX: $waypointX, waypointY: $waypointY")
        }

        return PuzzleAnswer(abs(x) + abs(y))
    }

}

private val sincos = mapOf(
        0 to (0 to 1),
        90 to (1 to 0),
        180 to (0 to -1),
        270 to (-1 to 0))

private fun rotate(x: Int, y: Int, angle: Int): Pair<Int, Int> {
    val angleRounded = (360 + angle) % 360
    val sin = sincos[angleRounded]!!.first
    val cos = sincos[angleRounded]!!.second

    val xPrim = x * cos - y * sin
    val yPrim = x * sin + y * cos

    return xPrim to yPrim
}
package com.hurricane.kata.adventofcode2020.day3

class Traverser(private val board: Board) {

    fun travel(down: Int, right: Int): List<PointType> {
        val visitedPoints = mutableListOf<PointType>()

        var checkRow = down
        var checkCol = right

        while (checkRow < board.boardLines) {
            val point = board.peekAt(checkRow, checkCol)

            visitedPoints.add(point)

            checkRow += down
            checkCol += right
        }

        return visitedPoints
    }

}
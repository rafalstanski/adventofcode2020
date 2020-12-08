package com.hurricane.kata.adventofcode2020.day3

class Board(boardLines: List<String>) {

    private val board: List<List<PointType>> = parseBoardLines(boardLines)

    private val boardSize = indicateBoardSize()

    val boardLines = board.size

    fun peekAt(row: Int, col: Int): PointType {
        val wrappedCol = col % boardSize

        return board[row][wrappedCol]
    }

    private fun parseBoardLines(boardLines: List<String>): List<List<PointType>> {
        return boardLines.map { line ->
            line.map { point ->
                when (point) {
                    '.' -> PointType.OPEN_SQUARE
                    else -> PointType.TREE
                }
            }
        }
    }

    private fun indicateBoardSize() = board.map { it.size }.max() ?: 0

}

enum class PointType {
    OPEN_SQUARE, TREE
}
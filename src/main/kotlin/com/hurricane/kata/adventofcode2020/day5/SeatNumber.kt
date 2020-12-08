package com.hurricane.kata.adventofcode2020.day5

data class SeatNumber(
        val row: Int,
        val col: Int
) {
    val id: Int = row * 8 + col
}
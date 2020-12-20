package com.hurricane.kata.adventofcode2020.day20

sealed class Border(open val pixels: String) {

    fun rotate(): Border = when (this) {
        is TopBorder -> RightBorder(pixels)
        is RightBorder -> BottomBorder(pixels.reversed())
        is BottomBorder -> LeftBorder(pixels)
        is LeftBorder -> TopBorder(pixels.reversed())
    }

    fun flip(): Border = when (this) {
        is TopBorder -> this.copy(this.pixels.reversed())
        is RightBorder -> this.copy(this.pixels.reversed())
        is BottomBorder -> this.copy(this.pixels.reversed())
        is LeftBorder -> this.copy(this.pixels.reversed())
    }
}

data class TopBorder(override val pixels: String) : Border(pixels)
data class RightBorder(override val pixels: String) : Border(pixels)
data class BottomBorder(override val pixels: String) : Border(pixels)
data class LeftBorder(override val pixels: String) : Border(pixels)

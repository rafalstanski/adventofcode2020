package com.hurricane.kata.adventofcode2020.day20

data class Tile(
        val id: Int,
        val lines: List<String>,
        val borders: List<Border>) {

    fun matchesAnyBorder(border: Border): Boolean {
        var rotatedBorder = border
        repeat(4) {
            if (matches(rotatedBorder)) return true

            rotatedBorder = rotatedBorder.rotate()
        }

        rotatedBorder = border.flip()
        repeat(4) {
            if (matches(rotatedBorder)) return true

            rotatedBorder = rotatedBorder.rotate()
        }

        return false
    }

    private fun matches(border: Border): Boolean {
        return when (border) {
            is TopBorder -> matchesBottomBorder(border)
            is RightBorder -> matchesLeftBorder(border)
            is BottomBorder -> matchesTopBorder(border)
            is LeftBorder -> matchesRightBorder(border)
        }
    }

    private fun matchesBottomBorder(border: TopBorder): Boolean {
        val checkedBorder = borders.first { it is BottomBorder }

        return border.pixels == checkedBorder.pixels
    }

    private fun matchesLeftBorder(border: RightBorder): Boolean {
        val checkedBorder = borders.first { it is LeftBorder }

        return border.pixels == checkedBorder.pixels
    }

    private fun matchesRightBorder(border: LeftBorder): Boolean {
        val checkedBorder = borders.first { it is RightBorder }

        return border.pixels == checkedBorder.pixels
    }

    private fun matchesTopBorder(border: BottomBorder): Boolean {
        val checkedBorder = borders.first { it is TopBorder }

        return border.pixels == checkedBorder.pixels
    }

//    private fun matchesBorderOf(border: Border, borderType: KClass<out Border>): Boolean {
//        val bottomBorder = borders.first { borderType.isInstance(it) }
//
//        return border.pixels == bottomBorder.pixels
//    }
}
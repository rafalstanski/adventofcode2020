package com.hurricane.kata.adventofcode2020.day14

import kotlin.math.pow

class ValueMaskModifier(mask: String) {

    private val orMask = calculateMaskValueFiltering(mask) {it == '1'}

    private val andMask = calculateMaskValueFiltering(mask) {it != '0'}

    private fun calculateMaskValueFiltering(mask: String, predicate: (Char) -> Boolean): Long {
        return mask
                .reversed()
                .mapIndexed { index, bitChar -> index to bitChar }
                .filter { predicate(it.second) }
                .map { 2.0.pow(it.first) }
                .sum().toLong()
    }

    fun modify(value: Long): Long =
            value
                    .let { modifyUsingSettingBits(it) }
                    .let { modifyUsingResettingBits(it) }

    private fun modifyUsingSettingBits(value: Long): Long =
            value.or(orMask)

    private fun modifyUsingResettingBits(value: Long): Long =
            value.and(andMask)

    companion object {

        @JvmStatic
        fun noModifications(): ValueMaskModifier {
            return ValueMaskModifier("X".repeat(36))
        }
    }

}
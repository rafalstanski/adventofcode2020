package com.hurricane.kata.adventofcode2020.day14

import kotlin.math.pow

class ValueMaskModifier(private val mask: String) {

    fun modify(value: Long): Long {
        val afterSettingValue = modifyUsingSettingBits(value)
        val afterResettingValue = modifyUsingResettingBits(afterSettingValue)

        return afterResettingValue
    }

    private fun modifyUsingSettingBits(value: Long): Long {
        val orMask = mask
                .reversed()
                .mapIndexed { index, bitChar -> index to bitChar }
                .filter { it.second == '1' }
                .map { 2.0.pow(it.first) }
                .sum().toLong()

        return value.or(orMask)
    }

    private fun modifyUsingResettingBits(value: Long): Long {
        val andMask = mask
                .reversed()
                .mapIndexed { index, bitChar -> index to bitChar }
                .filter { it.second != '0' }
                .map { 2.0.pow(it.first) }
                .sum().toLong()

        return value.and(andMask)
    }

    companion object {

        fun noModifications(): ValueMaskModifier {
            return ValueMaskModifier("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
        }
    }

}
package com.hurricane.kata.adventofcode2020.day14

import kotlin.math.pow

class MemoryAddressDecoder(mask: String) {

    private val orMask = asSingleBitsWithPosition(mask)
            .filter { it.second == '1' }
            .map { 2.0.pow(it.first) }
            .sum()
            .toLong()

    private val xSlotsIndexes = asSingleBitsWithPosition(mask)
            .filter { it.second == 'X' }
            .map { it.first }

    private fun asSingleBitsWithPosition(mask: String): List<Pair<Int, Char>> {
        return mask
                .reversed()
                .mapIndexed { index, bitChar -> index to bitChar }
    }

    fun decode(address: Long): Set<Long> {
        val afterSettingValue = modifyUsingSettingBits(address)

        return generatePossibleValues(afterSettingValue)
    }

    private fun modifyUsingSettingBits(value: Long): Long =
            value.or(orMask)

    private fun generatePossibleValues(value: Long): Set<Long> {
        val base = value.toString(2).padStart(36, '0').reversed().toList()

        val allCombinations = mutableListOf<List<Char>>()

        generateAllCombinations(allCombinations, base, 0)

        return allCombinations
                .map { perm -> perm.joinToString("").reversed().toLong(2) }
                .toSet()
    }

    private fun generateAllCombinations(acc:MutableList<List<Char>>, bits: List<Char>, indexToChange: Int) {
        if (indexToChange < xSlotsIndexes.size) {
            val with0 = bits.toMutableList()
            with0[xSlotsIndexes[indexToChange]] = '0'
            val with1 = bits.toMutableList()
            with1[xSlotsIndexes[indexToChange]] = '1'

            generateAllCombinations(acc, with0, indexToChange.inc())
            generateAllCombinations(acc, with1, indexToChange.inc())
        } else {
            acc += bits
        }
    }

}
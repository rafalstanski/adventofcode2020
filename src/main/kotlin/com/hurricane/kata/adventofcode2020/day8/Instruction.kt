package com.hurricane.kata.adventofcode2020.day8

data class Instruction(
        val operation: OperationType,
        val originalValue: String
) {
    val value = originalValue.toInt()
}

enum class OperationType {
    NOP, ACC, JMP
}
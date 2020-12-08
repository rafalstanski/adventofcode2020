package com.hurricane.kata.adventofcode2020.day8

class InstructionParser {

    fun parse(instructionString: String): Instruction {
        val parts = instructionString.split(' ')

        return Instruction(
                operation = OperationType.valueOf(parts[0].toUpperCase()),
                originalValue = parts[1])
    }
}
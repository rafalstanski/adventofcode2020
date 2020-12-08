package com.hurricane.kata.adventofcode2020.day8

import com.hurricane.kata.adventofcode2020.day8.OperationType.*

class ProgramRunner(private val instructionParser: InstructionParser) {

    fun run(instructionStrings: List<String>): ProgramResult {
        val instructions = parseInstructions(instructionStrings)

        return ProgramExecution(instructions).run()
    }

    private fun parseInstructions(instructionStrings: List<String>) =
            instructionStrings.map { instructionParser.parse(it) }
}

class SelfFixableProgramRunner(private val instructionParser: InstructionParser) {

    fun run(instructionStrings: List<String>): ProgramResult {
        val instructions = parseInstructions(instructionStrings)

        val result = runInstructions(instructions)

        return if (result.infiniteLoopDetected) {
            runWithFixedInstruction(instructions)
        } else {
            result
        }
    }

    private fun parseInstructions(instructionStrings: List<String>) =
            instructionStrings.map { instructionParser.parse(it) }

    private fun runWithFixedInstruction(instructions: List<Instruction>): ProgramResult {
        val instructionsToChangeIndexesIterator = findChangeCandidatesIndexes(instructions).iterator()

        var result: ProgramResult
        do {
            val instructionsToChangeIndex = instructionsToChangeIndexesIterator.next()
            val fixedInstructions = changeInstructionAt(instructionsToChangeIndex, instructions)

            result = runInstructions(fixedInstructions)
        } while (result.infiniteLoopDetected && instructionsToChangeIndexesIterator.hasNext())

        return result
    }

    private fun findChangeCandidatesIndexes(instructions: List<Instruction>): List<Int> =
            instructions
                    .mapIndexed { index, instruction -> index to instruction }
                    .filter { isInstructionCandidateToBeChanged(it.second) }
                    .map { it.first }

    private fun isInstructionCandidateToBeChanged(instruction: Instruction): Boolean =
            instruction.operation in setOf(JMP, NOP)

    private fun changeInstructionAt(instructionsToChangeIndex: Int, instructions: List<Instruction>): List<Instruction> {
        val changedInstructions = instructions.toMutableList()
        val instructionToChange = instructions[instructionsToChangeIndex]

        changedInstructions[instructionsToChangeIndex] = when (instructionToChange.operation) {
            JMP -> instructionToChange.copy(operation = NOP)
            NOP -> instructionToChange.copy(operation = JMP)
            else -> throw IllegalArgumentException("Operation not suitable for change: $instructionToChange")
        }

        return changedInstructions
    }

    private fun runInstructions(instructions: List<Instruction>): ProgramResult =
            ProgramExecution(instructions).run()
}

class ProgramExecution(private val instructions: List<Instruction>) {

    fun run(): ProgramResult {
        var acc = 0
        var instructionIndex = 0
        val executedIndexes = mutableListOf<Int>()
        var infiniteLoopDetected = false

        while (instructionIndex < instructions.size && !infiniteLoopDetected) {
            executedIndexes += instructionIndex

            val instruction = instructions[instructionIndex]

            when (instruction.operation) {
                ACC -> {
                    acc += instruction.value
                    instructionIndex += 1
                }
                NOP -> {
                    instructionIndex += 1
                }
                JMP -> {
                    instructionIndex += instruction.value
                }
            }

            infiniteLoopDetected = executedIndexes.contains(instructionIndex)
        }

        return ProgramResult(
                accumulator = acc,
                infiniteLoopDetected = infiniteLoopDetected)
    }
}

data class ProgramResult(
        val accumulator: Int,
        val infiniteLoopDetected: Boolean
)
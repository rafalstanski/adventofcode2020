package com.hurricane.kata.adventofcode2020.day14

class CommandsParser {

    fun parse(commandsInput: List<String>): List<Command> =
            commandsInput.map { parseCommand(it) }

    private fun parseCommand(commandInput: String): Command {
        return when {
            commandInput.startsWith("mask") -> parseMaskCommand(commandInput)
            commandInput.startsWith("mem") -> parseStoreCommand(commandInput)
            else -> throw IllegalArgumentException("Unknown command")
        }
    }

    private fun parseMaskCommand(commandInput: String): MaskCommand {
        val maskRegex = "mask = (.+)".toRegex()
        val matchMask = maskRegex.matchEntire(commandInput)

//        return MaskCommand(matchMask!!.groups[1]!!.value)
        return MaskCommand(matchMask.valueAt(1))
    }

    private fun parseStoreCommand(commandInput: String): StoreCommand {
        val maskRegex = "mem\\[([0-9]+)\\] = ([0-9]+)".toRegex()
        val matchMask = maskRegex.matchEntire(commandInput)

        val memoryAddress = matchMask.valueAt(1).toInt()
        val value = matchMask.valueAt(2).toLong()

        return StoreCommand(memoryAddress, value)
    }
}

private fun MatchResult?.valueAt(groupIndex: Int): String =
        this!!.groups[groupIndex]!!.value
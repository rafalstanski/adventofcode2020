package com.hurricane.kata.adventofcode2020.day14

class Program(commandsInputs: List<String>) {

    private val commands = CommandsParser().parse(commandsInputs)
    private val memory = mutableMapOf<Int, Long>()
    private var modifier = ValueMaskModifier.noModifications()

    fun run(): ProgramResult {
        executeAllCommands()

        return ProgramResult(memory.toMap())
    }

    private fun executeAllCommands() {
        commands.forEach {
            executeCommand(it)
        }
    }

    private fun executeCommand(command: Command) {
        when (command) {
            is StoreCommand -> memory[command.memoryAddress] = modifier.modify(command.value)
            is MaskCommand -> modifier = ValueMaskModifier(command.value)
        }
    }
}

class ProgramResult(private val memory: Map<Int, Long>) {

    val memorySum = memory.values.sum()

    fun readMemoryAt(address: Int): Long? {
        return memory[address]
    }

}
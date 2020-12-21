package com.hurricane.kata.adventofcode2020.day14

class Program(
        private val valueMaskModifier: (String) -> ValueMaskModifier,
        private val memoryAddressDecoder: (String) -> MemoryAddressDecoder,
        commandsInputs: List<String>) {

    private val commands = CommandsParser().parse(commandsInputs)
    private val memory = mutableMapOf<Long, Long>()
    private var modifier = ValueMaskModifier.noModifications()
    private var decoder: MemoryAddressDecoder = MemoryAddressDecoder.noFloating()

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
            is StoreCommand -> storeInMemory(command)
            is MaskCommand -> maskChanged(command)
        }
    }

    private fun maskChanged(command: MaskCommand) {
        modifier = valueMaskModifier(command.value)
        decoder = memoryAddressDecoder(command.value)
    }

    private fun storeInMemory(command: StoreCommand) {
        val modifiedValue = modifier.modify(command.value)

        decoder.decode(command.memoryAddress).forEach {
            memory[it] = modifiedValue
        }
    }
}

class ProgramResult(private val memory: Map<Long, Long>) {

    val memorySum = memory.values.sum()

    fun readMemoryAt(address: Long): Long? {
        return memory[address]
    }

}
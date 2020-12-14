package com.hurricane.kata.adventofcode2020.day14

sealed class Command

class MaskCommand(val value: String) : Command()

class StoreCommand(val memoryAddress: Int, val value: Long) : Command()
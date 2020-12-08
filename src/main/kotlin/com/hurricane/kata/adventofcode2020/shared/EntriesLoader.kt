package com.hurricane.kata.adventofcode2020.shared

object EntriesLoader {

    fun load(fileName: String): List<String> {
        val resource = Thread.currentThread().contextClassLoader.getResource(fileName)

        return requireNotNull(resource)
                .readText()
                .split("\n")
    }
}
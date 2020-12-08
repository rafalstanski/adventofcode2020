package com.hurricane.kata.adventofcode2020.day7

import com.hurricane.kata.adventofcode2020.shared.logger

class BagStorageCounter(private val bagRules: List<BagRule>) {

    fun count(bagToContain: String): Int {
        val directAndIndirectBugs = findDirectAndIndirectBagsThatCanStore(bagToContain)

        return directAndIndirectBugs.size
    }

    private fun findDirectAndIndirectBagsThatCanStore(bagToContain: String): Set<String> {
        val bagThatCanStore = findBagsThatCanStore(bagToContain)
        val indirectBagsThatCanStore = findIndirectBagsThatCanStore(bagThatCanStore)

        return bagThatCanStore + indirectBagsThatCanStore
    }

    private fun findBagsThatCanStore(bagToContain: String) =
            bagRules
                    .filter { it.canStore(bagToContain) }
                    .map { it.concernsBag }
                    .toSet()

    private fun findIndirectBagsThatCanStore(bagThatCanStore: Iterable<String>): Set<String> {
        return bagThatCanStore
                .flatMap { findDirectAndIndirectBagsThatCanStore(it) }
                .toSet()
    }

    companion object {
        private val logger by logger()
    }

}
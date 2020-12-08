package com.hurricane.kata.adventofcode2020.day7

data class BagRule(
        val concernsBag: String,
        val storage: List<BagStorage>
) {

    fun canStore(bagToContain: String): Boolean =
            storage.any { it.color == bagToContain }
}

data class BagStorage(
        val color: String,
        val count: Int
)

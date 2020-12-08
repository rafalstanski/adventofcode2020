package com.hurricane.kata.adventofcode2020.day7

class InnerBagCounter(private val bagRules: List<BagRule>) {

    fun count(bag: String): Int {
        val rule = findRuleFor(bag)

        return countInnerBags(rule)
    }

    private fun countInnerBags(rule: BagRule): Int {
        val directInnerBagCount = rule.storage
                .map { it.count }
                .sum()

        val indirectInnerBagCount = rule.storage
                .map { it.count * count(it.color) }
                .sum()

        return directInnerBagCount + indirectInnerBagCount
    }

    private fun findRuleFor(bag: String) =
            bagRules.find { it.concernsBag == bag } ?: throw IllegalArgumentException("There is no rule for bag $bag")
}
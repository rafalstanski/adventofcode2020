package com.hurricane.kata.adventofcode2020.day7

// 0     1   2    3       4 5      6     7    8 9     10     11
//'light red bags contain 1 bright white bag, 2 muted yellow bags.'
// 0    1    2    3       4  5     6
//plaid cyan bags contain no other bags.

private const val CONCERNS_COLOR_PART1_INDEX = 0
private const val CONCERNS_COLOR_PART2_INDEX = 1
private const val CONCERNS_PARTS_NUMBER = 4
private const val STORAGE_PARTS_NUMBER = 4
private const val CONTAINS_BAGS_COUNT_INDEX = 0
private const val CONTAINS_BAG_COLOR_PART1_INDEX = 1
private const val CONTAINS_BAG_COLOR_PART2_INDEX = 2
private const val NO_BUGS_TO_STORE_PATTERN = "no other bags."

class BagRuleParser {

    fun parse(ruleInstruction: String): BagRule {
        return RuleInstruction(ruleInstruction).toBagRule()
    }

}

class RuleInstruction(private val text: String) {

    fun toBagRule(): BagRule {
        val ruleParts = text.split(' ')

        return BagRule(
                concernsBag = extractConcernsBagColor(ruleParts),
                storage = extractStorage(ruleParts))
    }

    private fun extractConcernsBagColor(ruleParts: List<String>) =
            ruleParts[CONCERNS_COLOR_PART1_INDEX] + ' ' + ruleParts[CONCERNS_COLOR_PART2_INDEX]

    private fun extractStorage(ruleParts: List<String>): List<BagStorage> {
        val allStorageParts = ruleParts.takeLast(ruleParts.size - CONCERNS_PARTS_NUMBER)

        return when {
            cannotStoreBags(allStorageParts) -> listOf()
            else -> extractStorageFromStorageParts(allStorageParts)
        }
    }

    private fun cannotStoreBags(allStorageParts: List<String>): Boolean {
        return allStorageParts.joinToString(separator = " ") == NO_BUGS_TO_STORE_PATTERN
    }

    private fun extractStorageFromStorageParts(allStorageParts: List<String>): List<BagStorage> {
        val storageChunks = allStorageParts.chunked(STORAGE_PARTS_NUMBER)
        return storageChunks.map { storageParts ->
            BagStorage(
                    color = storageParts[CONTAINS_BAG_COLOR_PART1_INDEX] + ' ' + storageParts[CONTAINS_BAG_COLOR_PART2_INDEX],
                    count = storageParts[CONTAINS_BAGS_COUNT_INDEX].toInt()
            )
        }
    }

}
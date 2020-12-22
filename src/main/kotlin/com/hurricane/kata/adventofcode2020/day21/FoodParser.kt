package com.hurricane.kata.adventofcode2020.day21

class FoodParser {

    fun parse(foodString: String): Food {
        val simplifiedFoodString = removeCommasAndBrackets(foodString)
        val (ingredientsPart, allergensPart) = simplifiedFoodString.split("contains")

        return Food(
                ingredients = extractAsSetOfValues(ingredientsPart),
                allergens = extractAsSetOfValues(allergensPart)
        )
    }

    private fun extractAsSetOfValues(valuesSeparatedBySpace: String): Set<String> {
        return "[a-z]+".toRegex()
                .findAll(valuesSeparatedBySpace)
                .map { it.groupValues[0] }
                .toSet()
    }

    private fun removeCommasAndBrackets(foodString: String): String {
        return foodString.removeAllChars(',', '(', ')')
    }

}

private fun String.removeAllChars(vararg charsToRemove: Char): String =
        charsToRemove
                .map { it.toString() }
                .fold(this, { acc, charToRemove -> acc.replace(charToRemove, "") })

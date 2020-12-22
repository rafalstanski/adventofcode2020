package com.hurricane.kata.adventofcode2020.day21

import com.hurricane.kata.adventofcode2020.shared.PuzzleAnswer
import com.hurricane.kata.adventofcode2020.shared.PuzzleInput
import com.hurricane.kata.adventofcode2020.shared.PuzzleSolution

class Day21Part1Runner : PuzzleSolution {

    private val foodParser = FoodParser()
    private val allergensFinder = IngredientsAllergensFinder()

    override fun solve(puzzleInput: PuzzleInput): PuzzleAnswer {
        val foods = toFoods(puzzleInput)
        val ingredients = allergensFinder.find(foods)

        val noAllergensIngredients = ingredients.filter { it.hasNoAllergens() }

        val sum = noAllergensIngredients
                .map { ingredient -> foods.count { it.contains(ingredient) } }
                .sum()

        return PuzzleAnswer(sum)
    }

    private fun toFoods(puzzleInput: PuzzleInput) =
            puzzleInput.entries.map { foodParser.parse(it) }


}
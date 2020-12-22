package com.hurricane.kata.adventofcode2020.day21

class IngredientsAllergensFinder {

    fun find(foods: List<Food>): List<Ingredient> =
            collectIngredientsWithPotentialAllergens(foods)
                    .let { reduceAllergensWhenIngredientNotInFood(it, foods) }
                    .let { eliminateTillAllAreFound(it) }
                    .map { Ingredient(it.name, it.potentialAllergens) }

    private fun collectIngredientsWithPotentialAllergens(foods: List<Food>): List<FoodIngredient> =
            foods.map { it.ingredients }
                    .flatten()
                    .toSet()
                    .map { ingredient ->
                        val allergens = foods
                                .filter { it.ingredients.contains(ingredient) }
                                .map { it.allergens }
                                .flatten()
                                .toSet()

                        FoodIngredient(ingredient, allergens)
                    }

    private fun reduceAllergensWhenIngredientNotInFood(ingredientsToPotentialAllergens: List<FoodIngredient>, foods: List<Food>): List<FoodIngredient> {
        return ingredientsToPotentialAllergens.map { ingredient ->
            val allergensToReduce = foods
                    .filter { !it.ingredients.contains(ingredient.name) }
                    .map { it.allergens }
                    .flatten()

            ingredient.withDeducesAllergens(allergensToReduce)
        }
    }

    private fun eliminateTillAllAreFound(ingredientsToPotentialAllergens: List<FoodIngredient>): List<FoodIngredient> {
        val onlyWithMaxOneAllergen = ingredientsToPotentialAllergens.filter { it.potentialAllergensCount <= 1 }

        if (onlyWithMaxOneAllergen.size == ingredientsToPotentialAllergens.size) return ingredientsToPotentialAllergens

        val reduced = ingredientsToPotentialAllergens.map { current ->
            val allergensToReduce = onlyWithMaxOneAllergen
                    .filter { it != current }
                    .map { it.potentialAllergens }
                    .flatten()

            current.withDeducesAllergens(allergensToReduce)
        }

        return eliminateTillAllAreFound(reduced)
    }
}
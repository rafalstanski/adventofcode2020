package com.hurricane.kata.adventofcode2020.day21

data class Food(
        val ingredients: Set<String>,
        val allergens: Set<String>
) {

    fun contains(ingredient: Ingredient) = ingredients.contains(ingredient.name)
}

data class FoodIngredient(
        val name: String,
        val potentialAllergens: Set<String>
) {
    val potentialAllergensCount = potentialAllergens.size

    fun withReducedAllergens(allergensToReduce: Collection<String>): FoodIngredient =
            this.copy(potentialAllergens = potentialAllergens - allergensToReduce)
}

data class Ingredient(
        val name: String,
        val allergens: Set<String>
) {
    fun hasNoAllergens() = allergens.isEmpty()
}


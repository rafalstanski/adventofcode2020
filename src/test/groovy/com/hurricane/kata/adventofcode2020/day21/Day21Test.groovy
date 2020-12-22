package com.hurricane.kata.adventofcode2020.day21

import spock.lang.Specification
import spock.lang.Unroll

import static com.hurricane.kata.adventofcode2020.day21.FoodAssertions.assertThat

@Unroll
class Day21Test extends Specification {

    def "should read food as list of ingredients and allergens"() {
        when:
            def food = parseFoodString(foodString)

        then:
            assertThat(food)
                    .hasIngredients(expectedIngredients)
                    .hasAllergens(expectedAllergens)

        where:
            foodString                          || expectedIngredients | expectedAllergens
            'abcde (contains dairy)'            || ['abcde']           | ['dairy']
            'abcde efgh (contains dairy)'       || ['abcde', 'efgh']   | ['dairy']
            'abcde (contains dairy, fish)'      || ['abcde']           | ['dairy', 'fish']
            'abcde efgh (contains dairy, fish)' || ['abcde', 'efgh']   | ['dairy', 'fish']
    }

    def "should find ingredient's allergen when foods have disjoint ingredients lists"() {
        given:
            def foods = [
                    food(['i1'], ['a1']),
                    food(['i2'], ['a2']),
            ]

        when:
            def matches = findIngredientsAllergens(foods)

        then:
            matches['i1'] == ['a1'].toSet()
            matches['i2'] == ['a2'].toSet()
    }

    def "should find ingredient's allergen when foods have joint ingredients lists"() {
        given:
            def foods = [
                    food(['i1', 'i2'], ['a1']),
                    food(['i3', 'i2'], ['a2']),
            ]

        when:
            def matches = findIngredientsAllergens(foods)

        then:
            matches['i1'] == ['a1'].toSet()
            matches['i3'] == ['a2'].toSet()
            matches['i2'] == [].toSet()
    }

    def "should find ingredient's allergen when foods have joint allergen lists"() {
        given:
            def foods = [
                    food(['i1', 'i2'], ['a1', 'a2']),
                    food(['i3', 'i1'], ['a1']),
                    food(['i2', 'i4'], ['a2']),
            ]

        when:
            def matches = findIngredientsAllergens(foods)

        then:
            matches['i1'] == ['a1'].toSet()
            matches['i2'] == ['a2'].toSet()
            matches['i3'] == [].toSet()
            matches['i4'] == [].toSet()
    }


    private static Food parseFoodString(String foodString) {
        def foodParser = new FoodParser()

        return foodParser.parse(foodString)
    }

    private static Food food(List<String> ingredients, List<String> allergens) {
        return new Food(ingredients.toSet(), allergens.toSet())
    }

    private static Map<String, Set<String>> findIngredientsAllergens(List<Food> foods) {
        def finder = new IngredientsAllergensFinder()

        return finder.find(foods).collectEntries { [it.name, it.allergens] }
    }
}

class FoodAssertions {

    Food food

    static FoodAssertions assertThat(Food food) {
        return new FoodAssertions(food: food)
    }


    FoodAssertions hasIngredients(Collection<String> expectedIngredients) {
        assert food.ingredients == expectedIngredients.toSet()
        return this
    }

    FoodAssertions hasAllergens(Collection<String> expectedAllergens) {
        assert food.allergens == expectedAllergens.toSet()
        return this
    }

}
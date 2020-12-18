package com.hurricane.kata.adventofcode2020.day18

class EquationCalculator {

    fun calculate(equation: String): Long {
        val notProcessedEquation = createTokenizer(equation)

        return calculateUsing(notProcessedEquation)
    }

    private fun calculateUsing(notProcessedEquation: StringBuilder): Long {
        var result = takeNumber(notProcessedEquation)

        while (notProcessedEquation.isNotEmpty() && noClosingParentheses(notProcessedEquation)) {
            val operation = extractOperation(notProcessedEquation)

            val number2 = takeNumber(notProcessedEquation)

            result = performOperation(result, operation, number2)
        }

        return result
    }

    private fun noClosingParentheses(notProcessedEquation: StringBuilder): Boolean =
            if (notProcessedEquation.startsWith(')')) {
                notProcessedEquation.delete(0, 1)
                false
            } else {
                true
            }

    private fun createTokenizer(equation: String): StringBuilder {
        return StringBuilder(removeSpaces(equation))
    }

    private fun removeSpaces(equation: String) = equation.replace(" ", "")

    private fun takeNumber(notProcessedEquation: StringBuilder): Long =
            if (notProcessedEquation.startsWith('(')) {
                notProcessedEquation.delete(0, 1)

                calculateUsing(notProcessedEquation)
            } else {
                val number = "[0-9]+".toRegex().find(notProcessedEquation)!!.groupValues[0]
                notProcessedEquation.delete(0, number.length)

                number.toLong()
            }

    private fun extractOperation(notProcessedEquation: StringBuilder): String {
        val operation = "\\+|\\*".toRegex().find(notProcessedEquation)!!.value
        notProcessedEquation.delete(0, operation.length)

        return operation
    }

    private fun performOperation(left: Long, operation: String, right: Long): Long {
        return when (operation.trim()) {
            "+" -> left + right
            "*" -> left * right
            else -> throw IllegalArgumentException("Unknown operation: $operation")
        }
    }

}
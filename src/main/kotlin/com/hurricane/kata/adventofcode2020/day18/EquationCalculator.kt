package com.hurricane.kata.adventofcode2020.day18

import java.util.*

class EquationCalculator(private val priorityCalculator: OperationPriorityCalculator) {

    fun calculate(equation: String): Long {
        val notProcessedEquation = createTokenizer(equation)

        return calculateUsing(notProcessedEquation)
    }

    private fun calculateUsing(notProcessedEquation: StringBuilder): Long =
            FlatEquationCalculator(priorityCalculator).calculate(notProcessedEquation)

    private fun createTokenizer(equation: String): StringBuilder {
        return StringBuilder(removeSpaces(equation))
    }

    private fun removeSpaces(equation: String) = equation.replace(" ", "")

}

private class FlatEquationCalculator(private val priorityCalculator: OperationPriorityCalculator) {

    private val stack = Stack<Token>()

    fun calculate(notProcessedEquation: StringBuilder): Long {
        extractAndPushNumber(notProcessedEquation)

        var previousOperationPriority = Int.MIN_VALUE

        while (notProcessedEquation.isNotEmpty() && noClosingParentheses(notProcessedEquation)) {
            val operation = extractOperation(notProcessedEquation)
            if (previousOperationPriority > priorityOf(operation)) {
                calculateStackAndPushValue()
            }
            pushToken(operation)
            previousOperationPriority = priorityOf(operation)

            extractAndPushNumber(notProcessedEquation)
        }

        return calculateStackAndPushValue().value
    }

    private fun priorityOf(operation: OperationToken): Int =
            priorityCalculator.calculate(operation)

    private fun noClosingParentheses(notProcessedEquation: StringBuilder): Boolean =
            if (notProcessedEquation.startsWith(')')) {
                notProcessedEquation.delete(0, 1)
                false
            } else {
                true
            }

    private fun extractAndPushNumber(notProcessedEquation: StringBuilder) {
        if (notProcessedEquation.startsWith('(')) {
            notProcessedEquation.delete(0, 1)

            val innerValue = calculateInnerValue(notProcessedEquation)

            pushToken(NumberToken(innerValue))
        } else {
            val number = "[0-9]+".toRegex().find(notProcessedEquation)!!.groupValues[0]
            notProcessedEquation.delete(0, number.length)

            pushToken(NumberToken(number.toLong()))
        }
    }

    private fun calculateInnerValue(notProcessedEquation: StringBuilder) =
            FlatEquationCalculator(priorityCalculator).calculate(notProcessedEquation)

    private fun extractOperation(notProcessedEquation: StringBuilder): OperationToken {
        val operation = "\\+|\\*".toRegex().find(notProcessedEquation)!!.value
        notProcessedEquation.delete(0, operation.length)

        return OperationToken(operation)
    }

    private fun calculateStackAndPushValue() : NumberToken {
        val value = calculateStack()

        return pushToken(NumberToken(value))
    }

    private fun <T : Token> pushToken(token: T): T =
            stack.push(token) as T

    private fun calculateStack(): Long {
        var right = popAsNumber().value

        while (stack.isNotEmpty()) {
            val operation = popAsOperation().symbol
            val left = popAsNumber().value

            right = performOperation(left, operation, right)
        }

        return right
    }

    private fun popAsNumber(): NumberToken =
            when (val token = stack.pop()) {
                is NumberToken -> token
                else -> throw IllegalStateException("expected number rather then $token")
            }

    private fun popAsOperation(): OperationToken =
            when (val token = stack.pop()) {
                is OperationToken -> token
                else -> throw IllegalStateException("expected operation rather then $token")
            }

    private fun performOperation(left: Long, operation: String, right: Long): Long {
        return when (operation.trim()) {
            "+" -> left + right
            "*" -> left * right
            else -> throw IllegalArgumentException("Unknown operation: $operation")
        }
    }

}

sealed class Token

class NumberToken(val value: Long) : Token()

class OperationToken(val symbol: String) : Token()
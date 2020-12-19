package com.hurricane.kata.adventofcode2020.day18

import java.util.*

class EquationCalculator {

    fun calculate(equation: String): Long {
        val notProcessedEquation = createTokenizer(equation)

        return calculateUsing(notProcessedEquation)
    }

    private fun calculateUsing(notProcessedEquation: StringBuilder): Long =
            FlatEquationCalculator().calculate(notProcessedEquation)

    private fun createTokenizer(equation: String): StringBuilder {
        return StringBuilder(removeSpaces(equation))
    }

    private fun removeSpaces(equation: String) = equation.replace(" ", "")

}

private class FlatEquationCalculator {

    private val stack = Stack<Token>()

    fun calculate(notProcessedEquation: StringBuilder): Long {
        pushNumber(notProcessedEquation)

        while (notProcessedEquation.isNotEmpty() && noClosingParentheses(notProcessedEquation)) {
            pushOperation(notProcessedEquation)
            pushNumber(notProcessedEquation)

            calculateStackAndPushValue()
        }

        return popAsNumber().value
    }

    private fun noClosingParentheses(notProcessedEquation: StringBuilder): Boolean =
            if (notProcessedEquation.startsWith(')')) {
                notProcessedEquation.delete(0, 1)
                false
            } else {
                true
            }

    private fun pushNumber(notProcessedEquation: StringBuilder) {
        if (notProcessedEquation.startsWith('(')) {
            notProcessedEquation.delete(0, 1)

            val innerValue = FlatEquationCalculator().calculate(notProcessedEquation)

            stack.push(NumberToken(innerValue))
        } else {
            val number = "[0-9]+".toRegex().find(notProcessedEquation)!!.groupValues[0]
            notProcessedEquation.delete(0, number.length)

            stack.push(NumberToken(number.toLong()))
        }
    }

    private fun pushOperation(notProcessedEquation: StringBuilder) {
        val operation = "\\+|\\*".toRegex().find(notProcessedEquation)!!.value
        notProcessedEquation.delete(0, operation.length)

        stack.push(OperationToken(operation))
    }

    private fun calculateStackAndPushValue() {
        val value = calculateStack()

        stack.push(NumberToken(value))
    }

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
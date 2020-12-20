package com.hurricane.kata.adventofcode2020.day18

interface OperationPriorityCalculator {

    fun calculate(operationToken: OperationToken): Int
}

class SamePriorityCalculator : OperationPriorityCalculator {

    override fun calculate(operationToken: OperationToken): Int = 0
}

class DifferentPrioritiesCalculator : OperationPriorityCalculator {

    override fun calculate(operationToken: OperationToken): Int =
            when (operationToken.symbol) {
                "+" -> 100
                "*" -> 50
                else -> 0
            }

}
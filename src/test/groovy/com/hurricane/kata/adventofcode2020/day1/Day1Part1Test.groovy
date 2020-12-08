package com.hurricane.kata.adventofcode2020.day1

import com.hurricane.kata.adventofcode2020.day1.TwoNumberSolution
import com.hurricane.kata.adventofcode2020.day1.TwoNumberSolutionFinder
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@Unroll
class Day1Part1Test extends Specification {

    @Subject
    def solutionFinder = new TwoNumberSolutionFinder()

    def "should find solution within list of two numbers"() {
        when:
            def solution = findSolution(source)

        then:
            solution == expectedSolution

        where:
            source      || expectedSolution
            [0, 2020]   || new TwoNumberSolution(0, 2020)
            [520, 1500] || new TwoNumberSolution(520, 1500)
            [2020, 0]   || new TwoNumberSolution(2020, 0)
    }

    def "should find solution within list of multiple numbers"() {
        given:
            def source = [
                    200,
                    800,
                    400,
                    1620,
                    50
            ]

        when:
            def solution = findSolution(source)

        then:
            solution == new TwoNumberSolution(400, 1620)
    }

    def "should be able to calculate result from solution"() {
        when:
            def solution = new TwoNumberSolution(first, second)

        then:
            solution.result == expectedResult

        where:
            first | second || expectedResult
            0     | 2020   || 0
            10    | 2010   || 20100
            2020  | 0      || 0
    }

    def "should not find solution when no numbers"() {
        when:
            def solution = findSolution([])

        then:
            solution == null
    }

    def "should not find solution when one numbers"() {
        when:
            def solution = findSolution([2020])

        then:
            solution == null
    }

    def "should not find solution when there is none"() {
        when:
            def solution = findSolution([1, 2, 3, 4, 5])

        then:
            solution == null
    }

    TwoNumberSolution findSolution(List<Integer> source) {
        solutionFinder.find(source)
    }

}
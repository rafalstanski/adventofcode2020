package com.hurricane.kata.adventofcode2020.day1

import com.hurricane.kata.adventofcode2020.day1.ThreeNumberSolution
import com.hurricane.kata.adventofcode2020.day1.ThreeNumberSolutionFinder
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll


@Unroll
class Day1Part2Test extends Specification {

    @Subject
    def solutionFinder = new ThreeNumberSolutionFinder()

    def "should resolve task for the day"() {
        given:
            def source = [
                    1721,
                    979,
                    366,
                    299,
                    675,
                    1456
            ]

        when:
            def solution = findSolution(source)

        then:
            solution == new ThreeNumberSolution(979, 366, 675)
    }

    def "should find solution within list of three numbers"() {
        when:
            def solution = findSolution(source)

        then:
            solution == expectedSolution

        where:
            source           || expectedSolution
            [1000, 20, 1000] || new ThreeNumberSolution(1000, 20, 1000)
            [0, 2020, 0]     || new ThreeNumberSolution(0, 2020, 0)
            [2020, 0, 0]     || new ThreeNumberSolution(2020, 0, 0)
            [0, 2020, 0]     || new ThreeNumberSolution(0, 2020, 0)
    }

    def "should find solution within list of multiple numbers"() {
        given:
            def source = [
                    100,
                    300,
                    200,
                    1520,
                    50
            ]

        when:
            def solution = findSolution(source)

        then:
            solution == new ThreeNumberSolution(300, 200, 1520)
    }

    def "should be able to calculate result from solution"() {
        when:
            def solution = new ThreeNumberSolution(first, second, third)

        then:
            solution.result == expectedResult

        where:
            first | second | third || expectedResult
            10    | 2000   | 10    || 200000
            979   | 366    | 675   || 241861950
            0     | 2020   | 0     || 0
            2020  | 0      | 0     || 0
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

    def "should not find solution when two numbers"() {
        when:
            def solution = findSolution([2020, 0])

        then:
            solution == null
    }

    def "should not find solution when there is none"() {
        when:
            def solution = findSolution([1, 2, 3, 4, 5])

        then:
            solution == null
    }

    ThreeNumberSolution findSolution(List<Integer> source) {
        solutionFinder.find(source)
    }

}
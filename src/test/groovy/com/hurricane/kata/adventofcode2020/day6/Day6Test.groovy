package com.hurricane.kata.adventofcode2020.day6

import com.hurricane.kata.adventofcode2020.day6.DoneByAllAnswersExtractor
import com.hurricane.kata.adventofcode2020.day6.UniqueAnswersExtractor
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class Day6Test extends Specification {

    def "should detect groups"() {
        given:
            def answersLines = [
                    "abc",
                    "",
                    "a", "a",
                    "",
                    "a", "ab", "abc"
            ]

        when:
            def answersGroups = detectGroups(answersLines)

        then:
            countGroups(answersGroups) == 3
        and:
            answersGroups[0] == ["abc"]
            answersGroups[1] == ["a", "a"]
            answersGroups[2] == ["a", "ab", "abc"]
    }

    def "should detect single group when no empty lines"() {
        given:
            def singleLinesOfAnswers = ["abc"]

        when:
            def answersGroups = detectGroups(singleLinesOfAnswers)

        then:
            countGroups(answersGroups) == 1
            answersGroups[0] == ["abc"]
    }

    def "should extract unique answers for group"() {
        expect:
            extractUniqueAnswers(answers) == expectedAnswers

        where:
            answers         || expectedAnswers
            ["a"]           || ['a']
            ["abc"]         || ['a', 'b', 'c']
            ["a", "b", "c"] || ['a', 'b', 'c']
            ["aaaa"]        || ['a']
            ["b", "b", "b"] || ['b']
    }

    def "should count unique answers for group"() {
        expect:
            countUniqueAnswers(answers) == expectedCount

        where:
            answers               || expectedCount
            ["a"]                 || 1
            ["abc"]               || 3
            ["a", "b", "c"]       || 3
            ["aaaa"]              || 1
            ["b", "b", "b"]       || 1
            ["abc", "bcd", "cde"] || 5
    }

    def "should extract answer done by all group members"() {
        expect:
            extractAnswersDoneByAll(answers) == expectedAnswers

        where:
            answers                  || expectedAnswers
            ["a"]                    || ['a']
            ["abc"]                  || ['a', 'b', 'c']
            ["a", "b", "c"]          || []
            ["aaaa"]                 || ['a']
            ["b", "b", "b"]          || ['b']
            ["abcf", "abdf", "abef"] || ['a', 'b', 'f']
    }

    def "should count answer done by all group members"() {
        expect:
            countAnswersDoneByAll(answers) == expectedCount

        where:
            answers                  || expectedCount
            ["a"]                    || 1
            ["abc"]                  || 3
            ["a", "b", "c"]          || 0
            ["aaaa"]                 || 1
            ["b", "b", "b"]          || 1
            ["abcf", "abdf", "abef"] || 3
    }

    private static List detectGroups(List<String> answersLines) {
        def parser = new AnswersGroupsParser()

        parser.parse(answersLines)
    }

    private static int countGroups(List groups) {
        groups.size()
    }

    private static List<String> extractUniqueAnswers(List<String> groupAnswersLines) {
        def extractor = new UniqueAnswersExtractor()

        extractor.extract(groupAnswersLines).collect { it.toString() }
    }

    private static int countUniqueAnswers(List<String> groupAnswersLines) {
        def counter = new AnswersCounter(new UniqueAnswersExtractor())

        counter.count(groupAnswersLines)
    }

    private static List<String> extractAnswersDoneByAll(List<String> groupAnswersLines) {
        def extractor = new DoneByAllAnswersExtractor()

        extractor.extract(groupAnswersLines).collect { it.toString() }
    }

    private static int countAnswersDoneByAll(List<String> groupAnswersLines) {
        def counter = new AnswersCounter(new DoneByAllAnswersExtractor())

        counter.count(groupAnswersLines)
    }
}

package com.hurricane.kata.adventofcode2020.day2

import com.hurricane.kata.adventofcode2020.day2.PasswordDefinitionParser
import com.hurricane.kata.adventofcode2020.day2.PasswordIndexValidator
import com.hurricane.kata.adventofcode2020.day2.PasswordRangeValidator
import com.hurricane.kata.adventofcode2020.day2.PasswordValidator
import com.hurricane.kata.adventofcode2020.day2.ValidPasswordCounter
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ValidPasswordCounterTest extends Specification {

    def "should count valid passwords using range validator"() {
        given:
            def entries = [
                    '1-3 a: abcde',
                    '1-3 b: cdefg',
                    '2-9 c: ccccccccc'
            ]

        expect:
            countValidEntries(entries, new PasswordRangeValidator()) == 2
    }

    def "should count valid passwords using index validator"() {
        given:
            def entries = [
                    '1-3 a: abcde',
                    '1-3 b: cdefg',
                    '2-9 c: ccccccccc'
            ]

        expect:
            countValidEntries(entries, new PasswordIndexValidator()) == 1
    }

    private static int countValidEntries(List<String> entries, PasswordValidator validator) {
        def counter = new ValidPasswordCounter(new PasswordDefinitionParser(), validator)
        counter.count(entries)
    }
}

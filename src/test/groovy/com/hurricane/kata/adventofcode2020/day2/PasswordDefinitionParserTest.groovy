package com.hurricane.kata.adventofcode2020.day2

import com.hurricane.kata.adventofcode2020.day2.PasswordDefinition
import com.hurricane.kata.adventofcode2020.day2.PasswordDefinitionParser
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class PasswordDefinitionParserTest extends Specification {

    private def parser = new PasswordDefinitionParser()

    def "should parse string entry as password definition"() {
        when:
            def passwordDefinition = parse(entry)

        then:
            passwordDefinition.requiredLetter == (expectedLetter as char)
            passwordDefinition.firstRestriction == expectedMin
            passwordDefinition.secondRestriction == expectedMax
            passwordDefinition.password == expectedPassword

        where:
            entry              || expectedLetter | expectedMin | expectedMax | expectedPassword
            '1-3 a: abcde'     || 'a'            | 1           | 3           | 'abcde'
            '1-3 b: cdefg'     || 'b'            | 1           | 3           | 'cdefg'
            '2-9 c: ccccccccc' || 'c'            | 2           | 9           | 'ccccccccc'
    }

    private PasswordDefinition parse(String entryString) {
        parser.parse(entryString)
    }

}

package com.hurricane.kata.adventofcode2020.day2

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class PasswordIndexValidatorTest extends Specification {

    private def validator = new PasswordIndexValidator()

    def "should validate password that meets given policy"() {
        given:
            def passwordDefinition = parse(entry)

        expect:
            validate(passwordDefinition) == isValid

        where:
            entry              || isValid
            '1-3 a: abcde'     || true
            '1-3 b: cdefg'     || false
            '2-9 c: ccccccccc' || false
    }

    private PasswordDefinition parse(String entryString) {
        new PasswordDefinitionParser().parse(entryString)
    }

    private boolean validate(PasswordDefinition passwordDefinition) {
        validator.validate(passwordDefinition)
    }
}

@Unroll
class PasswordRangeValidatorTest extends Specification {

    private def validator = new PasswordRangeValidator()

    def "should validate password that meets given policy"() {
        given:
            def passwordDefinition = parse(entry)

        expect:
            validate(passwordDefinition) == isValid

        where:
            entry              || isValid
            '1-3 a: abcde'     || true
            '1-3 b: cdefg'     || false
            '2-9 c: ccccccccc' || true
    }

    private PasswordDefinition parse(String entryString) {
        new PasswordDefinitionParser().parse(entryString)
    }

    private boolean validate(PasswordDefinition passwordDefinition) {
        validator.validate(passwordDefinition)
    }
}
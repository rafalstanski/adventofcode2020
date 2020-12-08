package com.hurricane.kata.adventofcode2020.day4

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.hurricane.kata.adventofcode2020.day4.PassportFieldType.BYR
import static com.hurricane.kata.adventofcode2020.day4.PassportFieldType.CID
import static com.hurricane.kata.adventofcode2020.day4.PassportFieldType.ECL
import static com.hurricane.kata.adventofcode2020.day4.PassportFieldType.EYR
import static com.hurricane.kata.adventofcode2020.day4.PassportFieldType.HCL
import static com.hurricane.kata.adventofcode2020.day4.PassportFieldType.HGT
import static com.hurricane.kata.adventofcode2020.day4.PassportFieldType.IYR
import static com.hurricane.kata.adventofcode2020.day4.PassportFieldType.PID

@Unroll
class Day4Part1Test extends Specification {

    @Subject
    private def passportValidator = new RequiredFieldsPassportValidator()

    def "should determine if passport is valid"() {
        when:
            def passport = createPassport(passportFields)

        then:
            passportIsValid(passport) == expectIfValid

        where:
            passportFields                           || expectIfValid
            [BYR, IYR, EYR, HGT, HCL, ECL, PID, CID] || true
            [BYR, IYR, EYR, HGT, HCL, ECL, PID]      || true
            [BYR, IYR, EYR, HGT, HCL, ECL, CID]      || false
            [BYR, IYR, EYR, HGT]                     || false
    }

    def "should count valid passports"() {
        given:
            def inputLines = [
                    "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd",
                    "byr:1937 iyr:2017 cid:147 hgt:183cm",
                    "",
                    "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884",
                    "hcl:#cfa07d byr:1929",
                    "",
                    "hcl:#ae17e1 iyr:2013",
                    "eyr:2024",
                    "ecl:brn pid:760753108 byr:1931",
                    "hgt:179cm",
                    "",
                    "hcl:#cfa07d eyr:2025 pid:166559648",
                    "iyr:2011 ecl:brn hgt:59in",
            ]

        expect:
            parseAndCountValidPassports(inputLines) == 2
    }

    private static Passport createPassport(List<PassportFieldType> passportFieldTypes) {
        def passportFields = passportFieldTypes.collect { new PassportField(it, "dummy") }

        new Passport(passportFields)
    }

    private boolean passportIsValid(Passport passport) {
        passportValidator.valid(passport)
    }

    private int parseAndCountValidPassports(List<String> inputLines) {
        def validPassportCounter = new ValidPassportCounter(new PassportsParser(), passportValidator)

        validPassportCounter.count(inputLines)
    }
}

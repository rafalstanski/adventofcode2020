package com.hurricane.kata.adventofcode2020.day4

import spock.lang.Specification
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
class Day4CommonTest extends Specification {

    def "should treat multiple lines without empty one as one passport"() {
        given:
            def inputLines = [
                    "byr:1937",
                    "eyr:2023",
                    "ecl:amb",
            ]

        when:
            def passports = extractPassports(inputLines)

        then:
            countPassports(passports) == 1
    }

    def "should be able to find all passports"() {
        given:
            def inputLines = [
                    "byr:1937",
                    "",
                    "eyr:2023",
                    "ecl:amb",
                    "",
                    "hgt:179cm",
            ]

        when:
            def passports = extractPassports(inputLines)

        then:
            countPassports(passports) == 3
    }

    def "should parse passport's fields"() {
        when:
            def passports = extractPassports(inputLines)

        then:
            countPassports(passports) == 1
        and:
            passportFieldsTypes(passports[0]) == expectedFiels

        where:
            inputLines                                       || expectedFiels
            ["byr:1937"]                                     || [BYR]
            ["iyr:2023", "eyr:2023", "hgt:amb"]              || [IYR, EYR, HGT]
            ["hcl:#cfa07d ecl:brn"]                          || [HCL, ECL]
            ["hcl:#cfa07d ecl:brn", "pid:760753108 cid:147"] || [HCL, ECL, PID, CID]
    }

    def "should parse passport's fields for multiple passports"() {
        given:
            def inputLines = [
                    "ecl:gry pid:860033327",
                    "byr:1937",
                    "",
                    "iyr:2013",
                    "hcl:#cfa07d byr:1929",
                    "",
                    "hcl:#ae17e1 iyr:2013",
                    "eyr:2024",
                    "ecl:brn",
                    "hgt:179cm",
            ]

        when:
            def passports = extractPassports(inputLines)

        then:
            countPassports(passports) == 3
        and:
            passportFieldsTypes(passports[0]) == [ECL, PID, BYR]
            passportFieldsTypes(passports[1]) == [IYR, HCL, BYR]
            passportFieldsTypes(passports[2]) == [HCL, IYR, EYR, ECL, HGT]
    }

    private static List<Passport> extractPassports(List<String> inputLines) {
        def passportsParser = new PassportsParser()

        passportsParser.parse(inputLines)
    }

    private static int countPassports(List<Passport> passports) {
        passports.size()
    }

    private static List<PassportFieldType> passportFieldsTypes(Passport passport) {
        passport.fields.collect {it.type}
    }

}

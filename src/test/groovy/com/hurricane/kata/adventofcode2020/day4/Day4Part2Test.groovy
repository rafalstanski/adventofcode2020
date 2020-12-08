package com.hurricane.kata.adventofcode2020.day4

import groovy.transform.NamedParam
import groovy.transform.NamedVariant
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
class Day4Part2Test extends Specification {

    @Subject
    private def passportValidator = new RequiredAndValidFieldsPassportValidator(
            new RequiredFieldsPassportValidator(), new PassportFieldValidator())

    @Subject
    private def passportFieldValidator = new PassportFieldValidator()

    def "should extract field's value"() {
        when:
            def passports = extractPassports(inputLines)

        then:
            passportFields(passports[0]) == expectedFiels

        where:
            inputLines                                       || expectedFiels
            ["byr:1937"]                                     || [BYR: '1937']
            ["iyr:2023", "eyr:2023", "hgt:amb"]              || [IYR: '2023', EYR: '2023', HGT: 'amb']
            ["hcl:#cfa07d ecl:brn"]                          || [HCL: '#cfa07d', ECL: 'brn']
            ["hcl:#cfa07d ecl:brn", "pid:760753108 cid:147"] || [HCL: '#cfa07d', ECL: 'brn', PID: '760753108', CID: '147']
    }

    def "should validate 'byr' value: four digits; at least 1920 and at most 2002"() {
        given:
            def field = createPassportField(type: BYR, value: value)

        expect:
            fieldIsValid(field) == isValid

        where:
            value   || isValid
            '100'   || false
            '1000'  || false
            '1919'  || false
            '1920'  || true
            '1999'  || true
            '2002'  || true
            '2003'  || false
            '2050'  || false
            'okok'  || false
            'error' || false
    }

    def "should validate 'iyr' value: four digits; at least 2010 and at most 2020"() {
        given:
            def field = createPassportField(type: IYR, value: value)

        expect:
            fieldIsValid(field) == isValid

        where:
            value   || isValid
            '100'   || false
            '1000'  || false
            '2009'  || false
            '2010'  || true
            '2015'  || true
            '2020'  || true
            '2021'  || false
            '2050'  || false
            'okok'  || false
            'error' || false
    }

    def "should validate 'eyr' value: four digits; at least 2020 and at most 2030"() {
        given:
            def field = createPassportField(type: EYR, value: value)

        expect:
            fieldIsValid(field) == isValid

        where:
            value   || isValid
            '100'   || false
            '1000'  || false
            '2019'  || false
            '2020'  || true
            '2025'  || true
            '2030'  || true
            '2031'  || false
            '2050'  || false
            'okok'  || false
            'error' || false
    }

    def "should validate 'hgt' value: If cm, the number must be at least 150 and at most 193"() {
        given:
            def field = createPassportField(type: HGT, value: value)

        expect:
            fieldIsValid(field) == isValid

        where:
            value   || isValid
            '151ab' || false
            '192ab' || false
            '100cm' || false
            '149cm' || false
            '150cm' || true
            '180cm' || true
            '193cm' || true
            '194cm' || false
            '400cm' || false
            'XXXcm' || false
    }

    def "should validate 'hgt' value: If in, the number must be at least 59 and at most 76"() {
        given:
            def field = createPassportField(type: HGT, value: value)

        expect:
            fieldIsValid(field) == isValid

        where:
            value   || isValid
            '60ab'  || false
            '75ab'  || false
            '30in'  || false
            '58in'  || false
            '59in'  || true
            '68in'  || true
            '76in'  || true
            '77in'  || false
            '100in' || false
            'XXXin' || false
    }

    def "should validate 'hcl' value: a # followed by exactly six characters 0-9 or a-f"() {
        given:
            def field = createPassportField(type: HCL, value: value)

        expect:
            fieldIsValid(field) == isValid

        where:
            value      || isValid
            '#ab'      || false
            '#123'     || false
            '#abcd'    || false
            '#12345'   || false
            '#123456'  || true
            '#abcdef'  || true
            '#123abc'  || true
            '#123fgh'  || false
            '#1234561' || false
            '#abcdefa' || false
            '#error1'  || false
            '123456'   || false
            '1234561'  || false
            'abcdef'   || false
            'abcdefa'  || false
    }

    def "should validate 'ecl' value: exactly one of: amb blu brn gry grn hzl oth"() {
        given:
            def field = createPassportField(type: ECL, value: value)

        expect:
            fieldIsValid(field) == isValid

        where:
            value    || isValid
            'a'      || false
            'ab'     || false
            'aaa'    || false
            'amz'    || false
            'amb'    || true
            'blu'    || true
            'brn'    || true
            'gry'    || true
            'grn'    || true
            'hzl'    || true
            'oth'    || true
            'oth1'   || false
            'abcdef' || false
    }

    def "should validate 'pid' value: a nine-digit number, including leading zeroes."() {
        given:
            def field = createPassportField(type: PID, value: value)

        expect:
            fieldIsValid(field) == isValid

        where:
            value        || isValid
            '1'          || false
            'abcd'       || false
            '12345678'   || false
            '123456789'  || true
            '000000000'  || true
            '000000001'  || true
            '999999999'  || true
            '000012345'  || true
            '12345678a'  || false
            'aaaaaaaaa'  || false
            '1234567890' || false
    }

    def "should validate 'cid' value: ignored, missing or not."() {
        given:
            def field = createPassportField(type: CID, value: value)

        expect:
            fieldIsValid(field) == isValid

        where:
            value       || isValid
            '2002'      || true
            '60in'      || true
            '190cm'     || true
            '#123abc'   || true
            'brn'       || true
            '000000001' || true
            'whatever'  || true
            'error'     || true
    }

    def "should determine if passport is valid"() {
        given:
            def passports = extractPassports([passportDef])

        expect:
            passportIsValid(passports[0])

        where:
            passportDef << [
                    "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980 hcl:#623a2f",
                    "eyr:2029 ecl:blu cid:129 byr:1989 iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm",
                    "hcl:#888785 hgt:164cm byr:2001 iyr:2015 cid:88 pid:545766238 ecl:hzl eyr:2022",
                    "iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719"]
    }

    def "should determine if passport is invalid"() {
        given:
            def passports = extractPassports([passportDef])

        expect:
            passportIsInvalid(passports[0])

        where:
            passportDef << ["eyr:1972 cid:100 hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926",
                            "iyr:2019 hcl:#602927 eyr:1967 hgt:170cm ecl:grn pid:012533040 byr:1946",
                            "hcl:dab227 iyr:2012 ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277",
                            "hgt:59cm ecl:zzz eyr:2038 hcl:74454a iyr:2023 pid:3556412378 byr:2007"]
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

    private static List<Passport> extractPassports(List<String> inputLines) {
        def passportsParser = new PassportsParser()

        passportsParser.parse(inputLines)
    }

    private static Map<String, String> passportFields(Passport passport) {
        passport.fields.collectEntries { [it.type.toString(), it.value] }
    }

    private boolean passportIsValid(Passport passport) {
        passportValidator.valid(passport)
    }

    private boolean passportIsInvalid(Passport passport) {
        !passportIsValid(passport)
    }

    @NamedVariant
    private static PassportField createPassportField(@NamedParam PassportFieldType type, @NamedParam String value) {
        new PassportField(type, value)
    }

    private boolean fieldIsValid(PassportField passportField) {
        passportFieldValidator.validateField(passportField)
    }

    private int parseAndCountValidPassports(List<String> inputLines) {
        def validPassportCounter = new ValidPassportCounter(new PassportsParser(), passportValidator)

        validPassportCounter.count(inputLines)
    }

}

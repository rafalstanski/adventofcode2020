package com.hurricane.kata.adventofcode2020.day7


import spock.lang.Specification
import spock.lang.Unroll

import static RuleAssertions.assertThat

@Unroll
class Day7Test extends Specification {

    def "should parse single rule"() {
        when:
            def rule = parseRuleInstruction(ruleInstruction)

        then:
            assertThat(rule)
                    .concernsBag(bagColor)
                    .containsBagsInNumber(bagStorage)

        where:
            ruleInstruction                                                      || bagColor       | bagStorage
            'light red bags contain 1 bright white bag, 2 muted yellow bags.'    || 'light red'    | ['bright white': 1, 'muted yellow': 2]
            'dark orange bags contain 3 bright white bags, 4 muted yellow bags.' || 'dark orange'  | ['bright white': 3, 'muted yellow': 4]
            'bright white bags contain 1 shiny gold bag.'                        || 'bright white' | ['shiny gold': 1]
    }

    def "should parse correctly when bag not contains any bug"() {
        when:
            def rule = parseRuleInstruction('plaid cyan bags contain no other bags.')

        then:
            assertThat(rule)
                    .concernsBag('plaid cyan')
                    .cannotContainAnyBug()
    }

    def "should indicate if bag can contain specified bag"() {
        expect:
            canStoreBag(bagToStore, rule as BagRule) == canContain

        where:
            rule                                                    | bagToStore     || canContain
            rule('light red', ['shiny gold': 1])                    | 'shiny gold'   || true
            rule('light red', ['shiny gold': 1])                    | 'light red'    || false
            rule('light red', ['shiny gold': 1, 'bright white': 1]) | 'shiny gold'   || true
            rule('light red', ['shiny gold': 1, 'bright white': 1]) | 'bright white' || true
            rule('light red', ['shiny gold': 1, 'bright white': 1]) | 'muted yellow' || false
    }

    def "should count number of direct bags that can contain selected bag"() {
        given:
            def rules = [
                    rule('light red', ['shiny gold': 1]),
                    rule('dark orange', ['light black': 2]),
                    rule('bright white', ['shiny gold': 3]),
                    rule('dark olive', ['muted yellow': 4]),
                    rule('faded blue', ['muted yellow': 5]),
                    rule('vibrant plum', ['shiny gold': 6]),
            ]

        expect:
            countBagsThatCanStore(bagToContain, rules) == numberOfBagThatCanStore

        where:
            bagToContain      || numberOfBagThatCanStore
            'shiny gold'      || 3
            'light black'     || 1
            'muted yellow'    || 2
            'nonexists color' || 0

    }

    def "should count number of direct and indirect bags that can contain selected bag"() {
        given:
            def rules = [
                    rule('light red', ['shiny gold': 1]),
                    rule('dark orange', ['light red': 2]),
                    rule('bright white', ['dark orange': 3]),
                    rule('dark olive', ['bright white': 4]),
                    rule('faded blue', ['dark olive': 5]),
            ]

        expect:
            countBagsThatCanStore(bagToContain, rules) == numberOfBagThatCanStore

        where:
            bagToContain   || numberOfBagThatCanStore
            'shiny gold'   || 5
            'light red'    || 4
            'dark orange'  || 3
            'bright white' || 2
            'dark olive'   || 1
    }

    def "should count unique number of direct and indirect bags that can contain selected bag"() {
        given:
            def rules = [
                    rule('light red', ['shiny gold': 1]),
                    rule('dark orange', ['light red': 2]),
                    rule('bright white', ['light red': 3]),
                    rule('dark olive', ['dark orange': 4, 'bright white': 4]),
                    rule('faded blue', ['dark olive': 5, 'bright white': 1]),
            ]

        expect:
            countBagsThatCanStore(bagToContain, rules) == numberOfBagThatCanStore

        where:
            bagToContain   || numberOfBagThatCanStore
            'shiny gold'   || 5
            'light red'    || 4
            'dark orange'  || 2
            'bright white' || 2
            'dark olive'   || 1
    }

    def "should count number of bugs (direct and recursively inner) than need to be contained inside a bug"() {
        given:
            def rules = [
                    rule('light red', ['shiny gold': 1]),
                    rule('shiny gold', ['dark orange': 2]),
                    rule('dark orange', ['bright white': 3]),
                    rule('bright white', ['dark olive': 4, 'muted yellow': 4]),
                    rule('muted yellow', ['dark olive': 5, 'vibrant plum': 1]),
                    rule('dark olive', [:]),
                    rule('vibrant plum', [:]),
            ]

        expect:
            countNumberOfContainedBugs('bright white', rules) == 4 + 4 + 4 * (5 + 1)

        where:
            bag            || expectedInnerBagsCount
            'dark olive' || 0
            'muted yellow' || 5 + 1
            'bright white' || 4 + 4 + 4 * (5 + 1)
            'dark orange'  || 3 + 3 * (4 + 4 + 4 * (5 + 1))
            'shiny gold'   || 2 + 2 * (3 + 3 * (4 + 4 + 4 * (5 + 1)))
            'light red'    || 1 + 1 * (2 + 2 * (3 + 3 * (4 + 4 + 4 * (5 + 1))))
    }

    private static BagRule parseRuleInstruction(String ruleInstruction) {
        def bagRuleParser = new BagRuleParser()

        bagRuleParser.parse(ruleInstruction)
    }

    private static BagRule rule(String concernsBag, Map<String, Integer> bagsInNumber) {
        def storage = bagsInNumber.collect { new BagStorage(it.key, it.value) }

        new BagRule(concernsBag, storage)
    }

    private static boolean canStoreBag(String bagToContain, BagRule bagRule) {
        bagRule.canStore(bagToContain)
    }

    private static int countBagsThatCanStore(String bagToContain, List<BagRule> rules) {
        def bagStorageCounter = new BagStorageCounter(rules)

        bagStorageCounter.count(bagToContain)
    }

    private static int countNumberOfContainedBugs(String bug, List<BagRule> rules) {
        def innerBagCounter = new InnerBagCounter(rules)

        innerBagCounter.count(bug)
    }
}

class RuleAssertions {

    BagRule rule

    static RuleAssertions assertThat(BagRule rule) {
        new RuleAssertions(rule: rule)
    }

    RuleAssertions concernsBag(String bagColor) {
        assert rule.concernsBag == bagColor
        this
    }

    RuleAssertions cannotContainAnyBug() {
        assert rule.storage.size() == 0
        this
    }

    RuleAssertions containsBagInNumber(String bagColor, int numberOfBags) {
        assert rule.storage.find { it.color == bagColor }.count == numberOfBags
        this
    }

    RuleAssertions containsBagsInNumber(Map<String, Integer> bagsInNumber) {
        bagsInNumber.forEach { color, number -> containsBagInNumber(color, number) }
        this
    }

}

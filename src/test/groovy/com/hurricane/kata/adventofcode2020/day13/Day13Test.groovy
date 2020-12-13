package com.hurricane.kata.adventofcode2020.day13

import com.hurricane.kata.adventofcode2020.Puzzle
import com.hurricane.kata.adventofcode2020.PuzzleIntTestSupport
import spock.lang.Unroll

@Unroll
class Day13Test extends PuzzleIntTestSupport {

    @Override
    protected Puzzle puzzlePart1() {
        new Puzzle(
                inputLocation: 'test_entries_day13.txt',
                solution: new Day13Part1Runner(),
                expectedAnswer: 295
        )
    }

    @Override
    protected Puzzle puzzlePart2() {
        new Puzzle(
                inputLocation: 'test_entries_day13.txt',
                solution: new Day13Part2Runner(),
                expectedAnswer: 26
        )
    }

    def "should calculate earliest depart time based on arrival time"() {
        when:
            def departTime = calculateEarliestDepartTime(arrivalTime, bus)

        then:
            departTime.value == expectedEarliestDepartTime
            departTime.waitTime == expectedWaitTime

        where:
            arrivalTime | bus || expectedEarliestDepartTime | expectedWaitTime
            1           | 7   || 7                          | 6
            5           | 7   || 7                          | 2
            8           | 7   || 14                         | 6
            14          | 7   || 14                         | 0
            101         | 10  || 110                        | 9
    }

    def "should select earliest bus"() {
        given:
            def buses = [5, 7, 11]

        expect:
            selectEarliestBus(arrivalTime, buses) == expectedEarliestBus

        where:
            arrivalTime || expectedEarliestBus
            5           || 5
            7           || 7
            11          || 11
            16          || 5
            27          || 7
            32          || 11
    }

    def "should parse arrival time and buses from notes"() {
        when:
            def notes = parseNotes(notesInput)

        then:
            notes.arrivalTime == expectedArrivalTime
            notes.buses == expectedBuses

        where:
            notesInput           || expectedArrivalTime | expectedBuses
            ['10', '1,2,3']      || 10                  | [1, 2, 3]
            ['939', '3,7,19,22'] || 939                 | [3, 7, 19, 22]
    }

    def "should ignore out of service buses"() {
        when:
            def notes = parseNotes(notesInput)

        then:
            notes.buses == expectedBuses

        where:
            notesInput                     || expectedBuses
            ['1', 'x,x,x,1,x,x,x']         || [1]
            ['10', '1,x,2,x,3']            || [1, 2, 3]
            ['939', '3,x,x,7,19,x,x,22,x'] || [3, 7, 19, 22]
    }

    private static DepartTime calculateEarliestDepartTime(int arrivalTime, int bus) {
        def calculator = new DepartTimeCalculator(bus)

        return calculator.calculate(arrivalTime)
    }

    private static int selectEarliestBus(int arrivalTime, List<Integer> buses) {
        def finder = new EarliestDepartTimeFinder(buses)

        return finder.find(arrivalTime).bus
    }

    private static Notes parseNotes(List<String> notesInput) {
        def parser = new NotesParser()

        parser.parse(notesInput)
    }
}

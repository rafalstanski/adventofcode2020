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
                expectedAnswer: 1068781L
        )
    }

    def "should calculate earliest depart time based on arrival time"() {
        when:
            def departTime = calculateEarliestDepartTime(arrivalTime, busId)

        then:
            departTime.value == expectedEarliestDepartTime
            departTime.waitTime == expectedWaitTime

        where:
            arrivalTime | busId || expectedEarliestDepartTime | expectedWaitTime
            1           | 7     || 7                          | 6
            5           | 7     || 7                          | 2
            8           | 7     || 14                         | 6
            14          | 7     || 14                         | 0
            101         | 10    || 110                        | 9
    }

    def "should select earliest bus"() {
        given:
            def buses = [5, 7, 11]

        expect:
            selectEarliestBus(arrivalTime, buses) == expectedEarliestBusId

        where:
            arrivalTime || expectedEarliestBusId
            5           || 5
            7           || 7
            11          || 11
            16          || 5
            27          || 7
            32          || 11
    }

    def "should parse arrival time from notes"() {
        when:
            def notes = parseNotes(notesInput)

        then:
            notes.arrivalTime == expectedArrivalTime

        where:
            notesInput       || expectedArrivalTime
            ['10', '1,2,3']  || 10
            ['939', '1,2,3'] || 939
    }

    def "should parse buses and offset from notes"() {
        when:
            def notes = parseNotes(notesInput)

        then:
            notes.buses == expectedBuses

        where:
            notesInput         || expectedBuses
            ['1', '1']         || [bus(1, 0)]
            ['1', '1,2,3']     || [bus(1, 0), bus(2, 1), bus(3, 2)]
            ['1', '3,7,19,22'] || [bus(3, 0), bus(7, 1), bus(19, 2), bus(22, 3)]
    }


    def "should ignore out of service buses"() {
        when:
            def notes = parseNotes(notesInput)

        then:
            notes.buses == expectedBuses

        where:
            notesInput                     || expectedBuses
            ['1', 'x,x,x,1,x,x,x']         || [bus(1, 3)]
            ['10', '1,x,2,x,3']            || [bus(1, 0), bus(2, 2), bus(3, 4)]
            ['939', '3,x,x,7,19,x,x,22,x'] || [bus(3, 0), bus(7, 1), bus(19, 0), bus(22, 0)]
    }

    private static DepartTime calculateEarliestDepartTime(int arrivalTime, int busId) {
        def calculator = new DepartTimeCalculator(bus(busId, 0))

        return calculator.calculate(arrivalTime)
    }

    private static int selectEarliestBus(int arrivalTime, List<Integer> busesIds) {
        def buses = busesIds.collect{bus(it, 0)}
        def finder = new EarliestDepartTimeFinder(buses)

        return finder.find(arrivalTime).bus.busId
    }

    private static Notes parseNotes(List<String> notesInput) {
        def parser = new NotesParser()

        return parser.parse(notesInput)
    }

    private static Bus bus(int busId, int offset) {
        return new Bus(busId, offset)
    }
}

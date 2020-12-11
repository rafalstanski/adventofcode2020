package com.hurricane.kata.adventofcode2020.day11

import spock.lang.Specification
import spock.lang.Unroll

import static com.hurricane.kata.adventofcode2020.day11.Seat.EMPTY
import static com.hurricane.kata.adventofcode2020.day11.Seat.FLOOR
import static com.hurricane.kata.adventofcode2020.day11.Seat.OCCUPIED

@Unroll
class Day11Part1Test extends Specification {

    def "empty seat should becomes occupied when there are no occupied neighbours"() {
        when:
            def seatAfterChange = changeSeat(EMPTY, 0)

        then:
            seatAfterChange == OCCUPIED
    }

    def "empty seat should not becomes occupied when there are occupied neighbours"() {
        when:
            def seatAfterChange = changeSeat(EMPTY, occupiedNeighbours)

        then:
            seatAfterChange != OCCUPIED

        where:
            occupiedNeighbours << (1..8)
    }

    def "occupied seat should becomes empty when four or more neighbours are also occupied"() {
        when:
            def seatAfterChange = changeSeat(OCCUPIED, occupiedNeighbours)

        then:
            seatAfterChange == EMPTY

        where:
            occupiedNeighbours << (4..8)
    }

    def "occupied seat should not becomes empty when less then four neighbours are also occupied"() {
        when:
            def seatAfterChange = changeSeat(OCCUPIED, occupiedNeighbours)

        then:
            seatAfterChange != EMPTY

        where:
            occupiedNeighbours << (1..3)
    }

    def "floor should should not change no mather of occupied neighbours count"() {
        when:
            def seatAfterChange = changeSeat(FLOOR, occupiedNeighbours)

        then:
            seatAfterChange == FLOOR

        where:
            occupiedNeighbours << (0..8)
    }

    def "should count occupied neighbours based on adjacent seats"() {
        given:
            def seatGrid = parseLayout([
                    'L.L',
                    'L.L',
                    '#.#',
            ])

        expect:
            countOccupiedNeighbours(seatGrid, row, col) == expectedCount

        where:
            row | col | expectedCount
            0   | 0   | 0
            1   | 0   | 1
            1   | 1   | 2
            2   | 0   | 0
            2   | 1   | 2
    }

    def "should change all seat"() {
        given:
            def seatGrid = parseLayout([
                    'L.L#',
                    'L.##',
                    '#.##',
            ])

        when:
            def seatGridAfter = runSeatChanges(seatGrid)

        then:
            expectedLayout(seatGridAfter, [
                    '#.L#',
                    'L.LL',
                    '#.##',
            ])
    }

    private static Seat changeSeat(Seat seat, int occupiedNeighbours) {
        def changer = new SeatChanger()

        changer.change(seat, occupiedNeighbours)
    }

    private static SeatGrid parseLayout(List<String> rowLayouts) {
        def parser = new SeatGridParser()

        parser.parse(rowLayouts)
    }

    private static int countOccupiedNeighbours(SeatGrid seatGrid, int row, int col) {
        def counter = new OccupiedNeighboursCounter(seatGrid)

        counter.count(row, col)
    }

    private static SeatGrid runSeatChanges(SeatGrid seatGrid) {
        def changer = new SeatGridChanger(new SeatChanger())

        return changer.change(seatGrid)
    }

    private static void expectedLayout(SeatGrid seatGrid, List<String> expectedLayout) {
        expectedLayout.withIndex().forEach { row ->
            row.first.toList().withIndex().forEach { cell ->
                def seat = seatGrid.seatAt(row.second, cell.second)
                def expectedSeat = toSeat(cell.first)
                if (seat != expectedSeat) {
                    assert false, "Seat ($row.second, $cell.second) should be $expectedSeat but was $seat"
                }
            }
        }
    }

    private static Seat toSeat(String sign) {
        switch (sign) {
            case "#": return OCCUPIED
            case 'L': return EMPTY
            default: return FLOOR
        }
    }
}

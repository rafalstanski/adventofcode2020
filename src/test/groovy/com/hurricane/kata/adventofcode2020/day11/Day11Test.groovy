package com.hurricane.kata.adventofcode2020.day11

import com.hurricane.kata.adventofcode2020.Puzzle
import com.hurricane.kata.adventofcode2020.PuzzleIntTestSupport
import spock.lang.Unroll

import static com.hurricane.kata.adventofcode2020.day11.Seat.EMPTY
import static com.hurricane.kata.adventofcode2020.day11.Seat.FLOOR
import static com.hurricane.kata.adventofcode2020.day11.Seat.OCCUPIED

//. floor
//L empty seat
//# occupied
@Unroll
class Day11Test extends PuzzleIntTestSupport {

    @Override
    protected Puzzle puzzlePart1() {
        new Puzzle(
                inputLocation: 'test_entries_day11.txt',
                solution: new Day11Part1Runner(),
                expectedAnswer: 37
        )
    }

    @Override
    protected Puzzle puzzlePart2() {
        new Puzzle(
                inputLocation: 'test_entries_day11.txt',
                solution: new Day11Part2Runner(),
                expectedAnswer: 26
        )
    }

    def "should parse seat row's layout"() {
        expect:
            parseRowLayout(rowLayout) == expctedLayout

        where:
            rowLayout || expctedLayout
            '....'    || [FLOOR, FLOOR, FLOOR, FLOOR]
            'LLLL'    || [EMPTY, EMPTY, EMPTY, EMPTY]
            '####'    || [OCCUPIED, OCCUPIED, OCCUPIED, OCCUPIED]
            'L.L#'    || [EMPTY, FLOOR, EMPTY, OCCUPIED]
            '#.L#L.'  || [OCCUPIED, FLOOR, EMPTY, OCCUPIED, EMPTY, FLOOR]
    }

    def "empty seat should becomes occupied when there are no occupied seats adjacent to it"() {
        when:
            def seatAfterChange = changeSeat(EMPTY, 0)

        then:
            seatAfterChange == OCCUPIED
    }

    def "empty seat should not becomes occupied when there are some occupied seats adjacent to it"() {
        when:
            def seatAfterChange = changeSeat(EMPTY, occupiedNeighbours)

        then:
            seatAfterChange != OCCUPIED

        where:
            occupiedNeighbours << (1..8)
    }

    def "occupied seat should becomes empty when four or more seats adjacent to it are also occupied"() {
        when:
            def seatAfterChange = changeSeat(OCCUPIED, occupiedNeighbours)

        then:
            seatAfterChange == EMPTY

        where:
            occupiedNeighbours << (4..8)
    }

    def "occupied seat should not becomes empty when less then four seats adjacent to it are also occupied"() {
        when:
            def seatAfterChange = changeSeat(OCCUPIED, occupiedNeighbours)

        then:
            seatAfterChange != EMPTY

        where:
            occupiedNeighbours << (1..3)
    }

    def "floor should should not change no mather of occupied seats adjacent to it"() {
        when:
            def seatAfterChange = changeSeat(FLOOR, occupiedNeighbours)

        then:
            seatAfterChange == FLOOR

        where:
            occupiedNeighbours << (1..8)
    }

    def "should parse seat layout"() {
        given:
            def layout = [
                    'L.LL',
                    'L.L#',
                    '#.#.',
            ]

        when:
            def seatGrid = parseLayout(layout)

        then:
            seatGrid.seatAt(0, 0) == EMPTY
            seatGrid.seatAt(1, 1) == FLOOR
            seatGrid.seatAt(2, 2) == OCCUPIED
            seatGrid.seatAt(1, 2) == EMPTY
            seatGrid.seatAt(2, 3) == FLOOR
            seatGrid.seatAt(1, 3) == OCCUPIED
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

    def "should count occupied seats"() {
        given:
            def seatGrid = parseLayout([
                    'L.L#',
                    'L.##',
                    '#.##',
            ])

        when:
            def count = countOccupiedSeats(seatGrid)

        then:
            count == 6
    }

    private static List<Seat> parseRowLayout(String rowLayout) {
        def parser = new RowLayoutParser()

        parser.parse(rowLayout)
    }

    private static SeatGrid parseLayout(List<String> rowLayouts) {
        def parser = new SeatGridParser()

        parser.parse(rowLayouts)
    }

    private static Seat changeSeat(Seat seat, int occupiedNeighbours) {
        def changer = new SeatChanger()

        changer.change(seat, occupiedNeighbours)
    }

    private static SeatGrid runSeatChanges(SeatGrid seatGrid) {
        return seatGrid.changeSits()
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

    private static int countOccupiedSeats(SeatGrid seatGrid) {
        seatGrid.countOccupied()
    }
}

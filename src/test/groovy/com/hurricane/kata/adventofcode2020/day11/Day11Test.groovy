package com.hurricane.kata.adventofcode2020.day11

import com.hurricane.kata.adventofcode2020.Puzzle
import com.hurricane.kata.adventofcode2020.PuzzleIntTestSupport
import spock.lang.Unroll

import static com.hurricane.kata.adventofcode2020.day11.Seat.EMPTY
import static com.hurricane.kata.adventofcode2020.day11.Seat.FLOOR
import static com.hurricane.kata.adventofcode2020.day11.Seat.OCCUPIED

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

    def "should allow to read different seat type"() {
        when:
            def seatGrid = parseLayout(layout)

        then:
            seatGrid.seatAt(0, 0) == expectedSeat

        where:
            layout || expectedSeat
            ['L']  || EMPTY
            ['#']  || OCCUPIED
            ['.']  || FLOOR
    }

    def "should allow to read seats from selected position"() {
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

    def "should count occupied seats"() {
        given:
            def seatGrid = parseLayout(layout)

        expect:
            countOccupiedSeats(seatGrid) == expectedCount

        where:
            layout                || expectedCount
            ['#']                 || 1
            ['.']                 || 0
            ['L']                 || 0
            ['#.L']               || 1
            ['#.#']               || 2
            ['###']               || 3
            ['###', '#..']        || 4
            ['###', '#..', 'LL#'] || 5
    }

    private static SeatGrid parseLayout(List<String> rowLayouts) {
        def parser = new SeatGridParser()

        parser.parse(rowLayouts)
    }

    private static int countOccupiedSeats(SeatGrid seatGrid) {
        seatGrid.countOccupied()
    }

}

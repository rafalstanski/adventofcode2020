package com.hurricane.kata.adventofcode2020.day5

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class Day5Test extends Specification {

    def "should decode row number"() {
        when:
            def rowNumber = decodeRowNumber(rowNumberCode)

        then:
            rowNumber == expectedRowNumber

        where:
            rowNumberCode || expectedRowNumber
            'FBFBBFF'     || 44
            'BFFFBBF'     || 70
            'FFFBBBF'     || 14
            'BBFFBBF'     || 102
    }

    def "should decode column number"() {
        when:
            def columnNumber = decodeColumnNumber(columnNumberCode)

        then:
            columnNumber == expectedColumnNumber

        where:
            columnNumberCode || expectedColumnNumber
            'RLR'            || 5
            'RRR'            || 7
            'RLL'            || 4
    }

    def 'should decode seat number'() {
        when:
            def seatNumber = decodeSeatNumber(seatNumberCode)

        then:
            seatNumber == seat(expectedRow, expectedColumn)

        where:
            seatNumberCode || expectedRow | expectedColumn
            'BFFFBBFRRR'   || 70          | 7
            'FFFBBBFRRR'   || 14          | 7
            'BBFFBBFRLL'   || 102         | 4
    }

    def 'should return seat id'() {
        when:
            def seatNumber = decodeSeatNumber(seatNumberCode)

        then:
            seatNumber.id == expectedId

        where:
            seatNumberCode || expectedId
            'BFFFBBFRRR'   || 567
            'FFFBBBFRRR'   || 119
            'BBFFBBFRLL'   || 820
    }

    private int decodeRowNumber(String rowNumberCode) {
        def rowNumberDecoder = new RowNumberDecoder()

        rowNumberDecoder.decode(rowNumberCode)
    }

    private int decodeColumnNumber(String columnNumberCode) {
        def columnNumberDecoder = new ColumnNumberDecoder()

        columnNumberDecoder.decode(columnNumberCode)
    }

    private SeatNumber decodeSeatNumber(String seatCode) {
        def seatNumberDecoder = new SeatNumberDecoder()

        seatNumberDecoder.decode(seatCode)
    }

    private SeatNumber seat(int row, int col) {
        new SeatNumber(row, col)
    }

}

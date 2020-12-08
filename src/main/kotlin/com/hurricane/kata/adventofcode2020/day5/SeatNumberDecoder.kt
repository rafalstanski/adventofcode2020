package com.hurricane.kata.adventofcode2020.day5

class SeatNumberDecoder {

    private val rowNumberDecoder = RowNumberDecoder()
    private val columnNumberDecoder = ColumnNumberDecoder()

    fun decode(seatNumberCode: String): SeatNumber {
        val rowCode = seatNumberCode.take(7)
        val colCode = seatNumberCode.takeLast(3)

        return SeatNumber(
                row = rowNumberDecoder.decode(rowCode),
                col = columnNumberDecoder.decode(colCode))
    }
}
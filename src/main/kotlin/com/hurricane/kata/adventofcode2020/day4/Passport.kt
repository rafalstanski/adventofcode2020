package com.hurricane.kata.adventofcode2020.day4

data class Passport(
        val fields: List<PassportField>
)

data class PassportField(
        val type: PassportFieldType,
        val value: String
)

enum class PassportFieldType {
    BYR,
    IYR,
    EYR,
    HGT,
    HCL,
    ECL,
    PID,
    CID
}
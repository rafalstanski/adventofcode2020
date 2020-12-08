package com.hurricane.kata.adventofcode2020.day6

class AnswersCounter(private val answersExtractor: AnswersExtractor) {

    fun count(groupAnswersLines: List<String>): Int {
        return answersExtractor.extract(groupAnswersLines).size
    }

}
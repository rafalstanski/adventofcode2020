package com.hurricane.kata.adventofcode2020.day6

interface AnswersExtractor {

    fun extract(groupAnswersLines: List<String>): Set<Char>
}

class UniqueAnswersExtractor : AnswersExtractor {

    override fun extract(groupAnswersLines: List<String>): Set<Char> {
        return groupAnswersLines
                .flatMap { it.toCharArray().toList() }
                .toSet()
    }
}

class DoneByAllAnswersExtractor : AnswersExtractor {

    override fun extract(groupAnswersLines: List<String>): Set<Char> =
            findAnswersInterestedInAll(groupAnswersLines)

    private fun findAnswersInterestedInAll(groupAnswersLines: List<String>): Set<Char> {
        return groupAnswersLines
                .map { it.toCharArray().toList() }
                .reduce { acc, answers -> intersect(acc, answers) }
                .toSet()
    }

    private fun intersect(acc: List<Char>, answers: List<Char>) =
            (acc intersect answers).toList()
}
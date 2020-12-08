package com.hurricane.kata.adventofcode2020.shared

class PuzzleRunner(
        private val puzzleDescriptions: List<PuzzleDescription>
) {
    fun run(puzzleName: String) {
        val description = findPuzzleDescription(puzzleName)

        solveAndPrint(description)
    }

    fun runAll() {
        puzzleDescriptions.forEach { solveAndPrint(it) }
    }

    private fun solveAndPrint(description: PuzzleDescription) {
        val puzzleAnswer = solve(description)

        printPuzzleAnswer(description, puzzleAnswer)
    }

    private fun findPuzzleDescription(puzzleName: String): PuzzleDescription {
        return puzzleDescriptions
                .find { it.name == puzzleName }
                ?: throw IllegalArgumentException("Puzzle with $puzzleName name doesn't exists")
    }

    private fun solve(description: PuzzleDescription): PuzzleAnswer = with(description) {
        val entries = EntriesLoader.load(inputLocation)

        return solution.solve(PuzzleInput(entries))
    }

    private fun printPuzzleAnswer(description: PuzzleDescription, puzzleAnswer: PuzzleAnswer) {
        println("Answer for puzzle '${description.name}' is: ${puzzleAnswer.solution}")
    }

}

interface PuzzleSolution {
    fun solve(puzzleInput: PuzzleInput): PuzzleAnswer
}

data class PuzzleDescription(
        val name: String,
        val inputLocation: String,
        val solution: PuzzleSolution
)

data class PuzzleInput(
        val entries: List<String>
)

data class PuzzleAnswer(
        val solution: Any?
)

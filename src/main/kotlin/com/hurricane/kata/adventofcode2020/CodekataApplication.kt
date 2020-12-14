package com.hurricane.kata.adventofcode2020

import com.hurricane.kata.adventofcode2020.day1.Day1Part1Runner
import com.hurricane.kata.adventofcode2020.day1.Day1Part2Runner
import com.hurricane.kata.adventofcode2020.day10.Day10Part1Runner
import com.hurricane.kata.adventofcode2020.day10.Day10Part2Runner
import com.hurricane.kata.adventofcode2020.day11.Day11Part1Runner
import com.hurricane.kata.adventofcode2020.day11.Day11Part2Runner
import com.hurricane.kata.adventofcode2020.day12.Day12Part1Runner
import com.hurricane.kata.adventofcode2020.day12.Day12Part2Runner
import com.hurricane.kata.adventofcode2020.day13.Day13Part1Runner
import com.hurricane.kata.adventofcode2020.day13.Day13Part2Runner
import com.hurricane.kata.adventofcode2020.day2.Day2Part1Runner
import com.hurricane.kata.adventofcode2020.day2.Day2Part2Runner
import com.hurricane.kata.adventofcode2020.day3.Day3Part1Runner
import com.hurricane.kata.adventofcode2020.day3.Day3Part2Runner
import com.hurricane.kata.adventofcode2020.day4.Day4Part1Runner
import com.hurricane.kata.adventofcode2020.day4.Day4Part2Runner
import com.hurricane.kata.adventofcode2020.day5.Day5Part1Runner
import com.hurricane.kata.adventofcode2020.day5.Day5Part2Runner
import com.hurricane.kata.adventofcode2020.day6.Day6Part1Runner
import com.hurricane.kata.adventofcode2020.day6.Day6Part2Runner
import com.hurricane.kata.adventofcode2020.day7.Day7Part1Runner
import com.hurricane.kata.adventofcode2020.day7.Day7Part2Runner
import com.hurricane.kata.adventofcode2020.day8.Day8Part1Runner
import com.hurricane.kata.adventofcode2020.day8.Day8Part2Runner
import com.hurricane.kata.adventofcode2020.day9.Day9Part1Runner
import com.hurricane.kata.adventofcode2020.day9.Day9Part2Runner
import com.hurricane.kata.adventofcode2020.shared.PuzzleDescription
import com.hurricane.kata.adventofcode2020.shared.PuzzleRunner

fun main(args: Array<String>) {
    val puzzleRunner = PuzzleRunner(listOf(
            PuzzleDescription("day1_part1", "entries_day1.txt", Day1Part1Runner()),
            PuzzleDescription("day1_part2", "entries_day1.txt", Day1Part2Runner()),
            PuzzleDescription("day2_part1", "entries_day2.txt", Day2Part1Runner()),
            PuzzleDescription("day2_part2", "entries_day2.txt", Day2Part2Runner()),
            PuzzleDescription("day3_part1", "entries_day3.txt", Day3Part1Runner()),
            PuzzleDescription("day3_part2", "entries_day3.txt", Day3Part2Runner()),
            PuzzleDescription("day4_part1", "entries_day4.txt", Day4Part1Runner()),
            PuzzleDescription("day4_part2", "entries_day4.txt", Day4Part2Runner()),
            PuzzleDescription("day5_part1", "entries_day5.txt", Day5Part1Runner()),
            PuzzleDescription("day5_part2", "entries_day5.txt", Day5Part2Runner()),
            PuzzleDescription("day6_part1", "entries_day6.txt", Day6Part1Runner()),
            PuzzleDescription("day6_part2", "entries_day6.txt", Day6Part2Runner()),
            PuzzleDescription("day7_part1", "entries_day7.txt", Day7Part1Runner()),
            PuzzleDescription("day7_part2", "entries_day7.txt", Day7Part2Runner()),
            PuzzleDescription("day8_part1", "entries_day8.txt", Day8Part1Runner()),
            PuzzleDescription("day8_part2", "entries_day8.txt", Day8Part2Runner()),
            PuzzleDescription("day9_part1", "entries_day9.txt", Day9Part1Runner()),
            PuzzleDescription("day9_part2", "entries_day9.txt", Day9Part2Runner()),
            PuzzleDescription("day10_part1", "entries_day10.txt", Day10Part1Runner()),
            PuzzleDescription("day10_part2", "entries_day10.txt", Day10Part2Runner()),
            PuzzleDescription("day11_part1", "entries_day11.txt", Day11Part1Runner()),
            PuzzleDescription("day11_part2", "entries_day11.txt", Day11Part2Runner()),
            PuzzleDescription("day11_part2", "entries_day11.txt", Day11Part2Runner()),
            PuzzleDescription("day12_part1", "entries_day12.txt", Day12Part1Runner()),
            PuzzleDescription("day12_part2", "entries_day12.txt", Day12Part2Runner()),
            PuzzleDescription("day13_part1", "entries_day13.txt", Day13Part1Runner()),
            PuzzleDescription("day13_part2", "entries_day13.txt", Day13Part2Runner())
    ))

    puzzleRunner.runAll()
}
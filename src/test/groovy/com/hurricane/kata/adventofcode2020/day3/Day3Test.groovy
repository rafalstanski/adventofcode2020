package com.hurricane.kata.adventofcode2020.day3

import com.hurricane.kata.adventofcode2020.day3.Board
import com.hurricane.kata.adventofcode2020.day3.PointType
import com.hurricane.kata.adventofcode2020.day3.Traverser
import spock.lang.Specification
import spock.lang.Unroll

import static com.hurricane.kata.adventofcode2020.day3.PointType.OPEN_SQUARE
import static com.hurricane.kata.adventofcode2020.day3.PointType.TREE

@Unroll
class Day3Test extends Specification {

    private Board board

    def "should indicate open square at single line board"() {
        given:
            defineBoard(line)

        expect:
            isOpenSquareAt(0, expectOpenSquareAtCol)

        where:
            line    || expectOpenSquareAtCol
            '.#'    || 0
            '#.'    || 1
            '##.##' || 2
    }

    def "should indicate open square at multiline board"() {
        given:
            defineBoard("""
                ..##.......
                #...#...#..
                .#....#..#.
            """)

        expect:
            isOpenSquareAt(row, col)

        where:
            row | col
            0   | 0
            0   | 5
            1   | 1
            1   | 6
            2   | 7
    }

    def "should indicate tree at single line board"() {
        given:
            defineBoard(line)

        expect:
            isTreeAt(0, expectTreeAtCol)

        where:
            line    || expectTreeAtCol
            '#.'    || 0
            '.#'    || 1
            '..#..' || 2
    }

    def "should indicate tree at multiline board"() {
        given:
            defineBoard("""
                ..##.......
                #...#...#..
                .#....#..#.
            """)

        expect:
            isTreeAt(row, col)

        where:
            row | col
            0   | 2
            0   | 3
            1   | 4
            1   | 8
            2   | 9
    }

    def "should be able to wrap when checking point outside board's boundary"() {
        given:
            defineBoard("""
                ...###
                ......
                ######
                .#.#.#
            """)

        expect:
            pointTypeAt(expectedPointType, row, col)

        where:
            row | col        || expectedPointType
            0   | 0          || OPEN_SQUARE
            0   | 0 + 6      || OPEN_SQUARE
            0   | 0 + 6 * 30 || OPEN_SQUARE
            0   | 3          || TREE
            0   | 3 + 6      || TREE
            0   | 3 + 6 * 30 || TREE
            3   | 4           | OPEN_SQUARE
            3   | 4 + 6 * 2   | OPEN_SQUARE
            3   | 5           | TREE
            3   | 5 + 6 * 2   | TREE
    }

    def "should be abbe to travers through board and collect points"() {
        given:
            defineBoard("""
                ..##.......
                #...#...#..
                .#....#..#.
            """)

        expect:
            traversWithVector(down, right) == expectedPoints

        where:
            down | right | expectedPoints
            1    | 1     | [OPEN_SQUARE, OPEN_SQUARE]
            1    | 4     | [TREE, OPEN_SQUARE]
    }

    def "should count all visited trees"() {
        given:
            defineBoard("""
                ..##.......
                #...#...#..
                .#....#..#.
                ..#.#...#.#
                .#...##..#.
                ..#.##.....
                .#.#.#....#
                .#........#
                #.##...#...
                #...##....#
                .#..#...#.#
            """)

        expect:
            countVisitedTrees(1, 3) == 7
    }

    private void defineBoard(String boardLayout) {
        def boardLines = boardLayout
                .split('\n')
                .collect { it.trim() }
                .findAll { it.length() > 0 }

        board = new Board(boardLines)
    }

    private void isOpenSquareAt(int row, int col) {
        assert board.peekAt(row, col) == OPEN_SQUARE
    }

    private void isTreeAt(int row, int col) {
        assert board.peekAt(row, col) == TREE
    }

    void pointTypeAt(PointType pointType, int row, int col) {
        assert board.peekAt(row, col) == pointType
    }

    List<PointType> traversWithVector(int down, int right) {
        def traverser = new Traverser(board)

        return traverser.travel(down, right)
    }

    int countVisitedTrees(int down, int right) {
        def traverser = new Traverser(board)
        def pointCounter = new PointsCounter(TREE, traverser)

        pointCounter.countTraveling(down, right)
    }
}

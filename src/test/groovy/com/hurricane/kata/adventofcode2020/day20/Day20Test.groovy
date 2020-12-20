package com.hurricane.kata.adventofcode2020.day20

import com.hurricane.kata.adventofcode2020.Puzzle
import com.hurricane.kata.adventofcode2020.PuzzleIntTestSupport
import com.hurricane.kata.adventofcode2020.day18.Day18Part1Runner
import com.hurricane.kata.adventofcode2020.day18.Day18Part2Runner
import spock.lang.Unroll

@Unroll
class Day20Test extends PuzzleIntTestSupport {

    @Override
    protected Puzzle puzzlePart1() {
        new Puzzle(
                inputLocation: 'test_entries_day20.txt',
                solution: new Day18Part1Runner(),
                expectedAnswer: 20899048083289
        )
    }

    @Override
    protected Puzzle puzzlePart2() {
        new Puzzle(
                inputLocation: 'test_entries_day18.txt',
                solution: new Day18Part2Runner(),
                expectedAnswer: 23340
        )
    }

    def "should extract single tile"() {
        given:
            def tileLines = [
                    'Tile 1:',
                    '..',
                    '#.'
            ]

        when:
            def tile = extractTile(tileLines)

        then:
            tile.id == 1
            tile.lines == ['..', '#.']
    }

    def "should extract all tiles"() {
        given:
            def tilesLines = [
                    'Tile 1:',
                    '..',
                    '#.',
                    '',
                    'Tile 33:',
                    '##',
                    '..',
                    '',
                    'Tile 560:',
                    '.#',
                    '#.'
            ]

        when:
            def tiles = extractTiles(tilesLines)

        then:
            tiles[0].id == 1
            tiles[0].lines == ['..', '#.']
        and:
            tiles[1].id == 33
            tiles[1].lines == ['##', '..']
        and:
            tiles[2].id == 560
            tiles[2].lines == ['.#', '#.']
    }

    def "should read borders from tile lines"() {
        given:
            def tileLines = [
                    '....',
                    '#.#.',
                    '.##.',
                    '.###'
            ]

        expect:
            extractBorders(tileLines) == [
                    top('....'),
                    right('...#'),
                    bottom('.###'),
                    left('.#..')]
    }

    def "should tell if border match tile"() {
        given:
            def tile = extractTile([
                    'Tile 1:',
                    '....',
                    '#.#.',
                    '.##.',
                    '.###'
            ])

        expect:
            borderMatches(borderToMatch, tile) == matchesResult

        where:
            borderToMatch  || matchesResult
            top('.###')    || true
            bottom('....') || true
            left('...#')   || true
            right('.#..')  || true
            top('#...')    || true
            top('....')    || true
            top('.#..')    || true
    }

    def "should calculate borders hashes"() {
        expect:
            calculateBorderHash(border) == expectedHash

        where:
            border       || expectedHash
            '..........' || 0
            '.........#' || 1
            '#.........' || 512
            '...##.....' || 32 + 64
            '##########' || 1023
    }

    def "should extract all border hashes (clockwise from top) from tile"() {
        given:
            def tile = [
                    'Tile 1:',
                    '....',
                    '#.#.',
                    '.##.',
                    '.###'
            ]

        expect:
            extractBordersHashes(tile) == [0, 8, 7, 2]
    }

    private static int calculateBorderHash(String border) {
        def borderHashCalculator = new BorderHashCalculator()

        return borderHashCalculator.calculate(border)
    }

    private static List<Integer> extractBordersHashes(List<String> tilesLines) {
        def tilesParser = new TileParser()
        def tile = tilesParser.parse(tilesLines)

        return tile.bordersHashes
    }

    private static Tile extractTile(List<String> tileLines) {
        def tilesParser = new TileParser()

        return tilesParser.parse(tileLines)
    }

    private static List<Tile> extractTiles(List<String> tilesLines) {
        def tilesParser = new TilesParser()

        return tilesParser.parse(tilesLines)
    }

    private static List extractBorders(List<String> tileLines) {
        def borderExtractor = new BorderExtractor()

        return borderExtractor.extract(tileLines)
    }

    private static Border top(String borderPixels) {
        new TopBorder(borderPixels)
    }

    private static Border right(String borderPixels) {
        new RightBorder(borderPixels)
    }

    private static Border bottom(String borderPixels) {
        new BottomBorder(borderPixels)
    }

    private static Border left(String borderPixels) {
        new LeftBorder(borderPixels)
    }

    private static boolean borderMatches(Border border, Tile tile) {
        return tile.matchesAnyBorder(border)
    }
}
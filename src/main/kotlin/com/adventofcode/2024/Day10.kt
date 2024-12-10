package com.adventofcode.`2024`

fun main() {
    val trailHeadPosition = mutableListOf<Point>()
    val map = readFileFromResources("day10.txt").orEmpty()
        .lineSequence()
        .mapIndexed { row, line ->
            line.split("")
                .filter { it.isNotBlank() }
                .mapIndexed { column, value ->
                    if (value == "0") {
                        trailHeadPosition.add(Point(row, column))
                    }
                    value.toInt()
                }
                .toIntArray()
        }
        .toList()


    fun calculateScore(cur: Point, expectedSlopeValue: Int, onReachPeek: (Point) -> Unit) {
        if (expectedSlopeValue == 10) {
            return
        }

        if (map.isInMap(cur).not()) {
            return
        }

        val value = map.getValue(cur)

        if (expectedSlopeValue != value) {
            return
        }

        if (value == 9) {
            onReachPeek(cur)
            return
        }

        val nextExpectedValue = expectedSlopeValue + 1

        for (dir in Direction.FOUR_DIR) {
            calculateScore(cur.next(dir),  nextExpectedValue, onReachPeek)
        }
    }

    var totalScore = 0
    var totalRating = 0

    for (trailHead in trailHeadPosition) {
        val hikeTrailPointByScore = mutableSetOf<Point>()
        val hikeTrailPointByRating = mutableListOf<Point>()

        val onReachPeek: (Point) -> Unit = {
            hikeTrailPointByScore.add(it)
            hikeTrailPointByRating.add(it)
        }

        calculateScore(trailHead, 0, onReachPeek)
        totalScore += hikeTrailPointByScore.size
        totalRating += hikeTrailPointByRating.size
    }

    println("Score $totalScore")
    println("Rating $totalRating")
}
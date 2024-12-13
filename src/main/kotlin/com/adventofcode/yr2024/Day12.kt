package com.adventofcode.yr2024

import com.adventofcode.*

fun main() {
    val plots = readFileFromResources("day12.txt").orEmpty()
        .lineSequence()
        .map { line ->
            line.toCharArray()
        }
        .toList()

    val coveredPlot = mutableListOf<Point>()

    fun makeFence(
        garden: Char,
        curPoint: Point,
        lastPoint: Point,
        direction: Direction?,
        fenceMap: MutableMap<Direction, MutableList<Point>>
    ): Int {
        if (!plots.isInMap(curPoint)) {
            if (direction != null) {
                fenceMap.getOrPut(direction) { mutableListOf() }.apply {
                    add(lastPoint)
                }
            }
            return 1
        }

        val value = plots.getValue(curPoint)

        if (value != garden) {
            if (direction != null) {
                fenceMap.getOrPut(direction) { mutableListOf() }.apply {
                    add(lastPoint)
                }
            }
            return 1
        }

        if (coveredPlot.contains(curPoint)) {
            return 0
        }

        coveredPlot.add(curPoint)

        var counter = 0

        for (dir in Direction.entries) {
            counter += makeFence(garden, curPoint.next(dir), curPoint, dir, fenceMap)
        }

        return counter
    }


    var lastPlotSize: Int
    var amountByFenceCount = 0
    var amountByFenceSideCount = 0

    val xPointComparator = Comparator<Point> { first, second ->
        when {
            first == second -> 0
            first.x < second.x -> -1
            first.x > second.x -> 1
            else -> {
                when {
                    first.y == second.y -> 0
                    first.y < second.y -> -1
                    else -> 1
                }
            }
        }
    }

    val yPointComparator = Comparator<Point> { first, second ->
        when {
            first == second -> 0
            first.y < second.y -> -1
            first.y > second.y -> 1
            else -> {
                when {
                    first.x == second.x -> 0
                    first.x < second.x -> -1
                    else -> 1
                }
            }
        }
    }


    for (r in plots.indices) {
        for (c in plots[r].indices) {
            val point = Point(r, c)
            if (coveredPlot.contains(point)) {
                continue
            }
            lastPlotSize = coveredPlot.size
            val fenceMap = mutableMapOf<Direction, MutableList<Point>>()
            val fenceCount = makeFence(plots.getValue(point), point, point, null, fenceMap)

            amountByFenceCount += (coveredPlot.size - lastPlotSize) * fenceCount

            var slidesCount = 0
            for (key in fenceMap.keys) {
                var lastFencePoint = Point(Int.MIN_VALUE, Int.MIN_VALUE)

                val comparator = if (key in listOf(Direction.Up, Direction.Down)) {
                    xPointComparator
                } else {
                    yPointComparator
                }

                val sortedPoints =  fenceMap[key]?.sortedWith(comparator).orEmpty()

                for (p in sortedPoints) {
                    val valid: Boolean = if (key in listOf(Direction.Up, Direction.Down)) {
                        lastFencePoint.x == p.x && lastFencePoint.y + 1 == p.y
                    } else {
                        lastFencePoint.y == p.y && lastFencePoint.x + 1 == p.x
                    }
                    lastFencePoint = p

                    if (valid) {
                        continue
                    }

                    slidesCount += 1
                }
            }

            amountByFenceSideCount += (coveredPlot.size - lastPlotSize) * slidesCount
        }
    }

    println("Part 1 $amountByFenceCount")
    println("Part 2 $amountByFenceSideCount")
}
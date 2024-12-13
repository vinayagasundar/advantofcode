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

            var sideCount = 0
            for (key in fenceMap.keys) {
                var lastFencePoint = Point(Int.MIN_VALUE, Int.MIN_VALUE)

                val comparator = if (key in listOf(Direction.Up, Direction.Down)) {
                    pointXComparator
                } else {
                    pointYComparator
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

                    sideCount += 1
                }
            }

            amountByFenceSideCount += (coveredPlot.size - lastPlotSize) * sideCount
        }
    }

    println("Part 1 $amountByFenceCount")
    println("Part 2 $amountByFenceSideCount")
}
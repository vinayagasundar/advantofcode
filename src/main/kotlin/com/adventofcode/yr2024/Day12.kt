package com.adventofcode.yr2024

import com.adventofcode.*

fun main() {
    val plots = readFileFromResources("day12.txt").orEmpty()
        .lineSequence()
        .map { line ->
            line.toCharArray()
        }
        .toList()

    fun makeFence(garden: Char, point: Point, covered: MutableList<Point>): Int {
        if (!plots.isInMap(point)) {
            return 1
        }

        val value = plots.getValue(point)

        if (value != garden) {
            return 1
        }

        if (covered.contains(point)) {
            return 0
        }

        covered.add(point)

        var counter = 0

        for (dir in Direction.entries) {
            counter += makeFence(garden, point.next(dir), covered)
        }

        return counter
    }


    val covered = mutableListOf<Point>()
    var lastPlotSize = 0
    var amount = 0
    for (r in plots.indices) {
        for (c in plots[r].indices) {
            if (covered.contains(Point(r, c))) {
                continue
            }
            lastPlotSize = covered.size
            val value = makeFence(plots[r][c], Point(r, c), covered)
            amount += (covered.size - lastPlotSize) * value
        }
    }

    println(amount)
}
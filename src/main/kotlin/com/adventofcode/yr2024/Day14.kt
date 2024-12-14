package com.adventofcode.yr2024

import com.adventofcode.readFileFromResources

fun main() {
    val regex = Regex("-*\\d+")
    val maxRow = 103
    val maxColumn = 101
    val noOfSeconds = 100
    val input = readFileFromResources("day14.txt").orEmpty()
        .lineSequence()
        .map { line ->
            val (px, py, vx, vy) = regex.findAll(line).map { it.value.toInt() }.toList()
            var (cx, cy) = px to py

            for (i in 1..noOfSeconds) {
                cx += vx
                cy += vy

                while (true) {
                    if (cx in 0 until maxColumn && cy in 0 until maxRow) {
                        break
                    }

                    if (cx < 0) {
                        cx = maxColumn + cx
                    }

                    if (cx >= maxColumn) {
                        cx = cx - maxColumn
                    }

                    if (cy < 0) {
                        cy = maxRow + cy
                    }

                    if (cy >= maxRow) {
                        cy = cy - maxRow
                    }
                }
            }
            (cy to cx)
        }
        .toList()


    val midRow = maxRow / 2
    val midColumn = maxColumn / 2

    val map = Array(maxRow) {
        IntArray(maxColumn) {
            0
        }
    }

    var topLeft = 0
    var topRight = 0
    var bottomLeft = 0
    var bottomRight = 0

    for ((r, c) in input) {
        map[r][c] += 1
        when {
            r < midRow && c < midColumn -> topLeft += 1
            r < midRow && c > midColumn -> topRight += 1
            r > midRow && c < midColumn -> bottomLeft += 1
            r > midRow && c > midColumn -> bottomRight += 1
        }
    }

    println(map.joinToString("\n") { it.joinToString(" ") })
    println("Part ${topLeft * topRight * bottomLeft * bottomRight}")
}
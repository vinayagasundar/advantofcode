package com.adventofcode.yr2024

import com.adventofcode.*

fun main() {
    var robotPos = Point(0, 0)
    val (mapValue, directionValue) = readFileFromResources("day15.txt").orEmpty().split("\n\n")
    val map = mapValue
        .lineSequence()
        .mapIndexed { r, line ->
            line.toCharArray()
                .mapIndexed { c, value ->
                    if (value == '@') {
                        robotPos = Point(r, c)
                        '.'
                    } else value
                }
                .toCharArray()
        }
        .toList()

    for (d in directionValue) {
        if (d == '\n') {
            continue
        }
        val dir = d.toDirection()
        val updatedRobotPos = robotPos.next(dir)
        val value = map.getValue(updatedRobotPos)

        if (value == '#') {
            continue
        }

        if (value == '.') {
            robotPos = updatedRobotPos
            continue
        }

        val boxLocation = mutableListOf<Point>()
        var canMove = true
        var boxPos = updatedRobotPos
        boxLocation.add(boxPos)

        while (true) {
            boxPos = boxPos.next(dir)
            val boxValue = map.getValue(boxPos)

            if (boxValue == '#') {
                canMove = false
                break
            }

            if (boxValue == 'O') {
                boxLocation.add(boxPos)
            }

            if (boxValue == '.') {
                boxLocation.add(boxPos)
                break
            }
        }

        if (canMove) {
            for (i in boxLocation.indices) {
                val point = boxLocation[i]
                if (i == 0) {
                    robotPos = point
                    map[robotPos.x][robotPos.y] = '.'
                    continue
                }

                map[point.x][point.y] = 'O'
            }
        }
    }

    println(map.joinToString("\n") { it.joinToString(" ") })

    map.mapIndexed { r, row ->
        row.mapIndexed { c, value ->
            if (value == 'O') {
                100 * r + c
            } else {
                0
            }
        }.sum()
    }.sum()
        .apply {
            println("Part 1 $this")
        }
}
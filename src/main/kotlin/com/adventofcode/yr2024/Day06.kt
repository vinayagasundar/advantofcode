package com.adventofcode.yr2024

import com.adventofcode.Direction
import com.adventofcode.Direction.*
import com.adventofcode.readFileFromResources

fun main() {
    val inputFile = readFileFromResources("day06.txt").orEmpty()
    var (startX, startY) = 0 to 0
    var startDir = Up
    val map = inputFile.lineSequence()
        .map { it.split("").toTypedArray() }
        .onEachIndexed { i, array ->
            array.onEachIndexed { j, c ->
                getDirectionFromArrow(c)?.let { d ->
                    startX = i
                    startY = j
                    startDir = d
                }
            }
        }
        .toList()
    var (x, y, direction) = Triple(startX, startY, startDir)
    val seen = mutableSetOf<Pair<Int, Int>>()
    seen.add(x to y)
    while (!map.isEdge(x, y, direction)) {
        val (nx, ny) = x + direction.x to y + direction.y
        val next = map[nx][ny]

        if (next == "#") {
            direction = direction.turn90()
        } else {
            x = nx
            y = ny
        }
        seen.add(x to y)
    }
    println("Unique Points ${seen.size}")

    var counter = 0

    for ((ox, oy) in seen) {
        if (willLoop(map, startX, startY, startDir, ox, oy)) {
            counter += 1
        }
    }

    println("Loop Counter $counter")
}

private fun willLoop(
    map: List<Array<String>>,
    startX: Int,
    startY: Int,
    direction: Direction,
    ox: Int,
    oy: Int
): Boolean {
    if (map[ox][oy] == "#")
        return false

    map[ox][oy] = "#"

    var (x, y) = startX to startY
    var d = direction

    val seen = mutableSetOf<Triple<Int, Int, Direction>>()

    while (true) {
        if (map.isEdge(x, y, d)) {
            map[ox][oy] = "."
            return false
        }

        if (seen.add(Triple(x, y, d)).not()) {
            map[ox][oy] = "."
            return true
        }

        val (nx, ny) = x + d.x to y + d.y

        val next = map[nx][ny]

        if (next == "#") {
            d = d.turn90()
        } else {
            x = nx
            y = ny
        }
    }
}

private fun List<Array<String>>.isEdge(x: Int, y: Int, direction: Direction): Boolean {
    val mx = this.size - 1
    val my = this[0].size - 1
    return when (direction) {
        Up -> x == 0
        Left -> y == 0
        Right -> y == my
        Down -> x == mx
    }
}

private fun getDirectionFromArrow(arrow: String): Direction? {
    return when (arrow) {
        "^" -> Up
        "v" -> Down
        ">" -> Right
        "<" -> Left
        else -> null
    }
}

package com.adventofcode.`2024`

enum class Direction(val x: Int, val y: Int) {
    Up(-1, 0),
    Down(1, 0),
    Left(0, -1),
    Right(0, 1);

    fun turn90(): Direction {
        return when (this) {
            Up -> Right
            Left -> Up
            Down -> Left
            Right -> Down
        }
    }

    companion object {
        fun getDirectionFromArrow(arrow: String): Direction? {
            return when (arrow) {
                "^" -> Up
                "v" -> Down
                ">" -> Right
                "<" -> Left
                else -> null
            }
        }
    }
}

fun main() {
    val inputFile = readFileFromResources("day06.txt").orEmpty()
    var (x, y) = 0 to 0
    var direction = Direction.Up
    val map = inputFile.lineSequence()
        .map { it.split("").toTypedArray() }
        .onEachIndexed { i, array ->
            array.onEachIndexed { j, c ->
                Direction.getDirectionFromArrow(c)?.let { d ->
                    x = i
                    y = j
                    direction = d
                }
            }
        }
        .toList()
    var count = 1
    val path = mutableListOf<String>()
    val visited = mutableSetOf<String>()
    visited.add("$x,$y")

    while (!map.isEdge(x, y, direction)) {
        path.add("$x,$y,$direction")
        val (nx, ny) = x + direction.x to y + direction.y
        val next = map[nx][ny]
//        if (next == "#") {
//            direction = direction.turn90()
//        } else if (next == "X" || next == ".") {
//            if (next == ".") {
//                count += 1
//            }
//            map[x][y] = "X"
//            x = nx
//            y = ny
//        }

        if (next == "#") {
            direction = direction.turn90()
        } else {
            x = nx
            y = ny
        }

        if (visited.add("$x,$y")) {
            count += 1
        }
    }
//
//    if (map[x][y] == ".") {
//        count += 1
//    }

    println("Count $count")
}

fun List<Array<String>>.isEdge(x: Int, y: Int, direction: Direction): Boolean {
    val mx = this.size - 1
    val my = this[0].size - 1
    return when (direction) {
        Direction.Up -> x == 0
        Direction.Left -> y == 0
        Direction.Right -> y == my
        Direction.Down -> x == mx
    }
}

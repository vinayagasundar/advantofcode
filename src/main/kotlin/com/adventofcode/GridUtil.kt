package com.adventofcode

data class Point(val x: Int, val y: Int)

fun List<IntArray>.isInMap(point: Point): Boolean {
    return point.x in 0..this.lastIndex && point.y in 0..this[point.x].lastIndex
}

fun List<IntArray>.getValue(point: Point): Int {
    return this[point.x][point.y]
}

fun Point.next(direction: Direction): Point {
    return Point(x + direction.x, y + direction.y)
}

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
        val FOUR_DIR = listOf(Up, Down, Right, Left)
    }
}

package com.adventofcode

data class Point(val x: Int, val y: Int)

fun List<IntArray>.isInMap(point: Point): Boolean {
    return point.x in 0..this.lastIndex && point.y in 0..this[point.x].lastIndex
}

@JvmName("charArrayInMap")
fun List<CharArray>.isInMap(point: Point): Boolean {
    return point.x in 0..this.lastIndex && point.y in 0..this[point.x].lastIndex
}

fun List<IntArray>.getValue(point: Point): Int {
    return this[point.x][point.y]
}

@JvmName("charArrayGetValue")
fun List<CharArray>.getValue(point: Point): Char {
    return this[point.x][point.y]
}


@JvmName("booleanArrayGetValue")
fun Array<BooleanArray>.getValue(point: Point): Boolean {
    return this[point.x][point.y]
}

fun Point.next(direction: Direction): Point {
    return Point(x + direction.x, y + direction.y)
}

@JvmName("setValueBooleanArray")
fun Array<BooleanArray>.setValue(point: Point, value: Boolean) {
    this[point.x][point.y] = value
}

val pointXComparator = Comparator<Point> { first, second ->
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

val pointYComparator = Comparator<Point> { first, second ->
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

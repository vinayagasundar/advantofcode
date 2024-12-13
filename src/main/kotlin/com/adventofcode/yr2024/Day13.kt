package com.adventofcode.yr2024

import com.adventofcode.readFileFromResources

fun main() {
    val regex = Regex("\\d+")

    operator fun <T> List<T>.component6(): T {
        return this[5]
    }

    readFileFromResources("day13.txt").orEmpty()
        .splitToSequence("\n\n")
        .map {
            val (ax, ay, bx, by, px, py) = regex.findAll(it).map { result -> result.value.toInt() }.toList()
            solvePartOne(ax, bx, ay, by, px, py)
        }
        .sum()
        .apply {
            println("Part 1 $this")
        }

    readFileFromResources("day13.txt").orEmpty()
        .splitToSequence("\n\n")
        .map {
            val (ax, ay, bx, by, px, py) = regex.findAll(it).map { result -> result.value.toInt() }.toList()
            solvePartTwo(ax, bx, ay, by, px.toDouble() + 10000000000000, py.toDouble() + 10000000000000)
        }
        .sum()
        .apply {
            println("Part 2 $this")
            println("Part 2 %.0f".format(this))
        }
}

private fun solvePartOne(
    ax: Int,
    bx: Int,
    ay: Int,
    by: Int,
    px: Int,
    py: Int
): Int {
    for (a in 1..100) {
        for (b in 1..100) {
            val (tx, ty) = (ax * a) + (bx * b) to (ay * a) + (by * b)
            if (tx == px && py == ty) {
                return (a * 3) + b
            } else if (tx > px || ty > py) {
                break
            }
        }
    }
    return 0
}

/**
 * px = (ax * a) + (bx * b)
 * py = (ay * a) + (by * b)
 */
private fun solvePartTwo(
    ax: Int,
    bx: Int,
    ay: Int,
    by: Int,
    px: Double,
    py: Double
): Double {
    val a = (px * by - py * bx) / (ax * by - ay * bx).toDouble()
    val b = (px - ax * a) / bx

    if (getFractionalPart(a) > 0 || getFractionalPart(b) > 0) {
        return 0.0
    }

    println("$a $b")

    return (a * 3) + b
}

private fun getFractionalPart(number: Double): Double {
    return number - number.toLong()
}

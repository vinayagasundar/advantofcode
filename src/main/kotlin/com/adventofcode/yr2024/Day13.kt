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
            for (a in 1..100) {
                for (b in 1..100) {
                    val (tx, ty) = (ax * a) + (bx * b) to (ay * a) + (by * b)
                    if (tx == px && py == ty) {
                        println("Found $a $b")
                        return@map (a * 3) + (b * 1)
                    } else if (tx > px || ty > py) {
                        break
                    }
                }
            }
            0
        }
        .sum()
        .apply {
            println("Result $this")
        }
}

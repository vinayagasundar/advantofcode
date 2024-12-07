package com.adventofcode.`2024`

import java.math.BigDecimal

fun main() {
    val input = readFileFromResources("day07.txt").orEmpty()
        .lineSequence()
        .map { line ->
            val (e, n) = line.split(":")
            e.toLong() to n.split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
        }
        .toList()

    println(input)

    var total: Long = 0

    input.onEach { (target, numbers) ->
        val p = LongArray(numbers.size)
        for (i in numbers.indices) {
            p[i] = 1
            while (p[i] <= numbers[i]) p[i] = p[i] * 10
        }
        if (backtrack(numbers, 0, numbers[0], target)) {
            total += target
        }
    }
    println(total)
}

fun backtrack(number: List<Long>, i: Int, total: Long, target: Long): Boolean {
    if (i == number.lastIndex) {
        return total == target
    }

    if (total > target) {
        return false
    }

    val j = i + 1

    val n = number[j]
    if (backtrack(number, j, total + n, target)) {
        return true
    }

    if (backtrack(number, j, total * n, target)) {
        return true
    }

    return false
}

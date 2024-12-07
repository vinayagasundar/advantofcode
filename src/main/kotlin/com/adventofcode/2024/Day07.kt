package com.adventofcode.`2024`

var hasCombineOperator = false

fun main() {
    val input = readFileFromResources("day07.txt").orEmpty()
        .lineSequence()
        .map { line ->
            val (e, n) = line.split(":")
            e.toLong() to n.split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
        }
        .toList()

    var total: Long = 0

    input.onEach { (target, numbers) ->
        if (backtrack(numbers, 0, numbers[0], target)) {
            total += target
        }
    }
    println("Part 1 $total")

    hasCombineOperator = true
    total = 0

    input.onEach { (target, numbers) ->
        if (backtrack(numbers, 0, numbers[0], target)) {
            total += target
        }
    }
    println("Part 2 $total")
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

    if (hasCombineOperator && backtrack(number, j, "$total$n".toLong(), target)) {
        return true
    }

    return false
}

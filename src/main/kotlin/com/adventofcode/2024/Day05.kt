package com.adventofcode.`2024`

fun main() {
    val inputFile = readFileFromResources("day05.txt").orEmpty()

    val pageOrderRule = inputFile.lines()
        .takeWhile { it.isNotEmpty() }
        .map { line ->
            val (x, y) = line.split("|").map { it.toInt() }
            x to y
        }
        .scan(mutableMapOf<Int, IntArray>()) { acc, (x, y) ->
            acc.apply {
                val value = acc.getOrDefault(x, intArrayOf())
                put(x, value.plus(y))
            }
        }
        .first()

    val printOrderList = inputFile.lines().reversed()
        .takeWhile { it.isNotEmpty() }
        .map { line ->
            line.split(",").map { it.toInt() }.toIntArray()
        }

    println("Part 1: ${partOne(printOrderList, pageOrderRule)}")
    println("Part 2: ${partTwo(printOrderList, pageOrderRule)}")
}

private fun partOne(
    printOrderList: List<IntArray>,
    pageOrderRule: MutableMap<Int, IntArray>
): Int {
    var sum = 0

    printOrderList.forEach { printOrder ->
        sum += getCenterValue(pageOrderRule, printOrder)
    }

    return sum
}

private fun partTwo(
    printOrderList: List<IntArray>,
    pageOrderRule: MutableMap<Int, IntArray>
): Int {
    var sum = 0

    printOrderList.forEach { printOrder ->
        sum += getCenterValue(pageOrderRule, printOrder, true)
    }

    return sum
}

private fun getCenterValue(
    pageOrderRule: MutableMap<Int, IntArray>,
    printOrder: IntArray,
    fixIncorrectOrder: Boolean = false
): Int {
    var i = 0
    var invalidCounter = 0

    outerloop@ while (i <= printOrder.size - 1) {
        val page = printOrder[i]

        var j = 0
        while (j < i) {
            val prevPage = printOrder[j]
            val isInvalid = pageOrderRule[page]?.contains(prevPage) == true

            if (isInvalid) {
                if (fixIncorrectOrder) {
                    invalidCounter++
                    printOrder[j] = page
                    printOrder[i] = prevPage
                    continue@outerloop
                }
                return 0
            }
            j++
        }
        i++
    }

    if (fixIncorrectOrder && invalidCounter == 0) {
        return 0
    }

    return printOrder[printOrder.size / 2]
}


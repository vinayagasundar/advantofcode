package com.adventofcode.yr2024

import com.adventofcode.readFileFromResources

fun main() {
    val inputFile = readFileFromResources("day02.txt")
    println("Part 1 ${getSafeReportCount(inputFile.orEmpty(), allowSingleBadLevel = false)}")
    println("Part 2 ${getSafeReportCount(inputFile.orEmpty(), allowSingleBadLevel = true)}")
}

private fun getSafeReportCount(input: String, allowSingleBadLevel: Boolean): Int {
    var safeReportCount = 0
    input.lines().onEach { line ->
        if (line.isEmpty()) return@onEach

        val levelList = line.split(" ").map { it.toInt() }
        val valid = isValidReport(levelList, ignoreBadLevel = allowSingleBadLevel)
        if (valid.not()) {
            return@onEach
        }
        safeReportCount += 1
    }

    return safeReportCount
}

private fun isValidReport(levelList: List<Int>, ignoreBadLevel: Boolean): Boolean {
    val isIncreaseOrder = levelList[0] < levelList[1]

    for (index in levelList.indices) {
        if (index == 0) {
            continue
        }
        val cur = levelList[index]
        val prev = levelList[index - 1]
        val diff = if (isIncreaseOrder) {
            cur - prev
        } else {
            prev - cur
        }

        val valid = diff in 1..3

        if (valid.not()) {
            if (ignoreBadLevel.not()) {
                return false
            }

            for (i in levelList.indices) {
                val updateLevelList = levelList.toMutableList().apply {
                    removeAt(i)
                }
                if (isValidReport(updateLevelList, ignoreBadLevel = false))
                    return true
            }
            return false
        }
    }

    return true
}

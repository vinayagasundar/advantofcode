package com.adventofcode.yr2023

import com.adventofcode.readFileFromResources

fun main() {
    val digitString = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    fun solve(lines: List<String>, includeDigitString: Boolean = false): Int {
        return lines.sumOf { line ->
            var firstNumberIndex = line.indexOfFirst { it.isDigit() }
            var lastNumberIndex = line.indexOfLast { it.isDigit() }

            var first = if (firstNumberIndex > -1) line[firstNumberIndex].digitToInt() else -1
            var last = if (lastNumberIndex > -1) line[lastNumberIndex].digitToInt() else -1

            if (includeDigitString) {
                for (n in digitString.indices) {
                    val number = digitString[n]
                    val f = line.indexOf(number)
                    val l = line.lastIndexOf(number)

                    if (firstNumberIndex == -1 || (f != -1 && firstNumberIndex > f)) {
                        firstNumberIndex = f
                        first = n + 1
                    }

                    if (lastNumberIndex == -1 || (l != -1 && lastNumberIndex < l)) {
                        lastNumberIndex = l
                        last = n + 1
                    }
                }
            }
            "$first$last".toInt()
        }
    }

    readFileFromResources("2023/day01.txt").orEmpty()
        .lines()
        .let {
            println("Part One ${solve(it, false)}")
            println("Part One ${solve(it, true)}")
        }
}
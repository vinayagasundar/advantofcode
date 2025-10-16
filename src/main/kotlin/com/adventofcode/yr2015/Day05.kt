package com.adventofcode.yr2015

import com.adventofcode.readFileFromResources

fun main() {
    val lineSequence = readFileFromResources("2015/day05.txt").orEmpty()
        .lineSequence()

    fun solveOne() {
        val restrictedString = listOf("ab", "cd", "pq", "xy")
        val doubleCharRegex = Regex("(.)\\1")
        val vowels = listOf("a", "e", "i", "o", "u")

        val result = lineSequence.count { line ->
            line.count { ch -> vowels.contains(ch.toString()) } >= 3 &&
                    line.contains(doubleCharRegex) &&
                    restrictedString.any { word -> line.contains(word) }.not()
        }

        println("Result -> $result")
    }

    fun solveTwo() {
        fun String.hasPair(): Boolean {
            var index = 0
            while (index + 2 < length) {
                val pair = substring(index, index + 2)
                val hasPair = substring(index + 2)
                    .also { it }
                    .contains(pair)

                if (hasPair) return true

                index += 1
            }

            return false
        }

        fun String.hasPattern(): Boolean {
            forEachIndexed { index, ch ->
                val findIndex = indexOf(ch, index + 2)

                val hasPattern = index + 2 == findIndex

                if (hasPattern) return true
            }

            return false
        }

        val result = lineSequence.count { line ->
            line.hasPair() && line.hasPattern()
        }

        println("Result -> $result")
    }

    solveOne()
    solveTwo()
}
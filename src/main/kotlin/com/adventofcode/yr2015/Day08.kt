package com.adventofcode.yr2015

import com.adventofcode.readFileFromResources
import kotlin.coroutines.coroutineContext

fun main() {

    val lineSeq = readFileFromResources("2015/day08.txt").orEmpty()
        .lineSequence()

    fun solveOne() {
        val hexRegex = Regex("\\\\x(.){2}")
        val backSlashAndDoubleQuote = Regex("\\\\(\\\\|\")")
        lineSeq.map { line ->
            val actualLength = line.length
            val inMemoryLength = line.substring(1)
                .run {
                    substring(0, length - 1)
                }
                .replace(backSlashAndDoubleQuote, "1")
                .replace(hexRegex, "1")
                .length
            actualLength - inMemoryLength
        }
            .sum()
            .also {
                println("Solution 1 : $it")
            }
    }

    fun solveTwo() {
        lineSeq.map { line ->
            val actualLength = line.length
            val encodedLength = line.map { ch ->
                when (ch.toString()) {
                    "\"" -> "\\\""
                    "\\" -> "\\\\"
                    else -> ch.toString()
                }
            }.joinToString("").length + 2
            encodedLength - actualLength
        }
            .sum()
            .also {
                println("Solution 2 : $it")
            }
    }

    solveOne()
    solveTwo()
}
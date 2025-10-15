package com.adventofcode.yr2015

import com.adventofcode.readFileFromResources

fun main() {

    readFileFromResources("2015/day01.txt").orEmpty()
        .splitToSequence("")
        .filter { it != "" }
        .foldIndexed(0) { index, acc, string ->
            val updateValue = acc + if (string == "(") 1 else -1
            updateValue.also {
                if (it == -1) {
                    println("Enter basement ${index + 1}")
                }
            }
        }
        .also {
            println("Result $it")
        }

}
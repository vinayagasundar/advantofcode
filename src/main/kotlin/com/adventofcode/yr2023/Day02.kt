package com.adventofcode.yr2023

import com.adventofcode.readFileFromResources



fun main() {
    fun String.getMax(): Int {
        return when (this) {
            "red" -> 12
            "green" -> 13
            else -> 14
        }
    }

    readFileFromResources("2023/day02.txt").orEmpty()
        .lineSequence()
        .mapIndexed { gameId, value ->
            val valid = value.substringAfter(":")
                .splitToSequence(";")
                .filter { it.isNotEmpty() }
                .all { rounds ->
                    rounds.split(",")
                        .filter { it.isNotEmpty() }
                        .all { record ->
                            val (count, color) = record.trim().split(" ")
                            count.toInt() <= color.getMax()
                        }
                }

            if (valid) {
                gameId + 1
            } else {
                0
            }

        }.sum()
        .let {
            println("$it")
        }
}
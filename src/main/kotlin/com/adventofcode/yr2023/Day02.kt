package com.adventofcode.yr2023

import com.adventofcode.readFileFromResources

private const val RED_COLOR = "red"
private const val GREEN_COLOR = "green"
private const val BLUE_COLOR = "blue"

fun main() {
    println("${findAllValidGame()}")
    println("${findMinimumCubePower()}")
}

private fun findMinimumCubePower(): Int {
    return readFileFromResources("2023/day02.txt").orEmpty()
        .lineSequence()
        .mapIndexed { gameId, value ->
            var maxRed = Int.MIN_VALUE
            var maxGreen = Int.MIN_VALUE
            var maxBlue = Int.MIN_VALUE
             value.substringAfter(":")
                .splitToSequence(";")
                .filter { it.isNotEmpty() }
                .forEach { rounds ->
                    rounds.split(",")
                        .filter { it.isNotEmpty() }
                        .forEach { record ->
                            val (count, color) = record.trim().split(" ")
                            val countInt = count.toInt()

                            if (color == RED_COLOR && maxRed < countInt) {
                                maxRed = countInt
                            }

                            if (color == BLUE_COLOR && maxBlue < countInt) {
                                maxBlue = countInt
                            }

                            if (color == GREEN_COLOR && maxGreen < countInt) {
                                maxGreen = countInt
                            }
                        }
                }

            println("$maxRed $maxGreen $maxBlue")

            maxRed * maxBlue * maxGreen
        }.sum()
}

private fun findAllValidGame(): Int {
    return readFileFromResources("2023/day02.txt").orEmpty()
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
}

private fun String.getMax(): Int {
    return when (this) {
        RED_COLOR -> 12
        GREEN_COLOR -> 13
        BLUE_COLOR -> 14
        else -> throw IllegalArgumentException("Invalid color $this")
    }
}

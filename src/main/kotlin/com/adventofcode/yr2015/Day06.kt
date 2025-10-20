package com.adventofcode.yr2015

import com.adventofcode.numberRegex
import com.adventofcode.readFileFromResources

fun main() {
    val instructions = readFileFromResources("2015/day06.txt").orEmpty()
        .lineSequence()
        .toList()

    fun solveOne() {
        val map: List<IntArray> = MutableList(1000) {
            IntArray(1000) { 0 }
        }

        instructions.onEach { instruction ->
            val (sx, sy, ex, ey) = numberRegex.findAll(instruction).map { it.value.toInt() }.toList()
            val turnOn = instruction.contains("on")
            val turnOff = instruction.contains("off")

            for (i in sx..ex) {
                for (j in sy..ey) {
                    val value = map[i][j]
                    when {
                        turnOn -> map[i][j] = 1
                        turnOff -> map[i][j] = 0
                        else -> map[i][j] = if (value == 1) 0 else 1
                    }
                }
            }
        }

        val result = map.sumOf { it.count { value -> value == 1 } }

        println("Result $result")
    }

    fun solveTwo() {
        val map: List<IntArray> = MutableList(1000) {
            IntArray(1000) { 0 }
        }

        instructions.onEach { instruction ->
            val (sx, sy, ex, ey) = numberRegex.findAll(instruction).map { it.value.toInt() }.toList()
            val turnOn = instruction.contains("on")
            val turnOff = instruction.contains("off")

            for (i in sx..ex) {
                for (j in sy..ey) {
                    val value = map[i][j]
                    when {
                        turnOn -> map[i][j] += 1
                        turnOff -> map[i][j] = (value - 1).takeIf { it > 0 } ?: 0
                        else -> map[i][j] += 2
                    }
                }
            }
        }

        val result = map.sumOf { it.sumOf { value -> value } }

        println("Result $result")
    }

    solveOne()
    solveTwo()

}
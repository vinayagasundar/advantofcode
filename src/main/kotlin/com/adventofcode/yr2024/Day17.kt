package com.adventofcode.yr2024

import com.adventofcode.readFileFromResources
import kotlin.math.pow

fun main() {
    val (regValue, instructionValue) = readFileFromResources("day17.txt").orEmpty()
        .split("\n\n")

    val regex = Regex("\\d+")
    var (a, b, c) = regex.findAll(regValue).map { it.value.toInt() }.toList()
    val instruction = regex.findAll(instructionValue).map { it.value.toInt() }.toList()

    println("$a $b $c")
    println(instruction)
    var p = 0
    val inr = 2

    fun combo(value: Int): Int {
        return when (value) {
            4 -> a
            5 -> b
            6 -> c
            7 -> throw IllegalArgumentException("Invalid Program")
            else -> value
        }
    }

    val result = mutableListOf<Int>()

    while (p <= instruction.lastIndex) {
        val code = instruction[p]
        val value = instruction[p + 1]

        when (code) {
            0 -> {
                a = (a / 2f.pow(combo(value)).toInt())
            }
            1 -> {
                b = b xor value
            }
            2 -> {
                b = combo(value) % 8
            }
            3 -> {
                if (a != 0) {
                    p = value
                    continue
                }
            }
            4 -> {
                b = b xor c
            }
            5 -> {
                result.add(combo(value) % 8)
            }
            6 -> {
                b = (a / 2f.pow(combo(value)).toInt())
            }
            7 -> {
                c = (a / 2f.pow(combo(value)).toInt())
            }
        }

        println("$code $value $a $b $c")
        p += inr
    }
    println("Result $result")
    println(result.joinToString(","))
}
package com.adventofcode.yr2024

import com.adventofcode.readFileFromResources

fun main() {
    val input = readFileFromResources("day11.txt").orEmpty()
    var stones = input.split(" ").filter { it.isNotBlank() }.map { it.toLong() }.toList()

    for (i in 0 until 25) {
        val updatedStone = mutableListOf<Long>()

        for (s in stones) {
            if (s == 0L) {
                updatedStone.add(1)
            } else if (s.toString().length % 2 == 0) {
                val str = s.toString()
                val mid = str.length / 2
                println(str)
                updatedStone.add(str.substring(0 until mid).toLong())
                updatedStone.add(str.substring(mid  until str.length).toLong())
            } else {
                updatedStone.add(s * 2024)
            }
        }

        stones = updatedStone
    }

    println(stones.size)
}
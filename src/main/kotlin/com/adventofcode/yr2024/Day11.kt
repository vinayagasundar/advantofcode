package com.adventofcode.yr2024

import com.adventofcode.readFileFromResources


fun main() {
    val input = readFileFromResources("day11.txt").orEmpty()
    val stones = input.split(" ").filter { it.isNotBlank() }.map { it.toLong() }.toList()

    val cache = mutableMapOf<String, Long>()
    val result = stones.map {
        calculateStone4(it, 75, cache)
    }.sum()

    println("Answer 1 $result")
}


private fun calculateStone4(stone: Long, blinkCount: Int, cache: MutableMap<String, Long>): Long {
    val key = "$stone,$blinkCount"
    if (cache.contains(key))
        return cache[key]!!

    if (blinkCount == 0) {
        cache[key] = 1
        return 1
    }

    if (stone == 0L) {
        return calculateStone4(1L, blinkCount - 1, cache).apply {
            cache[key] = this
        }
    }

    if (stone.toString().length % 2 == 0) {
        val value = stone.toString()
        val mid = value.length / 2

        var firstHalf = value.substring(0 until mid)
        var secondHalf = value.substring(mid until value.length)

        return (calculateStone4(firstHalf.toLong(), blinkCount - 1, cache) + calculateStone4(
            secondHalf.toLong(),
            blinkCount - 1,
            cache
        )).apply {
            cache[key] = this
        }
    }

    return calculateStone4(stone * 2024, blinkCount - 1, cache).apply {
        cache[key] = this
    }
}

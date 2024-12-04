package com.adventofcode.`2024`

fun main() {
    val input = readFileFromResources("day03.txt").orEmpty()
    val regex = Regex("mul\\([0-9]{1,3},[0-9]{1,3}\\)|do\\(\\)|don't\\(\\)")
    val sequence = regex.findAll(input)
    println("Length ${sequence.toList().size}")
    var skip = false
    val result =sequence.map {
        var result = 0
        if (it.value == "do()") {
            skip = false
            result = 0
        } else if (it.value == "don't()") {
            skip = true
            result = 0
        } else if (!skip) {
            val (a, b) = it.value.substring(4, it.value.length - 1).split(",").map { v -> v.toInt() }
            result = a * b
        }
        result
    }.sumOf { it }
}
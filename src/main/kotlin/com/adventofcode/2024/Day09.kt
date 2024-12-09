package com.adventofcode.`2024`

fun main() {
    var file = intArrayOf()
    var space = intArrayOf()
    readFileFromResources("day09.txt").orEmpty()
        .lines()
        .onEach { line ->
            line.split("").filter { it.isNotBlank() }.map { it.toInt() }.onEachIndexed { index, value ->
                if (index % 2 == 0) {
                    file = file.plus(value)
                } else {
                    space = space.plus(value)
                }
            }
        }

    var i = 0
    var j = file.lastIndex
    val answer = mutableListOf<Int>()

    while (i <= j) {
        val fs = file[i]
        for (f in 1..fs) {
            answer.add(i)
        }
        file[i] = 0

        var ss = space[i]

        while (ss > 0 && j > i) {
            if (file[j] == 0) {
                j--
                continue
            }

            answer.add(j)
            ss--
            file[j]--
        }
        i++
    }

    println(answer.map{ it.toLong()}.mapIndexed { i, v -> i * v  }.sum())
}
package com.adventofcode.yr2024

import com.adventofcode.readFileFromResources

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

    println(partOne(file.clone(), space.clone()))
    println(partTwo(file.clone(), space.clone()))
}

fun partOne(file: IntArray, space: IntArray): String {
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

    return answer.map { it.toLong() }.mapIndexed { i, v -> i * v }.sum().toString()
}

fun partTwo(file: IntArray, space: IntArray): String {
    var i = 0
    val answer = mutableListOf<Int>()
    val fillerList = mutableMapOf<Int, Int>()

    while (i < file.lastIndex) {
        val fs = file[i]
        for (f in 1..fs) {
            answer.add(i)
        }
        file[i] = -1

        val filler = fillerList.getOrDefault(i, -1)
        if (filler > 0) {
            for (f in 1..filler) {
                answer.add(0)
            }
        }

        var ss = space[i]

        var j = file.lastIndex
        while (j > i) {
            if (file[j] in 1..ss) {
                fillerList[j] = file[j]
                for (f in 1..file[j]) {
                    answer.add(j)
                    ss--
                }
                file[j] = -1
            } else {
                j--
            }
        }

        while (ss > 0) {
            answer.add(0)
            ss--
        }

        i++
    }

    return answer.map { it.toLong() }.mapIndexed { i, v -> i * v }.sum().toString()
}

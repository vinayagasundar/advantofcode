package com.adventofcode.yr2024

import com.adventofcode.readFileFromResources
import kotlin.math.abs

fun main() {
    val inputFile = readFileFromResources("day01.txt")
    val leftList = mutableListOf<Int>()
    val rightList = mutableListOf<Int>()
    inputFile.orEmpty().lines()
        .onEach { line ->
            val (l, r) = line.split("   ").map { it.toInt() }
            leftList.add(l)
            rightList.add(r)
        }

    leftList.sort()
    rightList.sort()

    var distanceTotal = 0

    for (i in leftList.indices) {
        distanceTotal += abs(leftList[i] - rightList[i])
    }

    var similarityScore = 0
    var j = 0
    val cacheSimilarityScoreByValue = mutableMapOf<Int, Int>()

    for (i in leftList.indices) {
        val lValue = leftList[i]

        if (cacheSimilarityScoreByValue.contains(lValue)) {
            similarityScore += cacheSimilarityScoreByValue.getOrDefault(lValue, 0)
            continue
        }

        if (j == rightList.size) {
            break
        }

        var repeatCount = 0

        while (j < rightList.size) {
            val rValue = rightList[j]

            if (lValue == rValue) {
                repeatCount += 1
            } else if (lValue < rValue) {
                break
            }

            j++
        }

        similarityScore += (lValue * repeatCount).apply {
            cacheSimilarityScoreByValue[lValue] = this
        }
    }

    println("Distance $distanceTotal")
    println("Similarity Score $similarityScore")
}
package com.adventofcode.yr2024

import com.adventofcode.readFileFromResources

fun main() {
    val inputFile = readFileFromResources("day04.txt").orEmpty()
    val matrix = inputFile.lines()
        .map {
            it.toCharArray()
        }
        .toList()

    println("Find Xmas ${findXmas(matrix)}")
    println("Find X-Mas symbol ${findXmasSymbol(matrix)}")
}

/**
 * X 0 1 2
 * 0 S . S
 * 1 . A .
 * 2 M . M
 */
fun findXmasSymbol(matrix: List<CharArray>): Int {
    val maxRow = matrix.size
    val maxColumn = matrix[0].size
    var rp = 0

    var xmasCounter = 0

    while (rp < maxRow) {
        var cp = 0

        while (cp < maxColumn) {
            val value = matrix[rp][cp]

            if (value == 'A') {
                val isForward = cp + 1 < maxColumn
                val isBackward = cp - 1 >= 0
                val isTop = rp - 1 >= 0
                val isBottom = rp + 1 < maxRow

                if (isForward && isBottom && isTop && isBackward) {
                    val v1 = "${matrix[rp - 1][cp - 1]}$value${matrix[rp + 1][cp + 1]}"
                    val v2 = "${matrix[rp + 1][cp - 1]}$value${matrix[rp - 1][cp + 1]}"
                    if (v1 in listOf("MAS", "SAM") && v2 in listOf("MAS", "SAM")) {
                        xmasCounter += 1
                    }
                }
            }
            cp++
        }
        rp++
    }

    return xmasCounter
}


/***
 * X 0 1 2 3 4 5 6
 * 0 S . . S . . S
 * 1 . A . A . A .
 * 2 . . M M M . .
 * 3 S A M X M A S
 * 4 . . M M M . .
 * 5 . A . A . A .
 * 6 S . . S . . S
 */
fun findXmas(matrix: List<CharArray>): Int {
    val maxRow = matrix.size
    val maxColumn = matrix[0].size
    var rp = 0

    var xmasCounter = 0

    while (rp < maxRow) {
        var cp = 0

        while (cp < maxColumn) {
            val value = matrix[rp][cp]

            if (value == 'X') {
                val isForward = cp + 3 < maxColumn
                if (isForward) {
                    if (matrix[rp][cp + 1] == 'M' && matrix[rp][cp + 2] == 'A' && matrix[rp][cp + 3] == 'S') {
                        xmasCounter += 1
                    }
                }

                // check backward
                val isBackward = cp - 3 >= 0
                if (isBackward) {
                    if (matrix[rp][cp - 1] == 'M' && matrix[rp][cp - 2] == 'A' && matrix[rp][cp - 3] == 'S') {
                        xmasCounter += 1
                    }
                }

                // check top
                val isTop = rp - 3 >= 0
                if (isTop) {
                    if (matrix[rp - 1][cp] == 'M' && matrix[rp - 2][cp] == 'A' && matrix[rp - 3][cp] == 'S') {
                        xmasCounter += 1
                    }
                }

                // check bottom
                val isBottom = rp + 3 < maxRow
                if (isBottom) {
                    if (matrix[rp + 1][cp] == 'M' && matrix[rp + 2][cp] == 'A' && matrix[rp + 3][cp] == 'S') {
                        xmasCounter += 1
                    }
                }

                // check top left
                if (isBackward && isTop) {
                    if (matrix[rp - 1][cp - 1] == 'M' && matrix[rp - 2][cp - 2] == 'A' && matrix[rp - 3][cp - 3] == 'S') {
                        xmasCounter += 1
                    }
                }

                // check top right
                if (isForward && isTop) {
                    if (matrix[rp - 1][cp + 1] == 'M' && matrix[rp - 2][cp + 2] == 'A' && matrix[rp - 3][cp + 3] == 'S') {
                        xmasCounter += 1
                    }
                }

                // check bottom left
                if (isBackward && isBottom) {
                    if (matrix[rp + 1][cp - 1] == 'M' && matrix[rp + 2][cp - 2] == 'A' && matrix[rp + 3][cp - 3] == 'S') {
                        xmasCounter += 1
                    }
                }

                // check bottom right
                if (isForward && isBottom) {
                    if (matrix[rp + 1][cp + 1] == 'M' && matrix[rp + 2][cp + 2] == 'A' && matrix[rp + 3][cp + 3] == 'S') {
                        xmasCounter += 1
                    }
                }
            }
            cp++
        }
        rp++
    }

    return xmasCounter
}


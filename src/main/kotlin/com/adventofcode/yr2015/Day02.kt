package com.adventofcode.yr2015

import com.adventofcode.numberRegex
import com.adventofcode.readFileFromResources

fun main() {
    val lineSequence = readFileFromResources("2015/day02.txt").orEmpty()
        .lineSequence()

    lineSequence
        .sumOf { line ->
            val (l, w, h) = numberRegex.findAll(line).map { it.value.toInt() }.toList()
            val lw = l * w
            val wh = w * h
            val hl = h * l
            val minSide = minOf(lw, wh, hl)

            2 * lw + 2 * wh + 2 * hl + minSide
        }
        .let {
            println("Gift Wrapper -> $it")
        }

    lineSequence
        .sumOf { line ->
            val (l, w, h) = numberRegex.findAll(line).map { it.value.toInt() }.toList()
            val bow = l * w * h
            val side = listOf(l, w, h).sorted().mapIndexed { index, i -> if (index > 1) 0 else 2 * i }.sum()
            bow + side
        }
        .let {
            println("Ribbon -> $it")
        }
}
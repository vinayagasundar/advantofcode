package com.adventofcode

import com.adventofcode.Direction.*

enum class Direction(val x: Int, val y: Int) {
    Up(-1, 0),
    Down(1, 0),
    Left(0, -1),
    Right(0, 1);

    fun turn90(): Direction {
        return when (this) {
            Up -> Right
            Left -> Up
            Down -> Left
            Right -> Down
        }
    }
}

fun Direction.clockwise(): Direction {
    return when (this) {
        Up -> Right
        Left -> Up
        Down -> Left
        Right -> Down
    }
}

fun Direction.antiClockwise(): Direction {
    return when (this) {
        Up -> Left
        Left -> Down
        Down -> Right
        Right -> Up
    }
}

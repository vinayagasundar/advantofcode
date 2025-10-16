package com.adventofcode.yr2015

import com.adventofcode.Point
import com.adventofcode.next
import com.adventofcode.toDirection
import com.adventofcode.readFileFromResources

fun main() {
    // Solution Problem 1
    var current = Point(0, 0)
    val trackedLocation = mutableListOf<Point>()
        .apply { add(current) }

    readFileFromResources("2015/day03.txt").orEmpty()
        .onEachIndexed { index, char ->
            val direction = char.toDirection()
            current = current.next(direction)
            trackedLocation.add(current)
        }

    println("Result -> ${trackedLocation.toSet().count()}")

    trackedLocation.clear()
    // Solution Problem 2

    var santaCurrentLocation = Point(0, 0)
    var santaRoboCurrentLocation = Point(0, 0)

    readFileFromResources("2015/day03.txt").orEmpty()
        .onEachIndexed { index, char ->
            val direction = char.toDirection()
            if (index % 2 == 0) {
                santaCurrentLocation = santaCurrentLocation.next(direction)
                trackedLocation.add(santaCurrentLocation)
            } else {
                santaRoboCurrentLocation = santaRoboCurrentLocation.next(direction)
                trackedLocation.add(santaRoboCurrentLocation)
            }
        }

    println("Result -> ${trackedLocation.toSet().count()}")
}
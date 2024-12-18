package com.adventofcode.yr2024

import com.adventofcode.*
import java.util.PriorityQueue
import kotlin.math.min

fun main() {
    val obstaclePoint = readFileFromResources("day18.txt").orEmpty()
        .lineSequence()
        .map {
            val (x, y) = it.split(",").map { value -> value.toInt() }
            Point(x, y)
        }.toList()

    val maxBytesCount = 1024
    println(travelBfs(obstaclePoint, maxBytesCount))

    for (i in 1025 until obstaclePoint.size) {
        println("$i")
        if (travelBfs(obstaclePoint, i) == Int.MAX_VALUE) {
            println(obstaclePoint[i])
            break
        }
    }
}

private fun travelBfs(obstaclePointList: List<Point>, noOfObstacle: Int): Int {
    val rows = 71
    val cols = 71
    val grid = Array(rows) {
        CharArray(cols) {
            '.'
        }
    }

    for (i in obstaclePointList.indices) {
        if (i > noOfObstacle) {
            break
        }
        val point = obstaclePointList[i]
        grid[point.x][point.y] = '#'
    }

    val visited = mutableListOf<Point>()

    data class Node(
        val point: Point,
        val distance: Int,
    ) : Comparable<Node> {
        override fun compareTo(other: Node): Int {
            return this.distance.compareTo(other.distance)
        }
    }
    val start = Point(0, 0)
    val end = Point(grid.lastIndex, grid[0].lastIndex)

    val queue = PriorityQueue<Node>()
    queue.add(Node(start, 0))
    var minDistance = Int.MAX_VALUE

    while (!queue.isEmpty()) {
        val node = queue.poll()

        if (node.point == end) {
            minDistance = min(minDistance, node.distance)
            break
        }

        if (!(node.point.x in grid.indices && node.point.y in grid[node.point.x].indices)) {
            continue
        }

        if (visited.contains(node.point)) {
            continue
        }

        if (grid[node.point.x][node.point.y] == '#') {
            continue
        }

        visited.add(node.point)

        for (d in Direction.entries) {
            queue.add(Node(node.point.next(d), node.distance + 1))
        }
    }

    return minDistance
}

package com.adventofcode.yr2024

import com.adventofcode.*
import java.util.PriorityQueue
import kotlin.math.min

fun main() {
    var startPoint = Point(0, 0)
    var endPoint = Point(0, 0)
    val map = readFileFromResources("day16.txt").orEmpty()
        .lineSequence()
        .mapIndexed { r, line ->
            line.forEachIndexed { c, value ->
                if (value == 'S') {
                    startPoint = Point(r, c)
                } else if (value == 'E') {
                    endPoint = Point(r, c)
                }
            }
            line.toCharArray()
        }.toList()

    fun travelBfs(start: Point, end: Point): Int {

        val visited = mutableListOf<String>()

        data class Node(
            val point: Point,
            val direction: Direction,
            val distance: Int,
        ) : Comparable<Node> {
            val key: String
                get() = "$point,$direction"

            override fun compareTo(other: Node): Int {
                return this.distance.compareTo(other.distance)
            }
        }

        val queue = PriorityQueue<Node>()
        queue.add(Node(start, Direction.Right, 0))

        var minDistance = Int.MAX_VALUE

        while (!queue.isEmpty()) {
            val node = queue.poll()
            println("${node.distance} ${node.point} ${node.direction}")

            if (node.point == end) {
                minDistance = min(minDistance, node.distance)
                break
            }

            if (visited.contains(node.key)) {
                continue
            }

            visited.add(node.key)

            val nextPoint = node.point.next(node.direction)
            val value = map.getValue(nextPoint)


            if (value != '#') {
                queue.add(Node(nextPoint, node.direction, node.distance + 1))
            }

            queue.add(Node(node.point, node.direction.clockwise(), node.distance + 1000))
            queue.add(Node(node.point, node.direction.antiClockwise(), node.distance + 1000))
        }

        return minDistance
    }

    println("Part 1 ${travelBfs(startPoint, endPoint)}")
}
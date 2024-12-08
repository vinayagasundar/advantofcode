package com.adventofcode.`2024`


fun main() {
    val antenna = mutableMapOf<String, List<Pair<Int, Int>>>()
    val input = readFileFromResources("day08.txt").orEmpty()
        .lineSequence()
        .map { line ->
            line.split("").filter { it.isNotBlank() }.toTypedArray()
        }
        .onEachIndexed { r, list ->
            list.onEachIndexed { c, s ->
                if (s != ".") {
                    val antennaPosition = antenna.getOrDefault(s, emptyList()).toMutableList()
                    antennaPosition.add(r to c)
                    antenna[s] = antennaPosition
                }
            }
        }
        .toList()

    val maxRow = input.lastIndex
    val maxColumn = input[0].lastIndex

    val seen = mutableSetOf<Pair<Int, Int>>()

    fun Pair<Int, Int>.inMatrix(): Boolean {
        val (r, c) = this
        return r in 0..maxRow && c in 0..maxColumn
    }

    antenna.onEach { (_, positions) ->
        positions.onEachIndexed { i, selPos ->

            for (j in i + 1..positions.lastIndex) {
                val comPos = positions[j]
                if (selPos == comPos || (selPos.first == comPos.first || selPos.second == comPos.second)) {
                    continue
                }

                val dr = selPos.first - comPos.first
                val dc = selPos.second - comPos.second

                val p1 = selPos.first + dr to selPos.second + dc
                val p2 = comPos.first - dr to comPos.second - dc

                if (p1.inMatrix()) {
                    seen.add(p1)
                }

                if (p2.inMatrix()) {
                    seen.add(p2)
                }
            }
        }
    }

    println("Part 1 ${seen.size}")
}

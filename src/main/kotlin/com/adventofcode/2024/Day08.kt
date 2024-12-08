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

    fun Pair<Int, Int>.inMatrix(): Boolean {
        val (r, c) = this
        return r in 0..maxRow && c in 0..maxColumn
    }

    fun solve(part2: Boolean = false): Int {
        val seen = mutableSetOf<Pair<Int, Int>>()
        antenna.onEach { (_, positions) ->
            positions.onEachIndexed { i, selPos ->
                for (j in i + 1..positions.lastIndex) {
                    val comPos = positions[j]
                    if (selPos == comPos) {
                        continue
                    }

                    val dr = selPos.first - comPos.first
                    val dc = selPos.second - comPos.second

                    if (part2) {
                        seen.add(selPos)
                        seen.add(comPos)
                    }

                    var m = 1
                    while (true) {
                        val p1 = selPos.first + (dr * m) to selPos.second + (dc * m)
                        val p2 = comPos.first - (dr * m) to comPos.second - (dc * m)

                        if (p1.inMatrix()) {
                            seen.add(p1)
                        }

                        if (p2.inMatrix()) {
                            seen.add(p2)
                        }

                        if (part2.not()) {
                            break
                        }

                        if (p1.inMatrix().not() && p2.inMatrix().not()) {
                            break
                        }

                        m += 1
                    }
                }
            }
        }

        return seen.size
    }

    println("Part 1 ${solve()}")
    println("Part 2 ${solve(true)}")
}

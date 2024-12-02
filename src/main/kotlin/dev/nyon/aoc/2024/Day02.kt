package dev.nyon.aoc.`2024`

import dev.nyon.aoc.day

fun main() = day(2, 2024) {
    test1Expected = 2
    test2Expected = 4

    val reports = inputLines.map { line ->
        line.split(" ").map(String::toInt)
    }

    fun valid(levels: List<Int>, decreasing: Boolean): Boolean {
        return levels.windowed(2).none { (one, two) ->
            val diff = if (decreasing) one - two else two - one
            diff !in 1..3
        }
    }

    part1 {
        reports.count { levels ->
            valid(levels, levels[0] > levels[1])
        }
    }

    part2 {
        reports.count { levels ->
            val decreasing = levels.windowed(2).sumOf { (one, two) -> if (one > two) -1.0 else 1.0 } < 0
            levels.indices.any { index ->
                valid(levels.toMutableList().apply { removeAt(index) }, decreasing)
            }
        }
    }
}
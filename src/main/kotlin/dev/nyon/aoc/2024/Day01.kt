package dev.nyon.aoc.`2024`

import dev.nyon.aoc.day
import kotlin.math.abs

fun main() = day(1, 2024) {
    test1Expected = 11
    test2Expected = 31

    val (firstColumn, secondColumn) = (0 .. 1).map {
        inputLines.map { line -> line.split("   ")[it].toInt() }.sorted()
    }

    part1 {
        firstColumn.mapIndexed { index, i ->
            abs(secondColumn[index] - i)
        }.sum()
    }

    part2 {
        firstColumn.sumOf { i ->
            i * secondColumn.count { it == i }
        }
    }
}
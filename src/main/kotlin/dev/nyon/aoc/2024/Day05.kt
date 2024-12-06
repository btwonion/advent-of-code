package dev.nyon.aoc.`2024`

import dev.nyon.aoc.day

fun main() = day(5, 2024) {
    test1Expected = 143
    test2Expected = 123

    val rules = inputLines
        .takeWhile(String::isNotEmpty)
        .map { val split = it.split("|").map(String::toInt); split[0] to split[1] }
        .groupBy(Pair<Int, Int>::second, Pair<Int, Int>::first)

    val numbers = inputLines.takeLastWhile(String::isNotEmpty).map { it.split(",").map(String::toInt) }

    fun valid(line: List<Int>): Boolean {
        return line.filterIndexed { index, single ->
            val singleRules = rules[single] ?: return@filterIndexed false
            line.drop(index + 1).any { singleRules.contains(it) }
        }.isEmpty()
    }

    part1 {
        numbers.filter(::valid).sumOf { it[it.size / 2] }
    }

    part2 {
        numbers
            .filterNot(::valid)
            .map {
                it.sortedWith { first, second ->
                    val firstRules = rules[first] ?: return@sortedWith 0
                    if (firstRules.contains(second)) -1 else 1
                }
            }.sumOf { it[it.size / 2] }
    }
}
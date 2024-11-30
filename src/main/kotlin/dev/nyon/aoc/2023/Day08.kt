package dev.nyon.aoc.`2023`

import dev.nyon.aoc.day

/**
 * @author btwonion
 * @since 08/12/2023
 */
fun main() = day(8, 2023) {
    test1Expected = 2
    test2Expected = 6

    fun gcd(x: Long, y: Long): Long {
        var a = x
        var b = y
        while (b != 0L) a = b.also { b = a.mod(b) }
        return a
    }

    fun lcm(x: Long, y: Long): Long = x / gcd(x, y) * y

    fun String.parseWays(): Pair<String, String> {
        val (left, right) = drop(7).dropLast(1).split(", ")
        return left to right
    }

    val instructions = inputLines.first()
    val directions = inputLines.drop(2).associate { it.take(3) to it.parseWays() }

    fun step(start: String) = instructions.fold(start) { loc, char ->
        when (char) {
            'L' -> directions[loc]!!.first
            'R' -> directions[loc]!!.second
            else -> throw IllegalStateException()
        }
    }

    part1 {
        instructions.length * generateSequence("AAA", ::step).indexOf("ZZZ")
    }

    // Credits to: https://github.com/ephemient/aoc2023/
    // Couldn't figure it out myself.
    part2 {
        val instructionsNeeded = directions.keys.filter { it.endsWith('A') }.fold(1L) { count, start ->
            val index = generateSequence(start, ::step).withIndex().first { (_, end) -> end.endsWith('Z') }.index
            lcm(count, index.toLong())
        }
        instructionsNeeded * instructions.length
    }
}
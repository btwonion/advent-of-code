package dev.nyon.aoc.`2023`

import dev.nyon.aoc.day

fun main() = day(12, 2023) {
    test1Expected = 21
    test2Expected = 525152

    val knownPatterns = mutableMapOf<Pair<String, List<Int>>, Long>()
    fun getArrangementCount(record: String, pattern: List<Int>): Long {
        if (record.isEmpty() && pattern.isEmpty()) return 1
        if (record.isEmpty()) return 0
        if (pattern.isEmpty() && record.none { it == '#' }) return 1
        if (pattern.isEmpty()) return 0

        knownPatterns[record to pattern]?.let { return it }
        val count = when (record.first()) {
            '.' -> getArrangementCount(record.drop(1), pattern)
            '#' -> {
                val nextSize = pattern.first()
                if (record.length < nextSize) return 0
                val noDotsInWay = record.take(nextSize).none { char -> char == '.' }
                val matchesLength = record.length == nextSize || record[nextSize] != '#'
                return if (noDotsInWay && matchesLength) getArrangementCount(record.drop(nextSize + 1), pattern.drop(1))
                else 0
            }
            '?' -> getArrangementCount(record.replaceFirstChar { _ -> '.' }, pattern) +
                getArrangementCount(record.replaceFirstChar { _ -> '#' }, pattern)
            else -> 0
        }
        knownPatterns.put(record to pattern, count)
        return count
    }

    part1 {
        val recordsToPattern = inputLines.map { string ->
            val split = string.split(' ')
            split[0] to split[1].split(',').map { it.toInt() }
        }

        recordsToPattern.sumOf { (record, pattern) ->
            getArrangementCount(record, pattern)
        }
    }

    part2 {
        val recordsToPattern = inputLines.map { string ->
            val split = string.split(' ')
            List(5) { split[0] }.joinToString(separator = "?", prefix = "", postfix = "") to
                List(5) { split[1].split(',').map { it.toInt() } }.flatten()
        }

        recordsToPattern.sumOf { (record, pattern) ->
            getArrangementCount(record, pattern)
        }
    }
}
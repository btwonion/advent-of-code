package dev.nyon.aoc.`2023`

import dev.nyon.aoc.day

/**
 * @author btwonion
 * @since 06/12/2023
 */
fun main() = day(6, 2023) {
    test1Expected = 288
    test2Expected = 71503

    fun possibleWinCounts(time: Long, record: Long): Int {
        var couldWin = 0
        (0..time).forEach {
            if (it * (time - it) > record) couldWin++
        }
        return couldWin
    }

    part1 {
        val times = inputLines.first().drop(11).split(' ').filterNot { it.isEmpty() }.map { it.toInt() }
        val records = inputLines[1].drop(11).split(' ').filterNot { it.isEmpty() }.map { it.toInt() }
        // List of time - distance
        val games = times.mapIndexed { index, time ->
            time to records[index]
        }

        var margin = 0
        games.forEach { (time, record) ->
            val possibleWinCounts = possibleWinCounts(time.toLong(), record.toLong())
            if (margin == 0) margin = possibleWinCounts
            else margin *= possibleWinCounts
        }

        margin
    }

    part2 {
        val (time, record) = inputLines.map { it.drop(11).replace(" ", "").toLong() }

        possibleWinCounts(time, record)
    }
}
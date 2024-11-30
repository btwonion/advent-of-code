package dev.nyon.aoc.`2023`

import dev.nyon.aoc.day
import kotlin.math.abs

fun main() = day(11, 2023) {
    test1Expected = 374
    // 100x
    test2Expected = 8410

    // Returns each index where an empty line is.
    // Horizontal - vertical
    fun parseEmpties(): Pair<List<Int>, List<Int>> {
        // Correctly size horizontal lines
        val horizontal = inputLines.mapIndexedNotNull { index, line ->
            if (line.all { char -> char == '.' }) return@mapIndexedNotNull index
            null
        }

        // Correctly size vertical lines
        val vertical = inputLines.first().mapIndexedNotNull { index, line ->
            val verticalLine = inputLines.map { otherLine -> otherLine[index] }
            if (verticalLine.all { char -> char == '.' }) return@mapIndexedNotNull index
            null
        }
        return horizontal to vertical
    }

    fun calculateDistance(multiplier: Int): Long {
        val empties = parseEmpties()

        val galaxies = buildList {
            inputLines.forEachIndexed { lineIndex, line ->
                line.forEachIndexed { charIndex, char ->
                    if (char == '#') add(lineIndex to charIndex)
                }
            }
        }

        val calculatedPaths = mutableListOf<Pair<Int, Int>>()
        return galaxies.sumOf outerSum@{ start ->
            calculatedPaths.add(start)
            galaxies.sumOf innerSum@{ target ->
                if (calculatedPaths.contains(target)) return@innerSum 0
                val horizontalIntersections = empties.first.count { index ->
                    index > start.first && index < target.first || index > target.first && index < start.first
                }
                val verticalIntersections = empties.second.count { index ->
                    index > start.second && index < target.second || index > target.second && index < start.second
                }

                (abs(target.first - start.first) + horizontalIntersections * multiplier + abs(target.second - start.second) + verticalIntersections * multiplier).toLong()
            }
        }
    }

    part1 {
        calculateDistance(1)
    }

    part2 {
        calculateDistance(99)
    }
}
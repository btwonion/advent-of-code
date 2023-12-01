package dev.nyon.aoc

/**
 * @author btwonion
 * @since 01/12/2023
 */
fun main() = day(1) {
    test1Expected = 142
    test2Expected = 281

    val numberNames = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    fun solve(): Any {
        return inputLines.sumOf { line ->
            val numbers: List<Int> = line.mapIndexedNotNull { index, char ->
                if (char.isDigit()) return@mapIndexedNotNull char.digitToInt()
                numberNames.indexOfFirst { line.drop(index).startsWith(it) }
                    .also { return@mapIndexedNotNull if (it == -1) null else it + 1 }
            }

            "${numbers.first()}${numbers.last()}".toInt()
        }
    }

    part1 { solve() }

    part2 { solve() }
}
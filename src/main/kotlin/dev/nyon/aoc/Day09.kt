package dev.nyon.aoc

/**
 * @author btwonion
 * @since 09/12/2023
 */
fun main() = day(9) {
    test1Expected = 114
    test2Expected = 2

    val histories = inputLines.map { line -> line.split(' ').map { it.toInt() } }

    fun List<Int>.parseLayers(): List<List<Int>> {
        val layers = mutableListOf(this)

        while (!layers.last().all { it == 0 }) {
            val newLayer = buildList {
                layers.last().windowed(2) { (int1, int2) ->
                    add(int2 - int1)
                }
            }
            layers.add(newLayer)
        }

        return layers
    }

    fun solve(acc: (lastInt: Int, ints: List<Int>) -> Int): Long {
        return histories.sumOf { values ->
            val layers = values.parseLayers()

            val nextInt = layers.dropLast(1).reversed().fold(0) { previousInt, ints ->
                acc(previousInt, ints)
            }

            nextInt.toLong()
        }
    }

    part1 {
        solve { lastInt, ints ->
            lastInt + ints.last()
        }
    }

    part2 {
        solve { lastInt, ints ->
            ints.first() - lastInt
        }
    }
}
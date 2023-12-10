package dev.nyon.aoc

/**
 * @author btwonion
 * @since 10/12/2023
 */
fun main() = day(10) {
    test1Expected = 8
    test2Expected = 10

    val charMap = mapOf(
        '|' to ('N' to 'S'),
        '-' to ('W' to 'E'),
        'F' to ('S' to 'E'),
        '7' to ('W' to 'S'),
        'J' to ('N' to 'W'),
        'L' to ('N' to 'E')
    )

    fun Char.nextDirection(previous: Char): Char? {
        val directionPair = charMap[this] ?: return null
        val transformedPrevious = when (previous) {
            'N' -> 'S'
            'S' -> 'N'
            'E' -> 'W'
            'W' -> 'E'
            else -> return null
        }
        return if (directionPair.first == transformedPrevious) directionPair.second
        else if (directionPair.second == transformedPrevious) directionPair.first
        else null
    }

    /**
     * @return initial indexes
     */
    fun runLoop(callback: Pair<Int, Int>.() -> Unit): Pair<Int, Int> {
        // Line index - Char index
        val initialIndexes = inputLines.indexOfFirst { it.contains('S') }.run {
            this@run to inputLines[this@run].indexOf('S')
        }
        var nextDirection = listOf(
            initialIndexes.first to initialIndexes.second - 1,
            initialIndexes.first to initialIndexes.second + 1,
            initialIndexes.first - 1 to initialIndexes.second,
            initialIndexes.first + 1 to initialIndexes.second
        ).mapNotNull { (lineIndex, charIndex) ->
            val nextChar = inputLines.getOrNull(lineIndex)?.getOrNull(charIndex) ?: return@mapNotNull null
            val nextDirection = nextChar.nextDirection(
                when {
                    lineIndex == initialIndexes.first -> {
                        if (charIndex > initialIndexes.second) 'E'
                        else 'W'
                    }

                    lineIndex > initialIndexes.first -> 'N'
                    else -> 'S'
                }
            )
            if (nextDirection == null) return@mapNotNull null
            nextDirection to (lineIndex to charIndex)
        }.first()
        callback(nextDirection.second)

        while (true) {
            val nextIndexPair = when (nextDirection.first) {
                'N' -> nextDirection.second.first - 1 to nextDirection.second.second
                'S' -> nextDirection.second.first + 1 to nextDirection.second.second
                'W' -> nextDirection.second.first to nextDirection.second.second - 1
                'E' -> nextDirection.second.first to nextDirection.second.second + 1
                else -> throw IllegalStateException()
            }
            val nextChar = inputLines[nextIndexPair.first][nextIndexPair.second]
            if (nextChar != 'S') nextDirection = nextChar.nextDirection(nextDirection.first)!! to nextIndexPair
            callback(nextIndexPair)
            if (nextChar == 'S') break
        }

        return initialIndexes
    }

    part1 {
        var loopSize = 0

        runLoop {
            loopSize++
        }

        loopSize / 2
    }

    // Try to make it work... nevermind
    part2 {
        val lineIndexes = mutableMapOf<Int, MutableList<Int>>()

        runLoop {
            if (lineIndexes[first] == null) lineIndexes[first] = mutableListOf(second)
            else lineIndexes[first]!!.add(second)
        }.also {
            if (lineIndexes[it.first] == null) lineIndexes[it.first] = mutableListOf(it.second)
            else lineIndexes[it.first]!!.add(it.second)
        }

        val sorted = lineIndexes.values.map { it.sorted() }

        println(sorted)

        sorted.sumOf { loopIndexes ->
            var included = 0
            if (loopIndexes.size % 2 == 0) loopIndexes.chunked(2).forEach { (index1, index2) ->
                included += index2 - index1 + 1
            }
            else loopIndexes.windowed(2) { (index1, index2) ->
                included += index2 - index1 + 1
            }
            included
        }
    }
}
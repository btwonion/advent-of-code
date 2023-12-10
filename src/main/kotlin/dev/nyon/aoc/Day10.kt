package dev.nyon.aoc

/**
 * @author btwonion
 * @since 10/12/2023
 */
fun main() = day(10) {
    test1Expected = 8

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


    part1 {
        var loopSize = 0

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
        loopSize++

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
            loopSize++
            if (nextChar == 'S') break
        }

        loopSize / 2
    }
}
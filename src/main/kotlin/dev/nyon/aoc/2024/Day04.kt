package dev.nyon.aoc.`2024`

import dev.nyon.aoc.day

// No clean code today!!! I'm tired
fun main() = day(4, 2024) {
    test1Expected = 18
    test2Expected = 9

    fun lookForSurrounding(
        char: Char,
        currentPosition: Pair<Int, Int>,
        lineOffset: Int?,
        charOffset: Int?
    ): List<Pair<Int, Int>> {
        val maxLineLength = inputLines.first().length
        val lineAmount = inputLines.size
        val possiblePositions = if (charOffset == null || lineOffset == null) (-1 .. 1).map { line ->
            (-1 .. 1).map { char ->
                currentPosition.first + line to currentPosition.second + char
            }
        }.flatten() else listOf(currentPosition.first + lineOffset to currentPosition.second + charOffset)
        return possiblePositions.mapNotNull { (lineIndex, charIndex) ->
            if (lineIndex < 0 || charIndex < 0 || lineIndex >= lineAmount || charIndex >= maxLineLength) return@mapNotNull null
            val foundChar = inputLines[lineIndex][charIndex]
            return@mapNotNull if (foundChar != char) null
            else lineIndex to charIndex
        }
    }

    part1 {
        var counter = 0
        inputLines.forEachIndexed lineLoop@{ lIndex, line ->
            line.forEachIndexed charLoop@{ cIndex, char ->
                if (char != 'X') return@charLoop
                val mResults = lookForSurrounding('M', lIndex to cIndex, null, null)
                mResults.forEach { (foundL, foundC) ->
                    val lOffset = foundL - lIndex
                    val cOffset = foundC - cIndex
                    if (lookForSurrounding(
                            'A',
                            lIndex to cIndex,
                            2 * lOffset,
                            2 * cOffset
                        ).isNotEmpty() && lookForSurrounding(
                            'S',
                            lIndex to cIndex,
                            3 * lOffset,
                            3 * cOffset
                        ).isNotEmpty()
                    ) counter++
                }
            }
        }
        counter
    }

    part2 {
        var counter = 0
        inputLines.forEachIndexed lineLoop@{ lIndex, line ->
            line.forEachIndexed charLoop@{ cIndex, char ->
                if (char != 'A') return@charLoop
                val (foundL, foundC) = lookForSurrounding('M', lIndex to cIndex, null, null).firstOrNull { (l, c) -> c != cIndex && l != lIndex }
                    ?: return@charLoop
                val lOffset = foundL - lIndex
                val cOffset = foundC - cIndex
                if (
                    lookForSurrounding('S', lIndex to cIndex, -lOffset, -cOffset).isNotEmpty() &&
                    (lookForSurrounding('S', lIndex to cIndex, lOffset, -cOffset).isNotEmpty() && lookForSurrounding('M', lIndex to cIndex, -lOffset, cOffset).isNotEmpty()
                        || lookForSurrounding('M', lIndex to cIndex, lOffset, -cOffset).isNotEmpty() && lookForSurrounding('S', lIndex to cIndex, -lOffset, cOffset).isNotEmpty())
                    ) counter++
            }
        }
        counter
    }
}
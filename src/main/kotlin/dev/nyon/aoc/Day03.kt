package dev.nyon.aoc

/**
 * @author btwonion
 * @since 03/12/2023
 */
fun main() = day(3) {
    test1Expected = 4361
    test2Expected = 467835

    fun String.calculateNumbers(): List<Pair<IntRange, Int>> {
        val numbers = mutableListOf<Pair<IntRange, Int>>()
        forEachIndexed charIterator@{ index, char ->
            if (numbers.any { it.first.contains(index) }) return@charIterator
            if (!char.isDigit()) return@charIterator
            val digitString = drop(index).takeWhile { it.isDigit() }
            numbers.add((index..<index + digitString.length) to digitString.toInt())
        }
        return numbers
    }

    part1 {
        val allowedNumbers = mutableListOf<Int>()

        inputLines.forEachIndexed { lineIndex, line ->
            val possibleNumbers = line.calculateNumbers()

            possibleNumbers.forEach { (indexes, number) ->
                val possibleLines = buildList {
                    if (inputLines.getOrNull(lineIndex - 1) != null) add(inputLines[lineIndex - 1])
                    add(line)
                    if (inputLines.getOrNull(lineIndex + 1) != null) add(inputLines[lineIndex + 1])
                }
                val possibleIndexes = ((indexes.first - 1)..indexes.last + 1)
                if (possibleLines.any { pLine ->
                        possibleIndexes.any indexAny@{ pIndex ->
                            val char = pLine.getOrNull(pIndex) ?: return@indexAny false
                            !char.isDigit() && char != '.'
                        }
                    }) allowedNumbers.add(number)
            }
        }

        allowedNumbers.sum()
    }

    part2 {
        val numbers = mutableListOf<Int>()

        inputLines.forEachIndexed { index, line ->
            if (!line.contains('*')) return@forEachIndexed

            val possibleLines = buildList {
                if (inputLines.getOrNull(index - 1) != null) add(inputLines[index - 1])
                add(line)
                if (inputLines.getOrNull(index + 1) != null) add(inputLines[index + 1])
            }
            line.forEachIndexed charIndexed@{ charIndex, char ->
                if (char != '*') return@charIndexed
                val possibleIndexes = ((charIndex - 1)..charIndex + 1)
                val adjacentNumbers = mutableListOf<Int>()

                possibleLines.forEach { pLine ->
                    val lineNumbers = pLine.calculateNumbers()
                    lineNumbers.forEach {(range, num) ->
                        if (range.any { possibleIndexes.contains(it) }) adjacentNumbers.add(num)
                    }
                }

                if (adjacentNumbers.size == 2) numbers.add(adjacentNumbers[0] * adjacentNumbers[1])
            }
        }

        numbers.sum()
    }
}
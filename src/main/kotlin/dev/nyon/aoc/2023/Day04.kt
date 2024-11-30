package dev.nyon.aoc.`2023`

import dev.nyon.aoc.day

/**
 * @author btwonion
 * @since 04/12/2023
 */
fun main() = day(4, 2023) {
    test1Expected = 13
    test2Expected = 30

    fun String.findMatchingNumbers(): List<Int>? {
        val (winningNumbers, ownedNumbers) = split('|').map { numbersPart ->
            numbersPart.split(' ').mapNotNull { it.toIntOrNull() }
        }

        val matches = ownedNumbers.filter { winningNumbers.contains(it) }
        return matches.ifEmpty { return null }
    }

    part1 {
        val cardValues = mutableListOf<Int>()

        inputLines.map { it.drop(8) }.forEach { line ->
            val matches = line.findMatchingNumbers() ?: return@forEach

            var num = 1
            repeat(matches.size - 1) { num *= 2 }
            cardValues.add(num)
        }

        cardValues.sum()
    }

    part2 {
        val cardCounts = ArrayDeque(List(inputLines.size) { 1 })

        inputLines.forEachIndexed { index, line ->
            val matches = line.findMatchingNumbers() ?: return@forEachIndexed

            repeat(cardCounts[index]) {
                (1..matches.size).forEach { match ->
                    cardCounts[index + match] += 1
                }
            }
        }

        cardCounts.sum()
    }
}
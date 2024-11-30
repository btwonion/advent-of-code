package dev.nyon.aoc.`2023`

import dev.nyon.aoc.day

/**
 * @author btwonion
 * @since 07/12/2023
 */

fun main() = day(7, 2023) {
    test1Expected = 6440
    test2Expected = 5905

    fun solve(strengths: List<Char>, strength: String.() -> Int): Int {
        return inputLines.map { it.split(' ') }.sortedWith { (string1, _), (string2, _) ->
            val string1Strength = string1.strength()
            val string2Strength = string2.strength()
            if (string1Strength == string2Strength) {
                string1.forEachIndexed { index, c ->
                    val sc1Strength = strengths.indexOf(c)
                    val sc2Strength = strengths.indexOf(string2[index])
                    if (sc1Strength > sc2Strength) return@sortedWith 1
                    else if (sc2Strength > sc1Strength) return@sortedWith -1
                }
                return@sortedWith 0
            }
            return@sortedWith if (string1Strength > string2Strength) 1
            else -1
        }.mapIndexed { index, strings ->
            (index + 1) * strings.last().toInt()
        }.sum()
    }

    fun String.strength(): Int {
        val set = toSet()
        return when (set.size) {
            1 -> 6 // Five
            2 -> {
                if (set.any { char -> this.count { it == char } == 4 }) 5 // Four
                else 4 // Full House
            }

            3 -> {
                if (set.any { char -> this.count { it == char } == 3 }) 3 // Three
                else 2 // Two x
            }

            4 -> 1 // One x
            else -> 0
        }
    }

    part1 {
        val strengths = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')

        solve(strengths) { strength() }
    }

    part2 {
        val strengths = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')

        solve(strengths) { strengths.drop(1).maxOf { joker -> this.replace('J', joker).strength() } }
    }
}
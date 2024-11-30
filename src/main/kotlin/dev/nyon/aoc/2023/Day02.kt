package dev.nyon.aoc.`2023`

import dev.nyon.aoc.day

/**
 * @author btwonion
 * @since 02/12/2023
 */
fun main() = day(2, 2023) {
    test1Expected = 8
    test2Expected = 2286

    data class Game(val number: Int, val maxRed: Int, val maxGreen: Int, val maxBlue: Int)

    fun calculateGames(): List<Game> {
        val games = mutableListOf<Game>()
        inputLines.forEach { line ->
            val (gamePart, maximaPart) = line.split(':')
            val game = gamePart.removeRange(0, 5).toInt()
            val maxima = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)

            val maximaParts = maximaPart.replace(" ", "").split(';')

            maximaParts.forEach { part ->
                val partMaxima = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)

                part.split(',').forEach { colorMaximum ->
                    val color = colorMaximum.takeLastWhile { it.isLetter() }
                    partMaxima[color] = partMaxima[color]!! + colorMaximum.dropLastWhile { it.isLetter() }.toInt()
                }

                partMaxima.forEach { (color, max) ->
                    if (maxima[color]!! < max) maxima[color] = max
                }
            }

            games.add(Game(game, maxima["red"]!!, maxima["green"]!!, maxima["blue"]!!))
        }
        return games
    }

    part1 {
        val (mRed, mGreen, mBlue) = Triple(12, 13, 14)

        val games = calculateGames()

        games.sumOf { game ->
            if (mRed >= game.maxRed && mGreen >= game.maxGreen && mBlue >= game.maxBlue) game.number
            else 0
        }
    }

    part2 {
        calculateGames().sumOf { it.maxRed * it.maxGreen * it.maxBlue }
    }
}
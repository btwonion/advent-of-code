package dev.nyon.aoc


/**
 * @author btwonion
 * @since 02/12/2023
 */
fun main() = day(2) {
    test1Expected = 8
    test2Expected = 2286

    data class Game(val number: Int, val maxRed: Int, val maxGreen: Int, val maxBlue: Int)

    fun calculateGames(): List<Game> {
        val games = mutableListOf<Game>()
        inputLines.forEach { line ->
            val (gamePart, maximaPart) = line.split(':')
            val game = gamePart.removeRange(0, 5).toInt()
            var maxRed = 0
            var maxGreen = 0
            var maxBlue = 0

            val maximaParts = maximaPart.replace(" ", "").split(';')

            maximaParts.forEach { part ->
                var maxPartRed = 0
                var maxPartGreen = 0
                var maxPartBlue = 0

                part.split(',').forEach { colorMaximum ->
                    if (colorMaximum.contains("red")) maxPartRed += colorMaximum.dropLast(3).toInt()
                    if (colorMaximum.contains("green")) maxPartGreen += colorMaximum.dropLast(5).toInt()
                    if (colorMaximum.contains("blue")) maxPartBlue += colorMaximum.dropLast(4).toInt()
                }

                if (maxPartRed > maxRed) maxRed = maxPartRed
                if (maxPartGreen > maxGreen) maxGreen = maxPartGreen
                if (maxPartBlue > maxBlue) maxBlue = maxPartBlue
            }

            games.add(Game(game, maxRed, maxGreen, maxBlue))
        }
        return games
    }

    part1 {
        val (mred, mgreen, mblue) = Triple(12, 13, 14)

        val games = calculateGames()

        games.sumOf { game ->
            if (mred >= game.maxRed && mgreen >= game.maxGreen && mblue >= game.maxBlue) game.number
            else 0
        }
    }

    part2 {
        calculateGames().sumOf { it.maxRed * it.maxGreen * it.maxBlue }
    }
}
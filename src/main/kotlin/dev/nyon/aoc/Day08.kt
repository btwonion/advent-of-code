package dev.nyon.aoc

/**
 * @author btwonion
 * @since 08/12/2023
 */
fun main() = day(8) {
    test1Expected = 6

    fun String.parseWays(): Pair<String, String> {
        val (left, right) = drop(7).dropLast(1).split(", ")
        return left to right
    }

    part1 {
        val steps = inputLines.first()

        var currentStep = inputLines.first { it.startsWith("AAA") }.parseWays()
        var stepsNeeded = 0
        var finished = false
        while (!finished) {
            steps.forEach { nextStep ->
                if (finished) return@forEach
                val nextPlace = inputLines.first { it.startsWith(if (nextStep == 'L') currentStep.first else currentStep.second) }
                val nextWay = nextPlace.parseWays()
                stepsNeeded++
                if (nextPlace.startsWith("ZZZ")) finished = true
                currentStep = nextWay
            }
        }

        stepsNeeded
    }
}
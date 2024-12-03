package dev.nyon.aoc.`2024`

import dev.nyon.aoc.day

fun main() = day(3, 2024) {
    test1Expected = 161
    test2Expected = 48

    val onlyMulRegex = "mul\\((\\d{1,3}),(\\d{1,3})\\)".toRegex()
    val mulAndToggleRegex = "mul\\((\\d{1,3}),(\\d{1,3})\\)|(?:do\\(\\)|don't\\(\\))".toRegex()

    part1 {
        inputText.trim().let { onlyMulRegex.findAll(it) }.sumOf { result ->
            val (first, second) = result.groupValues.drop(1).map(String::toInt)
            first * second
        }
    }

    part2 {
        var enabled = true
        inputText.trim().let { mulAndToggleRegex.findAll(it) }.sumOf { result ->
            val wholeMatch = result.value
            when {
                wholeMatch.startsWith("mul") && enabled -> {
                    val (first, second) = result.groupValues.drop(1).map(String::toInt)
                    return@sumOf first * second
                }
                wholeMatch == "do()" -> enabled = true
                wholeMatch == "don't()" -> enabled = false
                else -> {}
            }
            0
        }
    }
}
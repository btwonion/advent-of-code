package dev.nyon.aoc.`2023`

import dev.nyon.aoc.day

fun main() = day(13, 2023) {
    test1Expected = 405

    val patterns = inputLines.fold(mutableListOf<MutableList<String>>(mutableListOf())) { acc, line ->
        if (line.isBlank()) acc.add(mutableListOf())
        else acc.last().add(line)
        acc
    }

    fun rotatePattern(pattern: List<String>): List<String> {
        return pattern.first().mapIndexed { index, _ ->
            pattern.map { line ->
                line[index]
            }.toCharArray().concatToString()
        }
    }

    fun handleReflection(pattern: List<String>): Int {
        return pattern.withIndex().find { (index, _) ->
            repeat(pattern.size) { offset ->
                val left = pattern.getOrNull(index - offset).also { if (it == null && offset != 0) return@find true }
                val right = pattern.getOrNull(index + 1 + offset).also { if (it == null && offset != 0) return@find true }
                if (left != right) return@find false
            }
            true
        }?.index?.let { it + 1 } ?: 0
    }

    part1 {
        patterns.sumOf { pattern ->
            val vertical = handleReflection(rotatePattern(pattern))
            val horizontal = handleReflection(pattern) * 100
            vertical + horizontal
        }
    }
}
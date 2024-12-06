package dev.nyon.aoc

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.time.measureTime

/**
 * @author btwonion
 * @since 01/12/2023
 */
fun day(day: Int, year: Int, block: Day.() -> Unit) {
    Day(day, year, block).run()
}

class Day(private val day: Int, private val year: Int, val block: Day.() -> Unit) {
    var inputText = ""
        private set
    val inputLines: List<String>
        get() = inputText.lines()

    var test1Expected: Any? = null
    var test2Expected: Any? = null

    var isTestRun: Boolean = false

    private var part1: (() -> Any?)? = null
    private var part2: (() -> Any?)? = null

    fun debug(string: String) {
        if (isTestRun) println(string)
    }

    fun part1(block: () -> Any?) {
        part1 = block
    }

    fun part2(block: () -> Any?) {
        part2 = block
    }

    private fun testPart(part: Int, block: (() -> Any?)?, expected: Any?) {
        if (block == null || expected == null) return
        var result = block()
        if (result is Long) result = result.toInt()
        if (expected != result) println("Test $part ${TextColors.red("failed")}! Expected '${TextColors.blue(expected.toString())}' but got '$result'.")
        else println("Test $part ${TextColors.green("succeeded")}! Expected '${TextColors.blue(expected.toString())}' - got '$result'.")
    }

    private fun runPart(part: Int, block: (() -> Any?)?) {
        if (block == null) return
        val result: Any?
        val time = measureTime {
            result = block()
        }
        println("The ${TextColors.green("result")} of part $part is ${TextColors.blue(result.toString())}. The calculation took ${TextColors.magenta(time.toString())}.")
    }

    fun run() {
        println(TextStyles.bold(TextColors.cyan("Running day $day of year $year")))

        println(TextColors.yellow("Running tests..."))
        println()

        repeat(2) { testCount ->
            isTestRun = true
            getPath("_test_${testCount + 1}").also { if (it.exists()) inputText = it.readText() }
            block()
            val (partBlock, expected) = when (testCount) {
                0 -> part1 to test1Expected
                1 -> part2 to test2Expected
                else -> part1 to test1Expected
            }
            testPart(testCount + 1, partBlock, expected)
            isTestRun = false
        }

        println()
        println(TextColors.yellow("Running challenge..."))
        println()
        inputText = getPath().readText()
        block()
        repeat(2) {
            runPart(
                it + 1, when (it) {
                    0 -> part1
                    1 -> part2
                    else -> null
                }
            )
        }
    }

    private fun getPath(extra: String? = null): Path {
        val dayName = day.toString().padStart(2, '0')
        return Path("src/main/resources/inputs/$year/day$dayName/day$dayName${extra ?: ""}.txt")
    }
}
package dev.nyon.aoc

/**
 * @author btwonion
 * @since 05/12/2023
 */
fun main() = day(5) {
    test1Expected = 35
    test2Expected = 46

    data class Range(val keyRange: LongRange, val valueRange: LongRange)
    data class MapKey(val key: String, val value: String)

    fun calculateRanges(): MutableMap<MapKey, MutableList<Range>> {
        val ranges = mutableMapOf<MapKey, MutableList<Range>>()
        var (key, value) = "" to ""
        inputLines.drop(1).forEach { line ->
            if (line.isBlank()) return@forEach
            if (line.replace(" ", "").all { !it.isDigit() }) {
                val split = line.dropLast(5).split('-')
                key = split[0]
                value = split[2]
                return@forEach
            }

            val (valueStart, keyStart, length) = line.split(' ').map { it.toLong() }
            val (keyRange, valueRange) = (keyStart..<keyStart + length) to (valueStart..<valueStart + length)
            try {
                ranges[MapKey(key, value)]!!.add(Range(keyRange, valueRange))
            } catch (e: NullPointerException) {
                ranges[MapKey(key, value)] = mutableListOf(Range(keyRange, valueRange))
            }
        }

        return ranges
    }

    fun List<Range>.toValue(key: Long): Long {
        val conversion = find { key in it.keyRange } ?: return key
        val difference = key - conversion.keyRange.first

        return conversion.valueRange.first + difference
    }

    fun calculateLocation(seed: Long, ranges: MutableMap<MapKey, MutableList<Range>>): Int? {
        var nextLong = seed
        ranges.forEach {
            nextLong = it.value.toValue(nextLong)
        }
        val int = nextLong.toInt()
        return if (int >= 0) nextLong.toInt() else null
    }

    part1 {
        val seeds = inputLines.first().drop(7).split(' ').map { it.toLong() }
        val ranges = calculateRanges()

        val seedLocations = seeds.mapNotNull { seed ->
            calculateLocation(seed, ranges)
        }

        seedLocations.min()
    }

    part2 {
        val seedRanges = inputLines.first().drop(7).split(' ').map { it.toLong() }.chunked(2)
        val ranges = calculateRanges()

        var lowest = Int.MAX_VALUE
        seedRanges.forEach { (start, end) ->
            (start..<start + end).forEach seed@{
                val loc = calculateLocation(it, ranges) ?: return@seed
                if (loc < lowest) lowest = loc
            }
        }

        lowest
    }
}
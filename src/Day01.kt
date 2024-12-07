import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val (list1, list2) = parseInputToTwoLists(input)
        return list1
            .sorted()
            .zip(list2.sorted())
            .fold(0) { acc, vals ->
                acc + abs(vals.first - vals.second)
            }
    }

    fun part2(input: List<String>): Int {
        val (list1, list2) = parseInputToTwoLists(input)
        val counter = list2.groupingBy { it }.eachCount()
        return list1.fold(0) { acc, num ->
            acc + num * counter.getOrDefault(num, 0)
        }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("1   2")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

private fun parseInputToTwoLists(input: List<String>) =
    input
        .map { line ->
            line.trim().split("\\s+".toRegex()).map { it.toInt() }
        }.map { nums -> nums[0] to nums[1] }
        .unzip()

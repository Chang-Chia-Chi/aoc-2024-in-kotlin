import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int =
        input
            .map { it.convertToNums() }
            .filter { it.isReportsSafe(1, 3, false) }
            .size

    fun part2(input: List<String>): Int =
        input
            .map { it.convertToNums() }
            .filter { it.isReportsSafe(1, 3, true) }
            .size

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("7 6 4 2 1", "1 2 7 8 9")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private fun List<Int>.isReportsSafe(
    minDiff: Int,
    maxDiff: Int,
    modifyOnce: Boolean,
): Boolean {
    if (this.isReportsSafeHelper(minDiff, maxDiff)) return true
    if (!modifyOnce) return false

    this.forEachIndexed { index, _ ->
        val mutableReports = this.toMutableList()
        mutableReports.removeAt(index)
        if (mutableReports.isReportsSafeHelper(minDiff, maxDiff)) {
            return true
        }
    }

    return false
}

private fun List<Int>.isReportsSafeHelper(
    minDiff: Int,
    maxDiff: Int,
): Boolean {
    val range = minDiff..maxDiff
    val diffs =
        this.zipWithNext { a, b ->
            val diff = a - b
            if (abs(diff) !in range) return false
            diff
        }

    val sign =
        when {
            diffs[0] > 0 -> 1
            diffs[0] < 0 -> -1
            else -> 0
        }
    return diffs.all { it * sign > 0 }
}

private fun String.convertToNums(): List<Int> = this.trim().split("\\s+".toRegex()).map { it.toInt() }

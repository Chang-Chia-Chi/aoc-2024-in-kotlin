fun main() {
    fun part1(input: List<String>): Int =
        input.fold(0) { answer, line ->
            val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
            val matches = regex.findAll(line)

            val lineTotal =
                matches.fold(0) { total, match ->
                    val a = match.groupValues[1].toInt()
                    val b = match.groupValues[2].toInt()
                    total + a * b
                }
            answer + lineTotal
        }

    fun part2(input: List<String>): Int {
        val mulRegex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
        val doRegex = Regex("""do\(\)""")
        val dontRegex = Regex("""don't\(\)""")

        val wholeLine = input.joinToString()
        val mulMatches = mulRegex.findAll(wholeLine)
        val doMatches = doRegex.findAll(wholeLine).map { it.range.last }.toList()
        val dontMatches = dontRegex.findAll(wholeLine).map { it.range.last }.toList()

        var indexDo = 0
        var maxDo = 0
        var indexDont = 0
        var maxDont = 0
        return mulMatches.fold(0) { total, match ->
            val index = match.range.first
            while (indexDo < doMatches.size && doMatches[indexDo] < index) {
                maxDo = doMatches[indexDo]
                indexDo++
            }
            while (indexDont < dontMatches.size && dontMatches[indexDont] < index) {
                maxDont = dontMatches[indexDont]
                indexDont++
            }

            if (maxDo >= maxDont) {
                val a = match.groupValues[1].toInt()
                val b = match.groupValues[2].toInt()
                total + a * b
            } else {
                total
            }
        }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("mul(2,4)")) == 8)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part2(testInput) == 48)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

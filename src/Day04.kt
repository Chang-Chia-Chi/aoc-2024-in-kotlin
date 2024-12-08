fun main() {
    fun part1(input: List<String>): Int {
        val m = input.size
        val n = input[0].length
        return (0..<m).fold(0) { answer, r ->
            answer +
                (0..<n).fold(0) { rowResult, c ->
                    rowResult + getWordCount(input, r, c, "XMAS")
                }
        }
    }

    fun part2(input: List<String>): Int {
        val m = input.size
        val n = input[0].length
        return (0..<m).fold(0) { answer, r ->
            answer +
                (0..<n).fold(0) { rowResult, c ->
                    if (getDiagWordCount(input, r, c, "MAS") +
                        getDiagWordCount(input, r, c, "SAM") == 2
                    ) {
                        rowResult + 1
                    } else {
                        rowResult
                    }
                }
        }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("XMAS")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

private fun getDiagWordCount(
    input: List<String>,
    r: Int,
    c: Int,
    word: String,
): Int {
    require(word.length % 2 == 1)
    val m = input.size
    val n = input[0].length
    val half = word.length / 2
    return dir2Diag.fold(0) { answer, (dr, dc) ->
        listOf(1, -1).forEach { dir ->
            (0..half).forEach { index ->
                val nr = r + dr * index * dir
                val nc = c + dc * index * dir
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) return@fold answer
                if (input[nr][nc] != word[half + index * dir]) return@fold answer
            }
        }
        answer + 1
    }
}

private fun getWordCount(
    input: List<String>,
    r: Int,
    c: Int,
    word: String,
): Int {
    val m = input.size
    val n = input[0].length
    return dir8.fold(0) { answer, (dr, dc) ->
        word.forEachIndexed { index, chr ->
            val nr = r + dr * index
            val nc = c + dc * index
            if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                return@fold answer
            }
            if (input[nr][nc] != chr) return@fold answer
        }
        answer + 1
    }
}

private val dir8: List<Pair<Int, Int>> =
    listOf(
        0 to 1,
        1 to 0,
        0 to -1,
        -1 to 0,
        1 to 1,
        1 to -1,
        -1 to 1,
        -1 to -1,
    )

private val dir2Diag: List<Pair<Int, Int>> =
    listOf(
        1 to 1,
        1 to -1,
    )

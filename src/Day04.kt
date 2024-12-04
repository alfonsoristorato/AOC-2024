fun main() {
    val input = readInput("Day04")
    var xmasCounter = 0
    input.forEachIndexed { lineIndex, line ->
        val xIndices = line.indices.filter { line[it] == 'X' }
        xIndices.forEach { xCharIndex ->
            xmasCounter += findXmas(input, xCharIndex, lineIndex)
        }
    }
    print("PART 1: ")
    xmasCounter.println()
}

fun findXmas(input: List<String>, xCharIndex: Int, lineIndex: Int): Int {
    var counter = 0
    Direction.entries.forEach { direction ->
        checkWithDirections(input, xCharIndex, lineIndex, "MAS", direction).takeIf { it }?.let {
            counter++
        }
    }
    return counter
}


enum class Direction(
    val mLineIndex: Int,
    val mXCharIndex: Int,
    val aLineIndex: Int,
    val aXCharIndex: Int,
    val sLineIndex: Int,
    val sXCharIndex: Int
) {
    RIGHT(0, 1, 0, 2, 0, 3),
    LEFT(0, -1, 0, -2, 0, -3),
    UP(-1, 0, -2, 0, -3, 0),
    DOWN(1, 0, 2, 0, 3, 0),
    RIGHT_UP(-1, 1, -2, 2, -3, 3),
    RIGHT_DOWN(1, 1, 2, 2, 3, 3),
    LEFT_UP(-1, -1, -2, -2, -3, -3),
    LEFT_DOWN(1, -1, 2, -2, 3, -3)
}

fun checkWithDirections(
    input: List<String>,
    xCharIndex: Int,
    lineIndex: Int,
    xmasRemaining: String,
    directions: Direction
): Boolean {
    return runCatching {
        val xmasMaybe =
            input[lineIndex + directions.mLineIndex][xCharIndex + directions.mXCharIndex].toString() +
                    input[lineIndex + directions.aLineIndex][xCharIndex + directions.aXCharIndex].toString() +
                    input[lineIndex + directions.sLineIndex][xCharIndex + directions.sXCharIndex].toString()

        return xmasMaybe == xmasRemaining
    }
        .getOrDefault(false)
}

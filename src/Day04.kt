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
    val mLineIndexModifier: Int,
    val mXCharIndexModifier: Int,
    val aLineIndexModifier: Int,
    val aXCharIndexModifier: Int,
    val sLineIndexModifier: Int,
    val sXCharIndexModifier: Int
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
            input[lineIndex + directions.mLineIndexModifier][xCharIndex + directions.mXCharIndexModifier].toString() +
                    input[lineIndex + directions.aLineIndexModifier][xCharIndex + directions.aXCharIndexModifier].toString() +
                    input[lineIndex + directions.sLineIndexModifier][xCharIndex + directions.sXCharIndexModifier].toString()

        return xmasMaybe == xmasRemaining
    }
        .getOrDefault(false)
}

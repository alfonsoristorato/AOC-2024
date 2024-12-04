fun main() {
    val input = readInput("Day04")
    var xmasCounter = 0
    var masXCounter = 0
    input.forEachIndexed { lineIndex, line ->
        val xIndices = line.indices.filter { line[it] == 'X' }
        val aIndices = line.indices.filter { line[it] == 'A' }
        xIndices.forEach { xCharIndex ->
            xmasCounter += findXmas(input, xCharIndex, lineIndex)
        }
        aIndices.forEach { aCharIndex ->
            masXCounter += findMasXShaped(input, aCharIndex, lineIndex)
        }
    }
    print("PART 1: ")
    xmasCounter.println()
    print("PART 2: ")
    masXCounter.println()
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

fun findMasXShaped(input: List<String>, aCharIndex: Int, lineIndex: Int): Int {
    var counter = 0
    runCatching {
        val topLeft = input[lineIndex - 1][aCharIndex - 1]
        val bottomRight = input[lineIndex + 1][aCharIndex + 1]

        val topRight = input[lineIndex - 1][aCharIndex + 1]
        val bottomLeft = input[lineIndex + 1][aCharIndex - 1]

        if (listOf(topLeft, bottomRight, topRight, bottomLeft).any { it == 'X' || it == 'A' }) return@runCatching
        if (topLeft == bottomRight || topRight == bottomLeft) return@runCatching

        counter++
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

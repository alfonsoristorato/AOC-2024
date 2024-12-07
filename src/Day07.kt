import java.math.BigInteger

fun main() {
    val input = readInput("Day07")
    val validResults = mutableListOf<BigInteger>()
    val validResultsPart2 = mutableListOf<BigInteger>()
    val expectedResults = input.map { line ->
        line.split(":")[0].toBigInteger()
    }
    val numbers = input.map { line ->
        line.split(":")[1].split(" ").filter { it != "" }.map { it.toBigInteger() }
    }
    expectedResults.forEachIndexed { index, result ->
        runCatching {
            validResults.add(checkValidEquation(result, numbers[index], listOf("*", "+")))
        }
    }

    expectedResults.forEachIndexed { index, result ->
        runCatching {
            validResultsPart2.add(checkValidEquation(result, numbers[index], listOf("*", "+", "|")))
        }
    }
    println("PART 1: ${validResults.sumOf { it }}")
    println("PART 2: ${validResultsPart2.sumOf { it }}")
}

fun checkValidEquation(expectedResult: BigInteger, numbers: List<BigInteger>, operators: List<String>): BigInteger {
    val allPermutations = generatePermutations(operators, numbers.size - 1)

    allPermutations.forEach { permutation ->
        val x: BigInteger = numbers.reduceIndexed { index, acc, i ->
            when (permutation[index - 1].toString()) {
                "+" -> acc.plus(i)
                "*" -> acc.multiply(i)
                "|" -> ("${acc}${i}").toBigInteger()
                else -> throw Exception("no matching operator found")
            }
        }
        if (x == expectedResult) {
            return expectedResult
        }
    }
    throw Exception("no matching equation found")
}

fun generatePermutations(chars: List<String>, length: Int): List<String> {
    if (length == 0) return listOf("")

    val permutations = generatePermutations(chars, length - 1)
    val result = mutableListOf<String>()

    for (perm in permutations) {
        for (char in chars) {
            result.add(perm + char)
        }
    }
    return result
}


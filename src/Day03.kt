import java.math.BigInteger

fun main() {
    var resultPartOne = BigInteger.ZERO
    var resultPartTwo = BigInteger.ZERO
    val partOneRegex = Regex("""mul\((\d{1,3}),\s?(\d{1,3})\)""")
    val partTwoRegex = Regex("""(mul\(\d{1,3},\s?\d{1,3}\)|do\(\)|don't\(\))""")
    var `do` = true

    val input = readInput("Day03")
    input.forEach { line ->
        partOneRegex.findAll(line).forEach {
            resultPartOne += it.groupValues[1].toBigInteger() * it.groupValues[2].toBigInteger()
        }
        partTwoRegex.findAll(line).forEach { it ->
            if(it.value == "don't()") {
                `do` = false
            }
            if(it.value == "do()") {
                `do` = true
            }
            if (`do`) {
                partOneRegex.findAll(it.value).forEach {
                    resultPartTwo += it.groupValues[1].toBigInteger() * it.groupValues[2].toBigInteger()
                }
            }
        }
    }
    println(resultPartOne)
    println(resultPartTwo)
}



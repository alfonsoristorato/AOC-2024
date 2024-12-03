fun main() {
    var resultPartOne = 0
    var resultPartTwo = 0
    val partOneRegex = Regex("""mul\((\d{1,3}),\s?(\d{1,3})\)""")
    val partTwoRegex = Regex("""(mul\(\d{1,3},\s?\d{1,3}\)|do\(\)|don't\(\))""")
    var `do` = true

    readInput("Day03").forEach { line ->
        partOneRegex.findAll(line).forEach {  mulMatch ->
            resultPartOne += mulMatch.groupValues[1].toInt() * mulMatch.groupValues[2].toInt()
        }
        partTwoRegex.findAll(line).forEach {
            when (val matchResultValue = it.value) {
                "do()" -> `do` = true
                "don't()" -> `do` = false
                else -> {
                    if (`do`) {
                        partOneRegex.find(matchResultValue)?.let { mulMatch ->
                            resultPartTwo += mulMatch.groupValues[1].toInt() * mulMatch.groupValues[2].toInt()
                        }
                    }
                }
            }
        }
    }
    print("PART 1: ")
    resultPartOne.println()
    print("PART 2: ")
    resultPartTwo.println()
}



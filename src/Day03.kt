fun main() {
    var resultPartOne = 0
    var resultPartTwo = 0
    val partOneRegex = Regex("""mul\((\d{1,3}),\s?(\d{1,3})\)""")
    val partTwoRegex = Regex("""(mul\(\d{1,3},\s?\d{1,3}\)|do\(\)|don't\(\))""")
    var `do` = true

    readInput("Day03").forEach { line ->
        partOneRegex.findAll(line).forEach {
            resultPartOne += it.groupValues[1].toInt() * it.groupValues[2].toInt()
        }
        partTwoRegex.findAll(line).forEach { it ->
            when (it.value){
                "do()" -> `do` = true
                "don't()" -> `do` = false
                else -> {
                    if(`do`){
                        partOneRegex.findAll(it.value).forEach {
                            resultPartTwo += it.groupValues[1].toInt() * it.groupValues[2].toInt()
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



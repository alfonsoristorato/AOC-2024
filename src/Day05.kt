fun main() {
    val input = readInput("Day05")

    val rules = mutableListOf<Pair<Int, Int>>()
    val updates = mutableListOf<List<Int>>()
    val validUpdates = mutableListOf<List<Int>>()
    input.forEach { line ->
        runCatching {
            val (left, right) = line.split("|")
            rules.add(Pair(left.toInt(), right.toInt()))
        }
        runCatching {
            updates.add(line.split(",").map { it.toInt() })

        }
    }
    updates.forEach { update ->
        var validUpdate = true
        rules.forEach {
            if (update.contains(it.first) && update.contains(it.second)) {
                if (update.indexOf(it.first) > update.indexOf(it.second)) {
                    validUpdate = false
                }
            }
        }
        validUpdate && validUpdates.add(update)
    }
    print("PART 1: ")
    println(validUpdates.sumOf { it[(it.size-1) / 2] }
    )
}

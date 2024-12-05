fun main() {
    val input = readInput("Day05")

    val rules = mutableListOf<Pair<Int, Int>>()
    val updates = mutableListOf<MutableList<Int>>()
    val validUpdates = mutableListOf<MutableList<Int>>()
    val invalidUpdates = mutableListOf<MutableList<Int>>()
    input.forEach { line ->
        runCatching {
            val (left, right) = line.split("|")
            rules.add(Pair(left.toInt(), right.toInt()))
        }
        runCatching {
            updates.add(line.split(",").map { it.toInt() }.toMutableList())
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
        validUpdate && validUpdates.add(update) || invalidUpdates.add(update)
    }
    print("PART 1: ")
    println(validUpdates.sumOf { it[(it.size - 1) / 2] })

    var invalidUpdatesCounter = invalidUpdates.size
    while (invalidUpdatesCounter > -1) {
        invalidUpdates.forEach { update ->
            var validUpdate = true
            rules.forEach {
                if (update.contains(it.first) && update.contains(it.second)) {
                    if (update.indexOf(it.first) > update.indexOf(it.second)) {
                        val moveToRightIndex = update.indexOf(it.second)
                        val moveToRightValue = update[moveToRightIndex]

                        update.removeAt(moveToRightIndex)
                        update.addLast(moveToRightValue)
                    }
                }
            }
            rules.forEach {
                if (update.contains(it.first) && update.contains(it.second)) {
                    if (update.indexOf(it.first) > update.indexOf(it.second)) {
                        validUpdate = false
                    }
                }
            }
            if (validUpdate) invalidUpdatesCounter-- else invalidUpdatesCounter++
        }
    }
    print("PART 2: ")
    println(invalidUpdates.sumOf { it[(it.size - 1) / 2] })
}



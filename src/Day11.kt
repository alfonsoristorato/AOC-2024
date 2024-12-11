fun main() {
    val input = readInput("Day11")[0]
    val stones = input.split(" ").map { it.toLong() }
    println("PART 1: ${blinkAndCount(stones, 25)}")
    println("PART 2: ${blinkAndCount(stones, 75)}")
}

fun blinkAndCount(stones: List<Long>, blinks: Int): Long {
    var sameStonesCount = stones.associateWith { 1L }
    for (i in 0 until blinks) {
        val newStonesCount = mutableMapOf<Long, Long>()
        for ((stone, stoneCount) in sameStonesCount) {
            when {
                stone == 0L -> newStonesCount[1] = newStonesCount[1]?.plus(stoneCount) ?: (0 + stoneCount)
                stone.toString().length % 2 == 0 -> {
                    val left = stone.toString().substring(0,stone.toString().length /2).toLong()
                    val right = stone.toString().substring(stone.toString().length /2).toLong()

                    newStonesCount[left] = newStonesCount[left]?.plus(stoneCount) ?: (0 + stoneCount)
                    newStonesCount[right] = newStonesCount[right]?.plus(stoneCount) ?: (0 + stoneCount)
                }
                else -> {
                    val stoneTimes2024 = stone * 2024
                    newStonesCount[stoneTimes2024] =
                        newStonesCount[stoneTimes2024]?.plus(stoneCount) ?: (0 + stoneCount)
                }
            }
        }
        sameStonesCount = newStonesCount
    }
    return sameStonesCount.values.sum()
}




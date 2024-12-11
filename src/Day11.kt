fun main() {
    val input = readInput("Day11")[0]
    val stones = input.split(" ").toMutableList()

    var nextStone = 0
    var blink = 0
    while (true) {
        if (nextStone > stones.size - 1) {
            nextStone = 0
            blink++
            if (blink == 25) {
                break
            }
        }
        if (stones[nextStone] == "0") {
            stones[nextStone] = "1"
            nextStone += 1
            continue
        }
        if (stones[nextStone].length % 2 == 0) {
            stones.add(nextStone + 1, stones[nextStone].substring(stones[nextStone].length / 2).toInt().toString())
            stones[nextStone] = stones[nextStone].substring(0, stones[nextStone].length / 2)
            nextStone += 2
            continue
        }
        stones[nextStone] = (stones[nextStone].toLong() * 2024).toString()
        nextStone += 1
        continue
    }
    println("PART 1: ${stones.size}")

}




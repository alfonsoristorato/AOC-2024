import kotlin.math.abs

fun main() {

    var distanceInLocationIds = 0
    var similarityScore = 0

    readInput("Day01")
        .map { line ->
            val (num1, num2) = line.split("   ").map { it.toInt() }
            Pair(num1, num2)
        }
        .unzip()
        .let { (list1, list2) ->
            val list2Sorted = list2.sorted()
            list1.sorted().forEachIndexed { index, numb ->
                distanceInLocationIds += abs(numb - list2Sorted[index])
                similarityScore += numb * list2.count { it == numb }
            }
        }
    println("PART 1: $distanceInLocationIds")
    println("PART 2: $similarityScore")

}

import java.awt.Point

fun main() {
    val input = readInput("Day14")
    val maxX = 101
    val maxY = 103
    val middleX = maxX / 2
    val middleY = maxY / 2
    val robotsAndVelocity = buildList<PointAndValue<Point>> {
        input.forEach {
            val robotPosition = Point(
                it.split("p=")[1].split(",")[0].toInt(),
                it.split("p=")[1].split(",")[1].split(" ")[0].toInt()
            )
            val robotVelocity = Point(
                it.split("v=")[1].split(",")[0].toInt(),
                it.split("v=")[1].split(",")[1].toInt()
            )
            add(PointAndValue(robotPosition, robotVelocity))
        }
    }.toMutableList()

    moveRobotsForNSeconds(robotsAndVelocity, 100, maxX, maxY, 0)
    val pointsInQuadrants = groupPointsByQuadrants(robotsAndVelocity.filter { it.point.y != middleY || it.point.x != middleX }.map { it.point }, middleX, middleY)
    val productOfSizes = pointsInQuadrants.values.map { it.size }.fold(1) { acc, size -> acc * size }
    println("PART 1: $productOfSizes")
    moveRobotsForNSeconds(robotsAndVelocity, Int.MAX_VALUE, maxX, maxY,100)
}

fun printMap(robotsAndVelocity: List<PointAndValue<Point>>, maxX: Int, maxY: Int) {
    println()
    val map = Array(maxY) { CharArray(maxX) { '.' } }
    robotsAndVelocity.forEach {
        map[it.point.y][it.point.x] = '#'
    }

    map.forEach { println(it.joinToString("")) }
}

private fun moveRobotsForNSeconds(robotsAndVelocity: MutableList<PointAndValue<Point>>, seconds: Int, maxX: Int, maxY: Int, elapsedSecondsSoFar:Int) {
    repeat(seconds) {
        robotsAndVelocity.forEach { robotAndVelocity ->
            var newX = robotAndVelocity.point.x + robotAndVelocity.value.x
            var newY = robotAndVelocity.point.y + robotAndVelocity.value.y
            if (newX in 0 until maxX && newY in 0 until maxY) {
                robotAndVelocity.point.x = newX
                robotAndVelocity.point.y = newY
            } else {
                if (newX >= maxX) {
                    newX -= maxX
                }
                if (newY >= maxY) {
                    newY -= maxY
                }
                if (newX < 0) {
                    newX += maxX
                }
                if (newY < 0) {
                    newY += maxY
                }
                robotAndVelocity.point.x = newX
                robotAndVelocity.point.y = newY
            }
        }
        robotsAndVelocity.groupBy { it.point.y }.forEach { (y, points) ->
            val xValues = points.map { it.point.x }.sorted()
            var sequentialCount = 1
            for (i in 1 until xValues.size) {
                if (xValues[i] == xValues[i - 1] + 1) {
                    sequentialCount++
                    if (sequentialCount >= 10) {
                        println("At least 20 sequential x values in group with y=$y. This happened at second ${it + 1 + elapsedSecondsSoFar}")
                        println("PART 2: ${it + 1 + elapsedSecondsSoFar}")
                        println("Here's the tree :) ")
                        printMap(robotsAndVelocity, maxX, maxY)
                        return
                    }
                } else {
                    sequentialCount = 1
                }
            }
        }
    }
}

fun groupPointsByQuadrants(points: List<Point>, midX: Int, midY: Int): Map<String, List<Point>> {
    val quadrants = mutableMapOf(
        "Q1" to mutableListOf<Point>(),
        "Q2" to mutableListOf<Point>(),
        "Q3" to mutableListOf<Point>(),
        "Q4" to mutableListOf<Point>()
    )

    for (point in points) {
        when {
            point.x > midX && point.y > midY -> quadrants["Q1"]?.add(point)
            point.x < midX && point.y > midY -> quadrants["Q2"]?.add(point)
            point.x < midX && point.y < midY -> quadrants["Q3"]?.add(point)
            point.x > midX && point.y < midY -> quadrants["Q4"]?.add(point)
        }
    }

    return quadrants
}



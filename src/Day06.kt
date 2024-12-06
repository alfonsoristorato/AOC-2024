import java.awt.Point

fun main() {
    val input = readInput("Day06")
    val distinctPositionsVisited = mutableSetOf<Point>()

    var direction = Direction.UP
    val startingPoint = Point(
        input.find { it.contains("^") }!!.indexOf("^"),
        input.indexOf(input.find { it.contains("^") })
    )

    var i = startingPoint.y
    var j = startingPoint.x
    runCatching {
        while (true) {
            distinctPositionsVisited.add(Point(j, i))
            if (direction == Direction.UP) {
                if (input[i-1][j] != '#') {
                    i--
                } else {
                    direction = Direction.RIGHT
                }
            }
            if (direction == Direction.RIGHT) {
                if (input[i][j+1] != '#') {
                    j++
                } else {
                    direction = Direction.DOWN
                }
            }
            if (direction == Direction.DOWN) {
                if (input[i+1][j] != '#') {
                    i++
                } else {
                    direction = Direction.LEFT
                }
            }
            if (direction == Direction.LEFT) {
                if (input[i][j-1] != '#') {
                    j--
                } else {
                    direction = Direction.UP
                }
            }
        }
    }

    println("PART 1: ${distinctPositionsVisited.size}")
}





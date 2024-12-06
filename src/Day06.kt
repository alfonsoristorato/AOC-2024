import java.awt.Point

fun main() {
    val input = readInput("Day06")
    val distinctPositionsVisited = mutableSetOf<Point>()
    val nonDistinctPositionsVisited = mutableListOf<Point>()
    val possibleObstacles = mutableListOf<Point>()

    val startingPoint = Point(
        input.find { it.contains("^") }!!.indexOf("^"),
        input.indexOf(input.find { it.contains("^") })
    )


    runCatching {
        moveToNextPosition(
            distinctPositionsVisited,
            nonDistinctPositionsVisited,
            startingPoint.x,
            startingPoint.y,
            Direction.UP,
            input,
            possibleObstacles
        )
    }

    println("PART 1: ${distinctPositionsVisited.size}")


    possibleObstacles.clear()


    input.forEachIndexed { y, row ->

        row.forEachIndexed { x, position ->
            if (position == '.') {
                distinctPositionsVisited.clear()
                nonDistinctPositionsVisited.clear()
                val newList = input.toMutableList()
                newList[y] = newList[y].replaceRange(x, x + 1, "#")
                runCatching {
                    moveToNextPosition(
                        distinctPositionsVisited,
                        nonDistinctPositionsVisited,
                        startingPoint.x,
                        startingPoint.y,
                        Direction.UP,
                        newList,
                        possibleObstacles
                    )
                }
            }
        }
    }

    println("PART 2: ${possibleObstacles.size}")


}

private fun moveToNextPosition(
    distinctPositionsVisited: MutableSet<Point>,
    nonDistinctPositionsVisited: MutableList<Point>,
    j: Int,
    i: Int,
    direction: Direction,
    input: List<String>,
    possibleObstacles: MutableList<Point>
) {
    var j1 = j
    var i1 = i
    var direction1 = direction
    while (true) {
        distinctPositionsVisited.add(Point(j1, i1))
        nonDistinctPositionsVisited.add(Point(j1, i1))

        if (nonDistinctPositionsVisited.size == ((distinctPositionsVisited.size * 2) +2)) {
                if (nonDistinctPositionsVisited.toSet() == distinctPositionsVisited) {
                    possibleObstacles.add(Point(j1, i1))
                    throw Exception()
                }

        }

        if (direction1 == Direction.UP) {
            if (input[i1 - 1][j1] != '#') {
                i1--
            } else {
                direction1 = Direction.RIGHT
            }
        }
        if (direction1 == Direction.RIGHT) {
            if (input[i1][j1 + 1] != '#') {
                j1++
            } else {
                direction1 = Direction.DOWN
            }
        }
        if (direction1 == Direction.DOWN) {
            if (input[i1 + 1][j1] != '#') {
                i1++
            } else {
                direction1 = Direction.LEFT
            }
        }
        if (direction1 == Direction.LEFT) {
            if (input[i1][j1 - 1] != '#') {
                j1--
            } else {
                direction1 = Direction.UP
            }
        }
    }
}






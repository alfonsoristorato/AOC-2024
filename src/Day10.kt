import java.awt.Point

fun main() {
    val input = readInput("Day10")
    val trailheadsPoints = buildSet {
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                add(PointAndValue(Point(x, y), char.digitToInt()))
            }
        }
    }
    val trailsAvailable = mutableMapOf<Point, MutableList<Point>>()
    trailheadsPoints.filter { it.value == 0 }.forEach { trailheadPoint ->
        trailsAvailable[trailheadPoint.point] = mutableListOf()
        goNextPosition(trailheadPoint.point, trailheadPoint.point, 0, trailheadsPoints, trailsAvailable, null)
    }
    print("PART 1: ")
    println(trailsAvailable.entries.sumOf { it.value.toSet().size })
    print("PART 2: ")
    println(trailsAvailable.entries.sumOf { it.value.size })


}

fun goNextPosition(
    positionOfZero: Point,
    currentPosition: Point,
    currentHeight: Int,
    trailheadsPoints: Set<PointAndValue<Int>>,
    trailsAvailable: MutableMap<Point, MutableList<Point>>,
    direction: Direction?
) {
    if (currentHeight == 9) {
        trailsAvailable[positionOfZero]?.add(currentPosition)
    }
    val directionToFilterOut = when (direction) {
        Direction.UP -> Direction.DOWN
        Direction.DOWN -> Direction.UP
        Direction.LEFT -> Direction.RIGHT
        Direction.RIGHT -> Direction.LEFT
        else -> null
    }

    Direction.entries.filter { it != Direction.LEFT_DOWN && it != Direction.LEFT_UP && it != Direction.RIGHT_DOWN && it != Direction.RIGHT_UP && it != directionToFilterOut }
        .forEach { direction1 ->
            when (direction1) {
                Direction.UP -> {
                    checkNextPosition(
                        Point(currentPosition.x, currentPosition.y - 1),
                        currentHeight + 1,
                        trailheadsPoints,
                    ).takeIf { it }?.let {
                        goNextPosition(
                            positionOfZero,
                            Point(currentPosition.x, currentPosition.y - 1),
                            currentHeight + 1,
                            trailheadsPoints,
                            trailsAvailable,
                            direction1
                        )
                    }
                }

                Direction.DOWN -> {
                    checkNextPosition(
                        Point(currentPosition.x, currentPosition.y + 1),
                        currentHeight + 1,
                        trailheadsPoints,
                    ).takeIf { it }?.let {
                        goNextPosition(
                            positionOfZero,
                            Point(currentPosition.x, currentPosition.y + 1),
                            currentHeight + 1,
                            trailheadsPoints,
                            trailsAvailable,
                            direction1
                        )
                    }
                }

                Direction.LEFT -> {
                    checkNextPosition(
                        Point(currentPosition.x - 1, currentPosition.y),
                        currentHeight + 1,
                        trailheadsPoints,
                    ).takeIf { it }?.let {
                        goNextPosition(
                            positionOfZero,
                            Point(currentPosition.x - 1, currentPosition.y),
                            currentHeight + 1,
                            trailheadsPoints,
                            trailsAvailable,
                            direction1
                        )
                    }
                }

                Direction.RIGHT -> {
                    checkNextPosition(
                        Point(currentPosition.x + 1, currentPosition.y),
                        currentHeight + 1,
                        trailheadsPoints,
                    ).takeIf { it }?.let {
                        goNextPosition(
                            positionOfZero,
                            Point(currentPosition.x + 1, currentPosition.y),
                            currentHeight + 1,
                            trailheadsPoints,
                            trailsAvailable,
                            direction1
                        )
                    }
                }

                else -> throw IllegalArgumentException("Invalid direction")
            }
        }
}

fun checkNextPosition(
    position: Point,
    currentHeight: Int,
    trailheadsPoints: Set<PointAndValue<Int>>,
): Boolean {
    return trailheadsPoints.any { it.point == position && it.value == currentHeight }
}



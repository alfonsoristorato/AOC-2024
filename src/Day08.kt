import java.awt.Point

fun main() {
    val input = readInput("Day08")
    val antennasPoints = mutableListOf<PointAndChar>()
    val antiNodesPoints = mutableSetOf<Point>()
    val (maxX, maxY) = input[0].length to input.size

    input.forEachIndexed { yIndex, string ->
        string.forEachIndexed { xIndex, char ->
            if (char.isDigit() || char.isLetter()) {
                antennasPoints.add(PointAndChar(Point(xIndex, yIndex), char))
            }
        }
    }
    val distinctAntennas = antennasPoints.map { it.char }.distinct()

    distinctAntennas.forEach { antenna ->
        val antennaPoints = antennasPoints.filter { it.char == antenna }
        antennaPoints.forEach { pointAndChar ->
            antennaPoints.filter { it != pointAndChar }.forEach { differentPointAndChar ->
                val distancePoints = differentPointAndChar.point - pointAndChar.point
                val relativeDistance = distancePoints * 2
                val antiNode = pointAndChar.point + relativeDistance

                if (antiNode.y in 0..<maxY && antiNode.x in 0..<maxX) {

                    antiNodesPoints.add(antiNode)

                }
            }
        }
    }
    println("PART 1: ${antiNodesPoints.size}")

    val antiNodesPointsPart2 = mutableSetOf<Point>()
    distinctAntennas.forEach { antenna ->
        val antennaPoints = antennasPoints.filter { it.char == antenna }
        antennaPoints.forEach { pointAndChar ->
            antennaPoints.filter { it != pointAndChar }.forEach { differentPointAndChar ->
                val distancePoints = differentPointAndChar.point - pointAndChar.point
                var antiNodeGoingDown = pointAndChar.point
                while (antiNodeGoingDown.y in 0..<maxY && antiNodeGoingDown.x in 0..<maxX) {
                    antiNodeGoingDown += distancePoints
                    if (antiNodeGoingDown.y in 0..<maxY && antiNodeGoingDown.x in 0..<maxX) {
                        antiNodesPointsPart2.add(antiNodeGoingDown)
                    }
                }

                var antiNodeGoingUp = pointAndChar.point
                while (antiNodeGoingDown.y in 0..<maxY && antiNodeGoingDown.x in 0..<maxX) {
                    antiNodeGoingUp -= distancePoints
                    if (antiNodeGoingUp.y in 0..<maxY && antiNodeGoingUp.x in 0..<maxX) {
                        antiNodesPointsPart2.add(antiNodeGoingUp)
                    }
                }


            }
        }
    }
    println("PART 2: ${antiNodesPointsPart2.size}")
}

operator fun Point.plus(other: Point) = Point(x + other.x, y + other.y)
operator fun Point.minus(other: Point) = Point(x - other.x, y - other.y)
operator fun Point.times(multiplier: Int) = Point(x * multiplier, y * multiplier)

data class PointAndChar(val point: Point, val char: Char)

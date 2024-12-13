import java.awt.Point

fun main() {
    val input = readInput("Day13")
    var cost = 0L
    var cost2 = 0L
    for (i in input.indices step 4) {
        val buttonA = Point(
            input[i].split("X+")[1].split(",")[0].toInt(),
            input[i].split("Y+")[1].toInt(),
        )
        val buttonB = Point(
            input[i + 1].split("X+")[1].split(",")[0].toInt(),
            input[i + 1].split("Y+")[1].toInt(),
        )
        val prize = Point(
            input[i + 2].split("X=")[1].split(",")[0].toInt(),
            input[i + 2].split("Y=")[1].toInt(),
        )
        cost += pressButton(prize, buttonA, buttonB, 0)
        cost2 += pressButton(prize, buttonA, buttonB, 10000000000000)
    }
    println("PART 1: $cost")
    println("PART 2: $cost2")
}

private fun pressButton(prize: Point, buttonClicked: Point, otherButton: Point, prizeCorrector: Long): Long {
    val matrixDeterminant = buttonClicked.x * otherButton.y - buttonClicked.y * otherButton.x
    if (matrixDeterminant == 0) {
        return 0
    }

    val x =
        if (((prize.x + prizeCorrector) * otherButton.y - (prize.y + prizeCorrector) * otherButton.x) % matrixDeterminant == 0L) ((prize.x + prizeCorrector) * otherButton.y - (prize.y + prizeCorrector) * otherButton.x) / matrixDeterminant else -1
    val y =
        if ((buttonClicked.x * (prize.y + prizeCorrector) - (prize.x + prizeCorrector) * buttonClicked.y) % matrixDeterminant == 0L) (buttonClicked.x * (prize.y + prizeCorrector) - (prize.x + prizeCorrector) * buttonClicked.y) / matrixDeterminant else -1
    return if (x > 0 && y > 0) {
        x * 3 + y
    } else {
        0
    }
}



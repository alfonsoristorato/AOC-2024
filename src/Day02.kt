import kotlin.math.abs

fun main() {
    readInput("Day02")
        .map { line ->
            line.split(" ").map { it.toInt() }
        }
        .map { reportList ->
            Pair(
                checkReportSafe(reportList, false),
                checkReportSafe(reportList, true)
            )
        }
        .unzip()
        .let { (reportWithoutDampener, reportWithDampener) ->
            print("PART 1: ")
            reportWithoutDampener.count { it }.println()
            print("PART 2: ")
            reportWithDampener.count { it }.println()
        }

}

fun checkReportSafe(reportList: List<Int>, safetyDampener: Boolean): Boolean {
    var increasing = true
    var decreasing = true
    var safe = true
    for (i in 0 until reportList.size - 1) {
        if (reportList[i] > reportList[i + 1]) {
            increasing = false
        }
        if (reportList[i] < reportList[i + 1]) {
            decreasing = false
        }

        if (!increasing && !decreasing) break

        safe = abs(reportList[i] - reportList[i + 1]) in 1..3

        if (!safe) break

    }
    return if (safetyDampener) {
        reportList.indices.any { indexToRemove ->
            val tempList = reportList.toMutableList()
            tempList.removeAt(indexToRemove)
            checkReportSafe(tempList, false)
        }
    } else {
        (increasing || decreasing) && safe
    }
}

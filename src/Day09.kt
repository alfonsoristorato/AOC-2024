import java.math.BigInteger

fun main() {
    val input = readInput("Day09")[0]
    val expandedDisk = mutableListOf<String>()
    val filesMap = mutableMapOf<String, Int>()
    val dotsRange = mutableSetOf<IntRange>()
    input.forEachIndexed { index, char ->
        when (index % 2 == 0) {
            true -> {
                for (i in 0 until char.digitToInt()) {
                    expandedDisk.add((index / 2).toString())
                    filesMap[(index / 2).toString()] = filesMap[(index / 2).toString()]?.plus(1) ?: 1
                }
            }

            false -> {
                for (i in 0 until char.digitToInt()) {
                    expandedDisk.add(".")
                }
                if (char.digitToInt() != 0) {
                    dotsRange.add(expandedDisk.size - char.digitToInt()..<expandedDisk.size)
                }

            }
        }
    }
    val expandedDiskMapForPart2 = expandedDisk.toMutableList()

    //TODO this can be avoided and the calculation happen in the acc forEach, refactor when you have time
    expandedDisk.forEachIndexed forLoop@{ index, s ->
        if (expandedDisk.subList(index, expandedDisk.size).toSet() == setOf(".")) {
            return@forLoop
        }
        if (s == ".") {
            val lastDigitIndex = expandedDisk.indexOfLast { it.toDoubleOrNull() != null }
            val lastDigit = expandedDisk[lastDigitIndex]
            expandedDisk[index] = lastDigit
            expandedDisk[lastDigitIndex] = "."
        }

    }

    var acc = BigInteger.ZERO
    expandedDisk.forEachIndexed { index, char ->
        if (char != ".") {
            acc += BigInteger.valueOf(index.toLong()).multiply(char.toBigInteger())
        }
    }
    println("Part 1: $acc")

    filesMap.entries.reversed().forEach { entry ->
        dotsRange.sortedBy { it.first }.find { it.count() >= entry.value }?.let let@{ range ->
            val firstIndexOfFileSelected = expandedDiskMapForPart2.indexOfFirst { it == entry.key }
            if (range.first > firstIndexOfFileSelected) {
                return@let
            }
            for (i in 0 until entry.value) {
                expandedDiskMapForPart2[range.first + i] = entry.key
                expandedDiskMapForPart2[firstIndexOfFileSelected + i] = "."
            }
            dotsRange.remove(range)
            if (range.count() > entry.value) {
                dotsRange.add(range.first + entry.value..range.last)
            }
        }
    }


    var acc2 = BigInteger.ZERO
    expandedDiskMapForPart2.forEachIndexed { index, char ->
        if (char != ".") {
            acc2 += BigInteger.valueOf(index.toLong()).multiply(char.toBigInteger())
        }
    }
    println("Part 2: $acc2")


}

fun main(args: Array<String>) {
//    part11_1()
    part11_2()
}

data class Day11Result(var x: Int, var y: Int, var squareSize: Int, var maxVal: Int)

fun part11_2() {
    val DAY11_SERIAL = 7315

    var grid = List(301) { MutableList(301) { 0 } }
    // populate grid
    for (x in 1..300) {
        for (y in 1..300) {
            grid[x][y] = getPower(x, y, DAY11_SERIAL)
        }
    }

    var gridSum = grid

    var results = Day11Result(0, 0, 0, 0)
    for (squareSize in 2..300) {
        gridSum = createGridSum(gridSum, grid, squareSize, results)
    }

    println(results)
}

private fun createGridSum(prevCalc: List<MutableList<Int>>, grid: List<MutableList<Int>>, squareSize: Int, result: Day11Result): List<MutableList<Int>> {
    // cleverly use memoization -- only need the edge
    var gridSum = List(301) { MutableList(301) { 0 } }
    for (x in 1..(300 - (squareSize)) ) {
        for (y in 1..(300 - (squareSize))) {
            val valueXY = prevCalc[x][y] +
                    (0 until squareSize).map { yIt ->
                        grid[x + squareSize - 1][y + yIt] }.sum() +
                    (0 until (squareSize - 1)).map { xIt ->
                        grid[x + xIt][y + squareSize - 1] }.sum()
            gridSum[x][y] = valueXY
            if (valueXY > result.maxVal) {
                result.maxVal = valueXY
                result.squareSize = squareSize
                result.x = x
                result.y = y
            }
        }
    }
    return gridSum
}

private fun getMaxPower(gridSum: List<MutableList<Int>>, squareSize: Int): Triple<Int, Int, Int> {  // deprecated, we want to calculate this as we go along
    var xMax = 0
    var yMax = 0
    var maxPower = 0
    // find largest
    for (x in 1..(300 - squareSize)) {
        for (y in 1..(300 - squareSize)) {
            if (gridSum[x][y] > maxPower) {
                xMax = x
                yMax = y
                maxPower = gridSum[x][y]
            }
        }
    }
    return Triple(xMax, yMax, maxPower)
}

fun part11_1() {
    val DAY11_SERIAL = 7315
    // sliding window of results
    // we're going to use coordinates, 1 : 300, and just skip the 0's
    var grid = List(301) { MutableList(301) { 0 } }
    // populate grid
    for (x in 1..300) {
        for (y in 1..300) {
            grid[x][y] = getPower(x, y, DAY11_SERIAL)
        }
    }

    var gridSum = List(301) { MutableList(301) { 0 } }
    for (x in 1..297) {
        for (y in 1..297) {
            gridSum[x][y] += (0..2).map{ x1 -> (0..2).map { y1 -> grid[x + x1][y + y1] }.sum()  }.sum()
        }
    }

    var xMax = 0
    var yMax = 0
    var maxPower = 0
    // find largest
    for (x in 1..297) {
        for (y in 1..297) {
            if (gridSum[x][y] > maxPower) {
                xMax = x
                yMax = y
                maxPower = gridSum[x][y]
            }
        }
    }

    println("Max Power: $maxPower")
    println("($xMax , $yMax)")
}

fun getPower(x: Int, y: Int, serial: Int): Int {
    val rackId = x + 10
    val power = rackId * y
    val increasePower = power + serial
    val newPower = (increasePower * rackId).toLong()
    val hundredthPlace = ((newPower % 1000L) / 100L).toInt()
    return (hundredthPlace - 5)
}
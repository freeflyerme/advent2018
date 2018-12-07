fun main(args: Array<String>) {  // https://adventofcode.com/2018/day/6#part2
    part6_1()
    part6_2()
}

fun part6_2() {
// Algorithm weakness -- the infinite detection isn't perfect.  The largest result was not the right answer.  Instead, went down to second largest
    val points: List<Pair<Int, Int>> = readAsStringList("Day6.txt", "\n").map { it -> val l = it.replace(" ", "").split(",")
        Pair(l[0].toInt(), l[1].toInt()) }

    val minX = points.minBy { it -> it.first }!!.first
    val maxX = points.maxBy { it -> it.first }!!.first
    val minY = points.minBy { it -> it.second }!!.second
    val maxY = points.maxBy { it -> it.second }!!.second

    val xLen = maxX - minX + 1
    val yLen = maxY - minY + 1

    var grid = List(xLen) { List(yLen) { HashMap<Int, MutableList<Int>>() } }  // each grid: distance, list of points that belong there

    val stdPoints = points.map { it -> Pair(it.first - minX, it.second - minY) }

    for ((pointName, p) in stdPoints.withIndex()) { // Lesson: withIndex to simplify things
        for (x in 0 until xLen ) {
            for (y in 0 until yLen) {
                val currentPoint = Pair(x, y)
                // calculate distance to p, add it to the map at the grid
                val dist = manhattanDist(currentPoint, p)
                if (grid[x][y].keys.contains(dist)) {
                    grid[x][y][dist]!!.add(pointName)
                }
                else {
                    grid[x][y].put(dist, MutableList(1){pointName})
                }
            }
        }
    }

    // get the minimum for each grid
    var minGrid = List(xLen) { MutableList(yLen) { -1 } }
    for (x in 0 until xLen) {
        for (y in 0 until yLen) {
            val minDist = grid[x][y].minBy { it.key }
            if (minDist!!.value.size == 1) { // only set it, if there's no tie
                minGrid[x][y] = minDist!!.value.first()
            }
        }
    }

    // finally, get the count of items
    var pointToArea = HashMap<Int, Int>()

    minGrid.forEach{row ->
        row.forEach { closestPoint ->
            if (closestPoint != -1) {
                if (pointToArea.keys.contains(closestPoint)) {
                    pointToArea[closestPoint] = pointToArea[closestPoint]!! + 1
                } else {
                    pointToArea.put(closestPoint, 1)
                }
            }
        }
    }


    // Debug -- draw the graphs
//    minGrid.forEach{
//        println(it)
//    }

    println("Points to Area: " + pointToArea)
    println("Areas Sorted: " + pointToArea.values.sorted())
    println("The max point with area is: " + pointToArea.maxBy{ it.value })
    println("The max area is: " + pointToArea.values.max()) // 6628 is too high
}

fun part6_1() {
    // Algorithm -- use the edges as the boundaries for the area to be considered
    // the finite rectangle that results, update each item with the distance from the others.  Use distance as the key, and number as value to simplify ties
    // make a final pass to get the minimum, marking it each.
    // make a pass to collect the ones that have the most -> one pass only, store in hashmap with a reduce function

    // Algorithm weakness -- the infinite detection isn't perfect.  The largest result was not the right answer.  Instead, went down to second largest
    val points: List<Pair<Int, Int>> = readAsStringList("Day6.txt", "\n").map { it -> val l = it.replace(" ", "").split(",")
            Pair(l[0].toInt(), l[1].toInt()) }

    val minX = points.minBy { it -> it.first }!!.first
    val maxX = points.maxBy { it -> it.first }!!.first
    val minY = points.minBy { it -> it.second }!!.second
    val maxY = points.maxBy { it -> it.second }!!.second

    val xLen = maxX - minX + 1
    val yLen = maxY - minY + 1

    var grid = List(xLen) { List(yLen) { HashMap<Int, MutableList<Int>>() } }  // each grid: distance, list of points that belong there

    val stdPoints = points.map { it -> Pair(it.first - minX, it.second - minY) }

    for ((pointName, p) in stdPoints.withIndex()) { // Lesson: withIndex to simplify things
        for (x in 0 until xLen ) {
            for (y in 0 until yLen) {
                val currentPoint = Pair(x, y)
                // calculate distance to p, add it to the map at the grid
                val dist = manhattanDist(currentPoint, p)
                if (grid[x][y].keys.contains(dist)) {
                    grid[x][y][dist]!!.add(pointName)
                }
                else {
                    grid[x][y].put(dist, MutableList(1){pointName})
                }
            }
        }
    }

    // get the minimum for each grid
    var minGrid = List(xLen) { MutableList(yLen) { -1 } }
    for (x in 0 until xLen) {
        for (y in 0 until yLen) {
            val minDist = grid[x][y].minBy { it.key }
            if (minDist!!.value.size == 1) { // only set it, if there's no tie
                minGrid[x][y] = minDist!!.value.first()
            }
        }
    }

    // finally, get the count of items
    var pointToArea = HashMap<Int, Int>()

    minGrid.forEach{row ->
        row.forEach { closestPoint ->
            if (closestPoint != -1) {
                if (pointToArea.keys.contains(closestPoint)) {
                    pointToArea[closestPoint] = pointToArea[closestPoint]!! + 1
                } else {
                    pointToArea.put(closestPoint, 1)
                }
            }
        }
    }


    // Debug -- draw the graphs
//    minGrid.forEach{
//        println(it)
//    }

    println("Points to Area: " + pointToArea)
    println("Areas Sorted: " + pointToArea.values.sorted())
    println("The max point with area is: " + pointToArea.maxBy{ it.value })
    println("The max area is: " + pointToArea.values.max()) // 6628 is too high
}

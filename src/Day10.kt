fun main(args: Array<String>) { // https://adventofcode.com/2018/day/10
    part10_1()
    // part2 is just # of seconds to wait
}

fun part10_1() {
    val input = readAsStringList("Day10.txt", "\n")

    // Idea -- the Y's keep getting smaller
    var prevY = 1000000L
    val positions = mutableListOf<Pair<Int, Int>>()
    val velocities = mutableListOf<Pair<Int, Int>>()

    input.forEach { line ->
        val split = line.split("<", ">", ",")
        positions.add(Pair(split[1].trim().toInt(), split[2].trim().toInt()))
        velocities.add(Pair(split[4].trim().toInt(), split[5].trim().toInt()))
    }

    var i = 0
    loop@ while (i < 11000) {
        for ((idx, pos) in positions.withIndex()) {
            positions[idx] = Pair(pos.first + velocities[idx].first, pos.second + velocities[idx].second)
        }

        val minY = positions.minBy { it.second }!!.second
        val maxY = positions.maxBy { it.second }!!.second
        val currentYDiff = Math.abs(maxY - minY)
        if (currentYDiff > prevY) {
            break@loop
        }

        prevY = currentYDiff.toLong()

        i++
    }

    // undo one move
    for ((idx, pos) in positions.withIndex()) {
        positions[idx] = Pair(pos.first - velocities[idx].first, pos.second - velocities[idx].second)
    }

    // draw the results -- transpose it
    val minX = positions.map { it.first }.min()
    val maxX = positions.map { it.first }.max()
    val minY = positions.map { it.second }.min()
    val maxY = positions.map { it.second }.max()

    println("X size: ${Math.abs(maxX!! - minX!! + 1)}")
    println("Y size: ${Math.abs(maxY!! - minY!! + 1)}")

    val grid = List(Math.abs(maxY!! - minY!! + 1)) { MutableList(Math.abs(maxX!! - minX!! + 1)) { "-" } }

    positions.forEach { point ->
        grid[point.second - minY][point.first - minX!!] = "#"
    }

    println(i)
    println("")
    grid.forEach{println(it)}

    /**  FNRGPBHR, after 10511 iterations
     * [#, #, #, #, #, #, -, -, #, -, -, -, -, #, -, -, #, #, #, #, #, -, -, -, -, #, #, #, #, -, -, -, #, #, #, #, #, -, -, -, #, #, #, #, #, -, -, -, #, -, -, -, -, #, -, -, #, #, #, #, #, -]
    [#, -, -, -, -, -, -, -, #, #, -, -, -, #, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #]
    [#, -, -, -, -, -, -, -, #, #, -, -, -, #, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, -, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #]
    [#, -, -, -, -, -, -, -, #, -, #, -, -, #, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, -, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #]
    [#, #, #, #, #, -, -, -, #, -, #, -, -, #, -, -, #, #, #, #, #, -, -, -, #, -, -, -, -, -, -, -, #, #, #, #, #, -, -, -, #, #, #, #, #, -, -, -, #, #, #, #, #, #, -, -, #, #, #, #, #, -]
    [#, -, -, -, -, -, -, -, #, -, -, #, -, #, -, -, #, -, -, #, -, -, -, -, #, -, -, #, #, #, -, -, #, -, -, -, -, -, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #, -, -, #, -, -, #, -, -]
    [#, -, -, -, -, -, -, -, #, -, -, #, -, #, -, -, #, -, -, -, #, -, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, -, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #, -, -, #, -, -, -, #, -]
    [#, -, -, -, -, -, -, -, #, -, -, -, #, #, -, -, #, -, -, -, #, -, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, -, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #, -, -, #, -, -, -, #, -]
    [#, -, -, -, -, -, -, -, #, -, -, -, #, #, -, -, #, -, -, -, -, #, -, -, #, -, -, -, #, #, -, -, #, -, -, -, -, -, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #]
    [#, -, -, -, -, -, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #, -, -, -, #, #, #, -, #, -, -, #, -, -, -, -, -, -, -, #, #, #, #, #, -, -, -, #, -, -, -, -, #, -, -, #, -, -, -, -, #]
     */
}
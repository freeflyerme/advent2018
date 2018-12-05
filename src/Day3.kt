fun main(args: Array<String>) {  // https://adventofcode.com/2018/day/3
//    part3_1()
    part3_2()

}

fun part3_2() {
    val input = readAsStringList( "Day3.txt", "\n")
    val size = 1000
    //  println("#1 @ 108,350: 22x29".split("#", " ", ",", "@", ":", "x"))

    // Lesson: How to quickly and easily initialize a list -- or a grid
    // Lesson: Kotlin distinguishes between Mutable and Immutable Lists -- https://kotlinlang.org/docs/reference/collections.html
    var grid = MutableList(size) { MutableList(size) {0} }

    val notOverlapped = ArrayList<Int>()
    //Ex:    #1 @ 108,350: 22x29
    // populate the map
    for (line in input) {
        val split = line.split("#", " ", ",", "@", ":", "x" )
        val listNumber = split[1].toInt()
        val startX = split[4].toInt()
        val startY = split[5].toInt()
        val recX = split[7].toInt()
        val recY = split[8].toInt()

        var allGood = true
        for (xIter in 0 until recX) {
            val xLoc = xIter + startX
            for (yIter in 0 until recY) {
                val yLoc = yIter + startY
                if (!markPos(listNumber, xLoc, yLoc, grid)) {
                    allGood = false
                }
            }
        }

        if (allGood) {
            notOverlapped.add(listNumber)
        }

    }

     // find the right answer -- can be more efficient by keeping a running tally of everything and checking the results
     for (line in input) {
        val split = line.split("#", " ", ",", "@", ":", "x" )
        val listNumber = split[1].toInt()
        val startX = split[4].toInt()
        val startY = split[5].toInt()
        val recX = split[7].toInt()
        val recY = split[8].toInt()

        val size = recX * recY
        val actualSize = grid.fold(0) { total, array -> array.count{ e -> e == listNumber} + total }

         if (size == actualSize) {
             println("Found no overlap! " + listNumber)
         }

    }

}

fun part3_1() {
    val input = readAsStringList( "Day3.txt", "\n")
    val size = 1000
    //  println("#1 @ 108,350: 22x29".split("#", " ", ",", "@", ":", "x"))

    // Lesson: How to quickly and easily initialize a list -- or a grid
    // Lesson: Kotlin distinguishes between Mutable and Immutable Lists -- https://kotlinlang.org/docs/reference/collections.html
    var grid = MutableList(size) { MutableList(size) {0} }

    //Ex:    #1 @ 108,350: 22x29
    // populate the map
    for (line in input) {
        val split = line.split("#", " ", ",", "@", ":", "x" )
        val listNumber = split[1].toInt()
        val startX = split[4].toInt()
        val startY = split[5].toInt()
        val recX = split[7].toInt()
        val recY = split[8].toInt()

        for (xIter in 0 until recX) {
            val xLoc = xIter + startX
            for (yIter in 0 until recY) {
                val yLoc = yIter + startY
                markPos(listNumber, xLoc, yLoc, grid)
            }
        }

    }

    // count number of -1's
    println(grid.fold(0) { total, array -> array.count{ e -> e == -1} + total })
    // Verification
    //    for (i in 1 until size) {
    //        println(grid[i])
    //    }

}

// part2: modification to return true for no overlap, and false for overlap
fun markPos(listNumber: Int, xLoc: Int, yLoc: Int, grid: MutableList<MutableList<Int>>): Boolean {
    if (grid.get(xLoc).get(yLoc) == 0) {
        grid[xLoc][yLoc] = listNumber
        return true
    } else {
        grid[xLoc][yLoc] = -1
        return false
    }
}

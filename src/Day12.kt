fun main(args: Array<String>) {
//    part12_1_redux()
//    part12_2()
    println((50000000000L-101)*67 + 6767)  // 3350000000000
}

data class Day12SlidingWindow(var matchingPatterns: List<List<Int>>, var currentCheckingPos: Int, var startingPosition: Int )

fun part12_2() {  // pattern repeats, increasing by 67 for every number after 102
    val initialState = //"#..#.#..##......###...###"
        "##...#...###.#.#..#...##.###..###....#.#.###.#..#....#..#......##..###.##..#.##..##..#..#.##.####.##"

    var STARTING_FLOWERS = initialState.toCharArray().map { it -> if (it == '#') { 1 } else { 0 } }.toMutableList()
    var newFlowers = MutableList(100){0}
    newFlowers.addAll(STARTING_FLOWERS)
    newFlowers.addAll(MutableList(100){0}) // start 0 at 100

    var patternToValue = mutableMapOf<List<Int>, Int>()
    readAsStringList("Day12.txt", "\n").map { line ->
        val split = line.split(" ")

        var aPattern = split[0].map{ char ->
            if (char == '#') { 1 } else { 0 }
        }.toList()
        patternToValue.put(aPattern, if (split[2] == "#") 1 else 0)
    }

    // find a repeat, and remember the number.  Mod 5 billion, then get the nth item
    var seenPatterns = mutableMapOf<List<Int>, Int>()
    seenPatterns[newFlowers] = 0

    var repeatNum = 0
    loop@for (i in 1..300) {
        var nextGen = createNextGen(newFlowers, patternToValue)
        if (seenPatterns.keys.contains(nextGen)) {
            repeatNum = i
//            newFlowers = nextGen // debug
            break@loop
        } else {
            seenPatterns[nextGen] = i
        }
        newFlowers = nextGen
        println("$i : ${(0 until nextGen.size).map{ (it - 100) * nextGen[it] }.sum()}")
    }

    val remainder = 50000000000L % (repeatNum - 1)

    val found = seenPatterns.filterValues { it == remainder.toInt() }.keys.first().toMutableList()
    val sum = (0 until found.size).map{ (it - 100) * found[it] }.sum()

    println("Sum: $sum")  // 6222 too low
}

private fun createNextGen(
    newFlowers: MutableList<Int>,
    patternToValue: MutableMap<List<Int>, Int>
): MutableList<Int> {
    var nextGenFlowers = MutableList(newFlowers.size) { 0 }

    // start the search -- a set of searches
    for (fIdx in 0..newFlowers.size - 5) {
        nextGenFlowers[fIdx + 2] = patternToValue.get(newFlowers.subList(fIdx, fIdx + 5))!!
    }
    return nextGenFlowers
}

fun part12_1_redux() {
    val initialState = //"#..#.#..##......###...###"
        "##...#...###.#.#..#...##.###..###....#.#.###.#..#....#..#......##..###.##..#.##..##..#..#.##.####.##"

    var STARTING_FLOWERS = initialState.toCharArray().map { it -> if (it == '#') { 1 } else { 0 } }.toMutableList()
    var newFlowers = MutableList(100){0}
    newFlowers.addAll(STARTING_FLOWERS)
    newFlowers.addAll(MutableList(100){0}) // start 0 at 100

    var patternToValue = mutableMapOf<List<Int>, Int>()
    readAsStringList("Day12.txt", "\n").map { line ->
        val split = line.split(" ")

        var aPattern = split[0].map{ char ->
            if (char == '#') { 1 } else { 0 }
        }.toList()
        patternToValue.put(aPattern, if (split[2] == "#") 1 else 0)
    }

    for (i in 1..20) {
        // add 2 to beginning and end
        var nextGenFlowers = MutableList(newFlowers.size) { 0 }

        // start the search -- a set of searches
        for (fIdx in 0..newFlowers.size - 5) {
            nextGenFlowers[fIdx+2] = patternToValue.get(newFlowers.subList(fIdx, fIdx+5))!!
        }

        newFlowers = nextGenFlowers
    }

    val sum = (0 until newFlowers.size).map{ (it - 100) * newFlowers[it] }.sum()  // 3299 too low

    println("Sum: $sum")
}


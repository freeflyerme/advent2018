fun main(args: Array<String>) {
    part12_1()
}

data class Day12SlidingWindow(var matchingPatterns: List<List<Int>>, var currentCheckingPos: Int, var startingPosition: Int )

fun part12_1() {
    val initialState = "#..#.#..##......###...###"
    //"##...#...###.#.#..#...##.###..###....#.#.###.#..#....#..#......##..###.##..#.##..##..#..#.##.####.##"

    var flowers = initialState.toCharArray().map { it -> if (it == '#') { 1 } else { 0 } }.toMutableList()

    var patternToValue = mutableMapOf<List<Int>, Int>()
    var patterns = mutableListOf<List<Int>>()

    readAsStringList("scratch.txt", "\n").map { line ->
        val split = line.split(" ")

        var aPattern = split[0].map{ char ->
            if (char == '#') { 1 } else { 0 }
        }.toList()

        patterns.add(aPattern)
        patternToValue.put(aPattern, if (split[2] == "#") 1 else 0)
    }

    var ongoingWindows = mutableListOf<Day12SlidingWindow>()

    for (i in 1..20) {
        // add 2 to beginning and end

        flowers.add(0, 0)
        flowers.add(0, 0)
        flowers.add(flowers.size, 0)
        flowers.add(flowers.size, 0)

        var nextGenFlowers = MutableList(flowers.size) { 0 }

        // start the search -- a set of searches

        flowers.forEachIndexed { index, element ->
            ongoingWindows.add(Day12SlidingWindow(patterns, -1, index))

            // update existing searches -- add / remove as necessary
            ongoingWindows.map { aWindow ->
                aWindow.currentCheckingPos += 1
                aWindow.matchingPatterns = aWindow.matchingPatterns.filter {
                    aPattern ->
                    if (aWindow.currentCheckingPos > 4) {
                        println("here")
                    }
                    aPattern.get(aWindow.currentCheckingPos) == element }
            }

           val toRemove = ongoingWindows.filter { aPattern ->
                aPattern.matchingPatterns.isEmpty()
            }

            ongoingWindows.removeAll(toRemove)

            // if match, add it to next generation, and remove it from the set
            var matching = ongoingWindows.find { window ->
                window.currentCheckingPos == 4
            }

            if (matching != null) {
                nextGenFlowers[matching.startingPosition + 2] = patternToValue[matching.matchingPatterns[0]]!!
                ongoingWindows.remove(matching)
            }

        }

        flowers = nextGenFlowers
    }

    val sum = (0 until flowers.size).map{ (it - 2 * 20) * flowers[it] }.sum()  // 3299 too low

    println("Sum: $sum")
}
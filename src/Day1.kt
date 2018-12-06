fun main(args : Array<String>) {
    // https://adventofcode.com/2018/day/1#part2
    part1()
    part2()
}

fun part1() {
    val input = readAsIntList("Day1.txt", "\n")
    println(input.fold(0){ total, next -> total + next })
}

fun part2() {
    // keep track of histories, and stop soon as it's found
    val input = readAsIntList("Day1.txt", "\n")

    val visited = HashSet<Int>()
    var i = 0
    var freq = 0
   find@ while (true) {
        i %= input.size
        freq += input[i]
        if (visited.contains(freq)) {
            println("Found duplicate! " + freq)
            break@find;
        } else {
            visited.add(freq);
        }
        i++
    }
}
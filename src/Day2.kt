fun main(args: Array<String>) {  // https://adventofcode.com/2018/day/2#part2
//    part2_1()
    part2_2()
}

fun part2_2() {
    val input = readAsStringList("Day2.txt", "\n")
    var i = 0
    var j = 0
    top@ while (i < input.size) {
        j = i
        while (j < input.size) {
            val diffPos = diffByOne(input[i], input[j])
            if (diffPos > -1) {
                println(input[i].substring(0, diffPos) + input[i].substring(diffPos+1))
                break@top
            }
            j++
        }
        i++
    }

}

fun diffByOne(s: String, s1: String): Int {
    val char1 = s.toCharArray()
    val char2 = s1.toCharArray()
    if (char1.size != char2.size) {
        return -1
    }
    var diffPos = -1
    for (i in char1.indices) { // Lesson: use Array.indices to easily for loop through them
        if (char1[i] != char2[i]) {
            if (diffPos == -1) {
                diffPos = i
            } else {
                return -1
            }
        }
    }
    return diffPos
}

fun part2_1() {
    val input = readAsStringList("Day2.txt", "\n")
    var twoOccurrence = 0
    var threeOccurrence = 0
    for (line in input) {
        val hash = toWordSoup(line)
        if (hash.values.contains(2)) {
            twoOccurrence++
        }
        if (hash.values.contains(3)) {
            threeOccurrence++
        }
    }

    println(twoOccurrence * threeOccurrence)
}

fun toWordSoup(input : String): HashMap<Char, Int> {
    var result = HashMap<Char, Int>()
    for (c in input) {
        // Lesson: optimization using the optional -- default to a sensible value and always override
        var occur = result.get(c) ?: 0
        occur++
        result.put(c, occur)

    }
    return result
}
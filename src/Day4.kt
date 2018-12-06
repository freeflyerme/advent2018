fun main(args: Array<String>) {
    part4_1()
//    print("[1518-11-01 00:00] Guard #10 begins shift".split("[", "]", " "))
    // [, 1518-11-01, 00:00, , Guard, #10, begins, shift]
}

fun part4_1() {
    val input = readAsStringList("Day4.txt", "\n")
    input.sort()
    // sort everything
    // get guard number
    // mark wake / asleep on data structure
    // find the highest number for each -- guard and number

    val guardToMins = HashMap<Int, MutableList<Int>>()

    var currentGuard = -1
    var sleepStart = -1
    var sleepEnd = -1
    for (l in input) {
        val split = l.split("[", "]", " ")
        // guard --> keep track, set current guard
        // wake up --> get start
        // sleep --> set end
        if (split[4] == "Guard") {
            currentGuard = split[5].substringAfter("#").toInt()
            if (!guardToMins.keys.contains(currentGuard)) {
                guardToMins[currentGuard] = MutableList(60) {0}
            }
        }
        else if (split[4] == "falls") {
            if (split[2].substringAfter(" ").substringBefore(":").toInt() == 23) {
                sleepStart = 0
            } else {
                sleepStart = split[2].substringAfter(":").toInt()
            }
        }

        else if (split[4] == "wakes") {
            sleepEnd = split[2].substringAfter(":").toInt()
            // increment the intervals
            for (i in sleepStart until sleepEnd) {
                guardToMins[currentGuard]!![i] = guardToMins[currentGuard]!![i] + 1 // Lesson: Null assertion
            }
            sleepStart = -1
            sleepEnd = -1
        }
    }

    println("=== part1 === ")
    part1Output(guardToMins)
    println("=== part2 === ")
    part2Output(guardToMins)
}

fun part2Output(guardToMins: HashMap<Int, MutableList<Int>>) {
 // get the guard with the most amount of sleep in that minute
    var maxSleepByGuard = HashMap<Int, Triple<Int, Int, Int>>() // the minute, and the number, most min slept
    guardToMins.forEach { guard, mins ->
        val minute = mins.indices.maxBy { mins[it] } ?: -1
        val value = mins[minute]
        val minAsleep = mins.sum() // most minutes asleep -- overall!
        maxSleepByGuard[guard] = Triple(minute, value, minAsleep)
    }

    // part 1
    val theGuard = maxSleepByGuard.maxBy { e -> e.value.second }
    println("Guard Number: " + theGuard!!.key)
    println("Guard Minute: " + theGuard!!.value.first)
    println("Guard Minute Times: " + theGuard!!.value.second)
    println("Guard Total asleep time: " + theGuard!!.value.third)
    println("Desired Answer: " + theGuard!!.key * theGuard!!.value.first) // to low: 65854, 84015
}

private fun part1Output(guardToMins: HashMap<Int, MutableList<Int>>) {
    // get the guard with the highest minutes slept number
    var maxSleepByGuard = HashMap<Int, Triple<Int, Int, Int>>() // the minute, and the number, most min slept
    guardToMins.forEach { guard, mins ->
        val minute = mins.indices.maxBy { mins[it] } ?: -1
        val value = mins[minute]
        val minAsleep = mins.sum() // most minutes asleep -- overall!
        maxSleepByGuard[guard] = Triple(minute, value, minAsleep)
    }

    // part 1
    val theGuard = maxSleepByGuard.maxBy { e -> e.value.third }
    println("Guard Number: " + theGuard!!.key)
    println("Guard Minute: " + theGuard!!.value.first)
    println("Guard Minute Times: " + theGuard!!.value.second)
    println("Guard Total asleep time: " + theGuard!!.value.third)
    println("Desired Answer: " + theGuard!!.key * theGuard!!.value.first) // to low: 65854, 84015
}


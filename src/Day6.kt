fun main(args: Array<String>) {
    part6_1()
}

fun part6_1() {
    // Algorithm -- use the edges as the boundaries for the area to be considered
    // the finite rectangle that results, update each item with the distance from the others.  Use distance as the key, and number as value to simplify ties
    // make a final pass to get the minimum, marking it each.
    // make a pass to collect the ones that have the most -> one pass only, store in hashmap with a reduce function
    val points = readAsStringList("Day6.txt", "\n").map { it -> val l = it.split(",")
            Pair(l[0].toInt(), l[1].toInt()) }


}

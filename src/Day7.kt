fun main(args: Array<String>) {
//    part7_1()
//    part7_2()
    println(Character.getNumericValue('a'))
}

val CHARACTER_TO_NUMBER_DAY7 = characterToNumber(true, 1)
val BASE_TIME_DAY7 = 60

fun part7_2() {
    // scheduler -- 2 goals
    // 1. Schedule the next available worker to work on the next thing -- whenever a relevant state change (something is done)
    // To do so, need to track each worker's end times for their task, and see if they're open / blocked


    // data structure for workers -- an array.  Each index is the worker itself, the value: -1 means available, a >0 num means occupied, and when they'll be done
    // need a clock to keep track of time
    var clock = 0
    var workers = (0..4).map{ it to 0 }.toMap().toMutableMap()

    val input = readAsStringList("Day7.txt", "\n")
    val parentToChild = input.map { line -> val split = line.split(" "); Pair(split[1], split[7]) }
    val parents = parentToChild.map { it.first }.toSet()
    val children = parentToChild.map { it.second }.toSet()
    val root = parents.subtract(children)

    // create the graph
    val allNodes = parents.union(children).map{ name -> name to Node(mutableListOf(), mutableListOf()) }.toMap()

    parentToChild.forEach{ pair -> // parent, child
        allNodes[pair.first]!!.children.add(pair.second)
        allNodes[pair.second]!!.parent.add(pair.first)
    }

    // traversal algorithm -- have 2 queues -- first is ready to go queue.  Second is available to traverse, but possibly blocked queue.
    // for each iteration, check the ready to traverse queue, and move any into the ready to go queue.
    // then sort the ready to go queue alphabetically, picking the first
    // repeat until we're done -- e.g. everything's been visited.

    // To do so, we need 2 queues, and a visited set
    var toVisitPool = mutableListOf<String>()
    var visitCandidatePool = mutableListOf<String>()
    var visited = HashSet<String>()
    var visitOrder = mutableListOf<String>()


    toVisitPool.addAll(root)

    loop@ while (visited.size < allNodes.size) {
        // check nextButBlockedPool -- remove any unblocked, and add to visit pool
        val unblocked = visitCandidatePool.filter { nodeName -> visited.containsAll(allNodes.get(nodeName)!!.parent) }
        visitCandidatePool.removeAll(unblocked)
        toVisitPool.addAll(unblocked)
        toVisitPool.sort()


        // add in some clock and scheduler logic
        // if available worker, visit
        if (workers.values.find { it <= clock } != null) { // available worker
            val visiting = toVisitPool.removeAt(0)

            val anAvailableWorker = workers.filterValues { it <= clock }.entries.first().key
            workers[anAvailableWorker] = getTimeToComplete(visiting, clock)

            visitOrder.add(visiting)
            visited.add(visiting)
            allNodes.get(visiting)!!.children.forEach { child ->
                if (!visited.contains(child) && !visitCandidatePool.contains(child) && !toVisitPool.contains(child)) {
                    visitCandidatePool.add(child)
                }
            }
        } else { // wait and then try again
            clock =
            continue@loop
        }

    }

    visitOrder.forEach{print(it)}
    println("Total time taken: " + clock)
}

fun getTimeToComplete(letter: String, currentTime : Int): Int {
    return CHARACTER_TO_NUMBER_DAY7.get(letter) + currentTime + BASE_TIME_DAY7
}

data class Node constructor (var children : MutableList<String>, var parent : MutableList<String>)

fun part7_1() {
    val input = readAsStringList("Day7.txt", "\n")
    val parentToChild = input.map { line -> val split = line.split(" "); Pair(split[1], split[7]) }
    val parents = parentToChild.map { it.first }.toSet()
    val children = parentToChild.map { it.second }.toSet()
    val root = parents.subtract(children) // hopefully just 1 root -- check

    // create the graph
    val allNodes = parents.union(children).map{ name -> name to Node(mutableListOf(), mutableListOf()) }.toMap()

    parentToChild.forEach{ pair -> // parent, child
        allNodes[pair.first]!!.children.add(pair.second)
        allNodes[pair.second]!!.parent.add(pair.first)
    }

    // traversal algorithm -- have 2 queues -- first is ready to go queue.  Second is available to traverse, but possibly blocked queue.
    // for each iteration, check the ready to traverse queue, and move any into the ready to go queue.
    // then sort the ready to go queue alphabetically, picking the first
    // repeat until we're done -- e.g. everything's been visited.

    // To do so, we need 2 queues, and a visited set
    var toVisitPool = mutableListOf<String>()
    var visitCandidatePool = mutableListOf<String>()
    var visited = HashSet<String>()
    var visitOrder = mutableListOf<String>()


    toVisitPool.addAll(root)

    while (visited.size < allNodes.size) {
        // check nextButBlockedPool -- remove any unblocked, and add to visit pool
        val unblocked = visitCandidatePool.filter { nodeName -> visited.containsAll(allNodes.get(nodeName)!!.parent) }
        visitCandidatePool.removeAll(unblocked)
        toVisitPool.addAll(unblocked)
        toVisitPool.sort()

        val visiting = toVisitPool.removeAt(0)
        visitOrder.add(visiting)
        visited.add(visiting)
        allNodes.get(visiting)!!.children.forEach { child ->
            if (!visited.contains(child) && !visitCandidatePool.contains(child) && !toVisitPool.contains(child)) {
                visitCandidatePool.add(child)
            }
        }
    }

    visitOrder.forEach{print(it)}
}
fun main(args: Array<String>) {  // https://adventofcode.com/2018/day/7
//    part7_1()    // OCPUEFIXHRGWDZABTQJYMNKVSL is answer
    part7_2()  // 991 is answer
}

val CHARACTER_TO_NUMBER_DAY7 = characterToNumber(true, 1)
val BASE_TIME_DAY7 = 60  // TODO GW: reset to 60
val NUM_WORKERS_DAY7 = 5  // TODO GW: reset to 5
// Lesson: be suspicious of 1 off results -- if the program is correct, maybe I'm incorrectly adding a +1 to the result due to bad input parameters (here, it's due to too many workers in example)

fun part7_2() {
    // scheduler -- 2 goals
    // 1. Schedule the next available worker to work on the next thing -- whenever a relevant state change (something is done)
    // To do so, need to track each worker's end times for their task, and see if they're open / blocked


    // data structure for workers -- an array.  Each index is the worker itself, the value: -1 means available, a >0 num means occupied, and when they'll be done
    // need a clock to keep track of time
    var currentTime = 0
    var workersCurrentTask = MutableList(NUM_WORKERS_DAY7) { "" }
    var workersFinishTime = MutableList(NUM_WORKERS_DAY7){ -1 }

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
        // want to do 2 things -- always visit something, might not be the one that's dequeued.
        // and 2nd, always want to advance time to the last thing that finished, so there's at least 1 worker

        // check nextButBlockedPool -- remove any unblocked, and add to visit pool
        val unblocked = visitCandidatePool.filter { nodeName -> visited.containsAll(allNodes.get(nodeName)!!.parent) }
        visitCandidatePool.removeAll(unblocked)
        toVisitPool.addAll(unblocked)
        toVisitPool.sort()

        // while there are available workers and things to queue, queue up work
//        while ()
        // visiting, but not visited -- que up the work
        var anAvailableWorkerIdx = findNextAvailableWorkerIdx(workersFinishTime)
        while (anAvailableWorkerIdx != -1 && !toVisitPool.isEmpty()) {
            val visiting = toVisitPool.removeAt(0)

            workersCurrentTask[anAvailableWorkerIdx] = visiting
            workersFinishTime[anAvailableWorkerIdx] = getTimeToComplete(visiting, currentTime)

            anAvailableWorkerIdx = findNextAvailableWorkerIdx(workersFinishTime)
        }

        // after queueing up all we can, we need to advance the clock and wait for the next worker to be available
        // visited some node -- whichever is first to finish
        val nextFinishedIdxAndTime = workersFinishTime.withIndex().filter { it.value > 0 }.minBy { it.value }
        currentTime = nextFinishedIdxAndTime!!.value
        val justFinishedName = workersCurrentTask[nextFinishedIdxAndTime.index]

        visitOrder.add(justFinishedName)
        visited.add(justFinishedName)
        allNodes.get(justFinishedName)!!.children.forEach { child ->
            if (!visited.contains(child) && !visitCandidatePool.contains(child) && !toVisitPool.contains(child)) {
                visitCandidatePool.add(child)
            }
        }

        // mark the nextFinished worker as available
        workersCurrentTask[nextFinishedIdxAndTime.index] = ""
        workersFinishTime[nextFinishedIdxAndTime.index] = -1

    }

    visitOrder.forEach{print(it)}
    println("")
    println("Total time taken: " + (currentTime))  // 991 is the answer
}

private fun findNextAvailableWorkerIdx(workersFinishTime: MutableList<Int>): Int {
    var anAvailableWorkerIdx = -1
    find@ for ((index, value) in workersFinishTime.withIndex()) {
        if (value == -1) {
            anAvailableWorkerIdx = index
            break@find
        }
    }
    return anAvailableWorkerIdx
}

fun getTimeToComplete(letter: String, currentTime : Int): Int {
    return CHARACTER_TO_NUMBER_DAY7.get(letter)!! + currentTime + BASE_TIME_DAY7
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
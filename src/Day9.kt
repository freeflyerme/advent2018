import java.util.*

fun main(args: Array<String>) { // https://adventofcode.com/2018/day/9
//    part9_1()
    part9_2()
}

class LinkedNode(val num: Long) {
    var prevNode: LinkedNode = this
    var nextNode: LinkedNode = this
}

fun part9_2() {
    // need an actual list implementation to be fast

    // clockwise ==> next
    // counterclickwise ==> prev
    val root = LinkedNode(num = 0)
    val NUM_PLAYERS = 452
    val LAST_MARBLE = 70784 * 100
    val playerScores = MutableList(NUM_PLAYERS){0L}


    var current = root
    for (i in 1 .. LAST_MARBLE) {
        if ((i % 23) != 0) {
            val next = current.nextNode
            // add in next
            val newNode = LinkedNode(i.toLong())
            newNode.nextNode = next.nextNode
            newNode.prevNode = next
            next.nextNode = newNode

            newNode.nextNode.prevNode = newNode
            current = newNode
        } else {
            var playerIdx = (i % NUM_PLAYERS)
            playerScores[playerIdx] += i.toLong()

            (1..7).forEach{ current = current.prevNode }
            val seventhPrevNode = current

            // add 7th score, then remove it, making current the one next
            current = seventhPrevNode.nextNode
            playerScores[playerIdx] += seventhPrevNode.num

            val before7 = seventhPrevNode.prevNode
            before7.nextNode = current
            current.prevNode = before7
        }

    }
    println(playerScores.max())
}

fun part9_1() {
    val NUM_PLAYERS = 452
    val LAST_MARBLE = 70784 * 10
    val playerScores = MutableList(NUM_PLAYERS){0}

    var currentIndex = 1

    var board = mutableListOf<Int>()
    board.add(0)

    loop@ for (i in 1 .. LAST_MARBLE) {
        if ((i % 23) != 0) {
            currentIndex = getNextMarbleLocation(currentIndex, board.size)
            if (currentIndex == 0) {
                board.add(board.size, i)
                currentIndex = board.size - 1
            } else {
                board.add(currentIndex, i)
            }
        } else {
            var score = i
            var pos7CC = get7CounterClockwise(currentIndex, board.size)
            var removed = board.removeAt(pos7CC)
            currentIndex = (pos7CC ) % board.size // one clockwise from just removed
            score += removed

            var playerIdx = (i % NUM_PLAYERS)
            playerScores[playerIdx] += score
        }
    }

    println(playerScores.max())


}

fun getNextMarbleLocation(currentIndex: Int, boardSize: Int): Int {
    return ((currentIndex + 2) % boardSize)   // Lesson: order of operations
}

fun get7CounterClockwise(currentIndex: Int, boardSize: Int): Int {
    return (currentIndex - 7 + boardSize) % boardSize
}
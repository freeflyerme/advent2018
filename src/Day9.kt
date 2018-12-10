import java.util.*

fun main(args: Array<String>) {
    part9_1()
}

fun part9_1() {
    val NUM_PLAYERS = 9
    val PLAYER_SCORES = MutableList(9){0}

    var currentTurn = 0
    var currentIndex = 1

    var board = LinkedList<Int>()
    board.add(0)

    for (i in 1 .. 22) {
        currentIndex = getNextMarbleLocation(currentIndex, board.size)
        if (currentIndex == 0) {
            board.add(board.size, i)
            currentIndex = board.size - 1
        } else {
            board.add(currentIndex, i)
        }
        println(board)
    }


}

fun getNextMarbleLocation(currentIndex: Int, boardSize: Int): Int {
    return ((currentIndex + 2) % boardSize)   // Lesson: order of operations
}
import java.util.*

fun readAsStringList(fileName : String, separator : String): ArrayList<String> {
    val input = ClassLoader.getSystemResource("resources/" + fileName).readText().trim().split(separator)
    return ArrayList(input) // Lesson: inferred return type, no conversion required
}

fun readAsIntList(fileName : String, separator : String): ArrayList<Int> {
    val input = ClassLoader.getSystemResource("resources/" + fileName).readText().trim().split(separator).map{ it.toInt() }
    return ArrayList(input)
}

fun readLineByLine(fileName: String): Scanner {
    val scan = Scanner(ClassLoader.getSystemResourceAsStream("resources/" + fileName))
    return scan
}

fun readString(fileName: String): String {
    return ClassLoader.getSystemResource("resources/" + fileName).readText()
}

// Utilizes the "born context" of the parsingFunction to populate a result.  Less functional
fun readFileIntoData(fileName: String, split: String, parsingFunction: (String) -> Unit) {
    val fileAsString = readString(fileName)
    val fileSplit = fileAsString.split(split)
    fileSplit.forEach { part ->
        parsingFunction(part)
    }

}

/**
 * Pre-condition: toVisit is populated with the first item to visit
 * Each visited node has an action performed upon it.  Action is optional
 * Post-Condition: includes has all of the elements visited
 */
fun <T> breadthFirstVisit(toVisit: LinkedList<T>, included: HashSet<T>, adjList: HashMap<T, ArrayList<T>>,
                          action: (T) -> Unit = {T -> Unit}) {
    while (!toVisit.isEmpty()) {
        val visited = toVisit.poll()
        included.add(visited)
        action(visited)
        val neighbors = adjList.get(visited)!!
        for (n in neighbors) {
            if (!included.contains(n)) {
                toVisit.add(n)
            }
        }
    }
}

// Day 10 reverse sublist
fun <T> MutableList<T>.reverse(index1: Int, index2: Int, swaps: Int) { // Lesson: Extension class for ArrayList for custom reverse
    var numSwaps = swaps / 2
    var start = index1
    var end = index2
    while (numSwaps > 0) {
        swap(start, end)
        start = (start + 1) % this.size
        end = (end - 1 + size) % size // Lesson: apparently mods can be negative
        numSwaps--
    }
}

fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this' corresponds to the list
    this[index1] = this[index2]
    this[index2] = tmp
}

fun hexToBin(input: String) : String? {
    var hexToBin = HashMap<String, String>()
    hexToBin.put("0","0000")
    hexToBin.put("1","0001")
    hexToBin.put("2","0010")
    hexToBin.put("3","0011")
    hexToBin.put("4","0100")
    hexToBin.put("5","0101")
    hexToBin.put("6","0110")
    hexToBin.put("7","0111")
    hexToBin.put("8","1000")
    hexToBin.put("9","1001")
    hexToBin.put("a","1010")
    hexToBin.put("b","1011")
    hexToBin.put("c","1100")
    hexToBin.put("d","1101")
    hexToBin.put("e","1110")
    hexToBin.put("f","1111")
    return hexToBin.get(input)
}

enum class DIRECTION(val dx: Int,val dy: Int) { // Lesson: Enums
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0)
}

fun getOppositeDirection(dir : DIRECTION): DIRECTION {
    return when (dir) {
        DIRECTION.DOWN -> DIRECTION.UP
        DIRECTION.UP -> DIRECTION.DOWN
        DIRECTION.LEFT -> DIRECTION.RIGHT
        DIRECTION.RIGHT -> DIRECTION.LEFT
    }
}

fun turnLeft(dir : DIRECTION): DIRECTION {
    return when(dir) {
        DIRECTION.UP -> DIRECTION.LEFT
        DIRECTION.DOWN -> DIRECTION.RIGHT
        DIRECTION.LEFT -> DIRECTION.DOWN
        DIRECTION.RIGHT -> DIRECTION.UP
    }
}

fun turnRight(dir : DIRECTION): DIRECTION {
    return when(dir) {
        DIRECTION.LEFT -> DIRECTION.UP
        DIRECTION.RIGHT -> DIRECTION.DOWN
        DIRECTION.DOWN -> DIRECTION.LEFT
        DIRECTION.UP -> DIRECTION.RIGHT
    }
}

fun readToGrid(input: String, value :Int = 1): Array<Array<Int>>{
    val split = input.split("\n")
    var result = Array(split.size, {Array(split.size, {0})})
    for (line in split.withIndex()) {
        for (c in line.value.withIndex()) {
            if (c.value == '#') {
                result[line.index][c.index] = value
            }
        }
    }
    return result
}

fun setSquareInGrid(x : Int, y: Int, input: Array<Array<Int>>, grid: Array<Array<Int>>) {
    for (i in 0 until input.size) {
        for (j in 0 until input.size) {
            grid[x+i][y+j] = input[i][j]
        }
    }
}
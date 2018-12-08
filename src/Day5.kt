fun main(args: Array<String>) { // https://adventofcode.com/2018/day/5
//    part5_1()
    part5_2()
}

fun part5_2() {
    val input = readString("Day5.txt")

    val result = 'a'.rangeTo('z').asSequence().map { c ->
        reactPolymer( input.replace(c.toString(), "").replace(c.toUpperCase().toString(), "").toCharArray().toList() )
    }.min()
    println(result)
}

fun part5_1() {
    val input = readString("Day5.txt")
    val linkedList =  input.toCharArray().toList()
    println(reactPolymer(linkedList))

}

private fun reactPolymer(input: List<Char>): Int{
    var linkedList = input.toMutableList()
    var moreFound = true
    while (moreFound) {
        moreFound = false
        var i = 0
        while (i < linkedList.size - 1) {
            if (linkedList[i].isLowerCase() && linkedList[i].toUpperCase().equals(linkedList.get(i + 1)) ||
                    linkedList.get(i).isUpperCase() && linkedList.get(i + 1) == (linkedList[i].toLowerCase())) {
                linkedList.removeAt(i)
                linkedList.removeAt(i)
                moreFound = true
            } else {
                i++
            }
        }
    }
    return linkedList.size
}
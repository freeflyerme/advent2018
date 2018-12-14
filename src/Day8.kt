fun main(args: Array<String>) {  // https://adventofcode.com/2018/day/8
//    part8_1()
    part8_2()
}

data class Day8NodeGeneric  (  // Lesson how to use an interface with a data class
    val numChildren: Int,
    val numMetadata : Int,
    override val children : MutableList<GenericNode<MutableList<Int>, Int>>,
    override val data: MutableList<Int>,
    override var data2: Int
) : GenericNode<MutableList<Int>, Int>

fun part8_2() {
    val input = readAsIntList("Day8.txt", " ")

    // Lesson: functional programming to simplify traversal.  Built in traversal in interface, then closure to track state inside the object itself (data2 to store)
    val i = input.iterator()
    val root = Day8NodeGeneric(numChildren = i.next(), numMetadata = i.next(), children = mutableListOf(), data = mutableListOf(), data2=0)
    populateNode(root, i)

    // next, need to traverse it and get sum of metadata
    root.depthFirstTraversal { node ->
        // data2 is the metaData value at the node
        if (node.children.isEmpty()) {
            node.data2 = node.data.sum()
        }
        else {
            node.data.forEach { metaData ->
                if (metaData - 1 < node.children.size) { // Lesson -- wrong variable used at first (data instead of children), the logic was always correct
                    node.data2 += node.children[metaData-1].data2
                }
            }
        }
    }

    println("Sum of metadata is : ${root.data2}") // Lesson: String templates (just like Groovy)
}

fun part8_1() {
    val input = readAsIntList("Day8.txt", " ")

    // Lesson: functional programming to simplify traversal
    val i = input.iterator()
    val root = Day8NodeGeneric(numChildren = i.next(), numMetadata = i.next(), children = mutableListOf(), data = mutableListOf(), data2=0)
    populateNode(root, i)

    // next, need to traverse it and get sum of metadata
    var sumMetaData = 0  // Lesson: cool use of a closure -- the context of the function passed in refers to outside variable
    root.depthFirstTraversal { node -> sumMetaData += node.data.sum() }

    println("Sum of metadata is : $sumMetaData") // Lesson: String templates (just like Groovy
}

fun populateNode(node: Day8NodeGeneric, iterator: MutableIterator<Int>) {
    for (child in 0 until node.numChildren) {
        val aChildNode = Day8NodeGeneric(iterator.next(), iterator.next(), mutableListOf(), mutableListOf(), 0)
        populateNode(aChildNode, iterator)
        node.children.add(aChildNode)
    }

    // populate metaData
    for (metaIndex in 0 until node.numMetadata) {
        node.data.add(iterator.next())
    }
}

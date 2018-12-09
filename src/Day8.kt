fun main(args: Array<String>) {
    part8_1()
}

data class Day8Node constructor (val numChildren: Int, val numMetadata : Int, var children : MutableList<Day8Node>, var metadata: MutableList<Int>)

data class Day8NodeGeneric  (
    val numChildren: Int,
    val numMetadata : Int,
    override val children : MutableList<GenericNode<MutableList<Int>>>,
    override val data: MutableList<Int>
) : GenericNode<MutableList<Int>>

fun part8_1() {
    val input = readAsIntList("scratch.txt", " ")

    // need a data class -- get num child nodes, get num meta data, then proceed to get each
    val i = input.iterator()
    val root = Day8Node(i.next(), i.next(), mutableListOf(), mutableListOf())
    populateNode(root, i)

    // next, need to traverse it and get sum of metadata
}

fun populateNode(node: Day8Node, iterator: MutableIterator<Int>) {
    for (child in 0 until node.numChildren) {
        val aChildNode = Day8Node(iterator.next(), iterator.next(), mutableListOf(), mutableListOf())
        populateNode(aChildNode, iterator)
        node.children.add(aChildNode)
    }

    // populate metaData
    for (metaIndex in 0 until node.numMetadata) {
        node.metadata.add(iterator.next())
    }
}

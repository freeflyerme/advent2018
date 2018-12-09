interface GenericNode<T> {
    val children : MutableList<GenericNode<T>>
    val data : T
    fun depthFirstTraversal(action: (GenericNode<T>) -> Unit ) {
        children.forEach { child ->
            child.depthFirstTraversal(action)
        }
        action(this)
    }

}


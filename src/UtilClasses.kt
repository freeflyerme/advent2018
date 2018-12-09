interface GenericNode<T, R> {  // Lesson: value in generic class and also a traversal built in
    val children : MutableList<GenericNode<T, R>>
    val data : T
    var data2: R
    fun depthFirstTraversal(action: (GenericNode<T, R>) -> Unit ) {
        children.forEach { child ->
            child.depthFirstTraversal(action)
        }
        action(this)
    }

}


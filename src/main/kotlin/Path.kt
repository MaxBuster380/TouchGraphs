/*
 * MIT License
 *
 * Copyright (c) 2023 MaxBuster380
 *
 * This is the "Path.kt" file from the TouchGraphs project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * # Path
 *
 * A path is a finite sequence of edges which joins a sequence of vertices.
 *
 * [Related Wikipedia Article](https://en.wikipedia.org/wiki/Path_(graph_theory))
 */
class Path<Node>(
    private val nodes: List<Node>
) : List<Node> {
    /*
        /**
         * @param edges
         * @param tally
         */
        constructor(edges: List<Edge<Node>>, tally : Boolean) : this(mutableListOf<Node>()) {
            if (edges.isEmpty()) {
                return
            }

            val mutableList = nodes as MutableList

            for (edge in edges) {
                mutableList += edge.tail
            }
            mutableList += edges.last().head
        }
    */
    companion object {

        internal fun <T> compile(mapping: HashMap<T, Pair<Double, T>>, origin: T, destination: T): Path<T> {
            assert(mapping[destination] != null)

            val res = mutableListOf<T>()

            var currentNode = destination

            while (currentNode != origin) {
                res += currentNode

                currentNode = mapping[currentNode]!!.second
            }
            res += origin

            res.reverse()

            return Path(res)
        }
    }


    /**
     * A simple path is a path with no repeating nodes.
     * If a path between two nodes exists, there exists a simple path between them.
     * This method creates a simple path between the first and last nodes.
     * @return A path with the same first and last nodes, without any repeating node.
     */
    fun toSimplePath(): Path<Node> {
        val newList = mutableListOf<Node>()

        var i = 0

        while (i < nodes.size) {
            newList += nodes[i]

            var j = nodes.size - 1

            while (nodes[j] != nodes[i]) {
                j--
            }

            i = j + 1
        }

        return Path(newList)
    }

    /**
     * Sums up the weights of the edges of this path according to a given graph.
     *
     * @param graph Graph to pull the edge weights from.
     * @return The sum of the consecutive edges' weights according to the given graph.
     * @throws NoSuchElementException when one of the edges doesn't exist in the graph.
     */
    fun length(graph: Graph<Node>): Double {
        var res = 0.0

        for (i in 0..nodes.size - 2) {
            res += graph.edgeWeight(nodes[i], nodes[i + 1])
        }

        return res
    }

    /**
     * Returns the node at the specified index.
     *
     * @param index Index to read on.
     * @return The index-th node of the path.
     * @throws IndexOutOfBoundsException When trying to get a node outside the path.
     */
    fun node(index: Int): Node {
        if (index !in 0..<size) {
            throw IndexOutOfBoundsException("Index out of range : $index not in ${0..<size}")
        }

        return nodes[index]
    }

    /**
     * Returns the edge at the specified index.
     *
     * @param index Index to read on.
     * @return The index-th edge of the path.
     * @throws IndexOutOfBoundsException When trying to get an edge outside the path.
     */
    fun edge(index: Int): Edge<Node> {
        if (index !in 0..size - 2) {
            throw IndexOutOfBoundsException("Index out of range : $index not in ${0..size - 2}")
        }

        return Edge(nodes[index], nodes[index + 1])
    }

    /**
     * Returns a list of edges that, consecutively, make the same path as this instance.
     *
     * @return A list of edges equivalent to this path.
     */
    fun edges(): List<Edge<Node>> {
        val res = mutableListOf<Edge<Node>>()

        for (i in 0..size - 2) {
            res += Edge(nodes[i], nodes[i + 1])
        }

        return res
    }

    /////////////////////////////////////////// LIST METHODS ///////////////////////////////////////////

    override val size: Int
        get() = nodes.size

    override fun get(index: Int): Node = nodes[index]

    override fun isEmpty(): Boolean = nodes.isEmpty()

    override fun indexOf(element: Node): Int = nodes.indexOf(element)

    override fun containsAll(elements: Collection<Node>): Boolean = nodes.containsAll(elements)

    override fun contains(element: Node): Boolean = nodes.contains(element)

    override fun iterator(): Iterator<Node> = nodes.iterator()

    override fun listIterator(): ListIterator<Node> = nodes.listIterator()

    override fun listIterator(index: Int): ListIterator<Node> = nodes.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): List<Node> = nodes.subList(fromIndex, toIndex)

    override fun lastIndexOf(element: Node): Int = nodes.lastIndexOf(element)

    override fun toString(): String {
        return nodes.toString()
    }

    ////////////////////////////////////////// /LIST METHODS ///////////////////////////////////////////
}
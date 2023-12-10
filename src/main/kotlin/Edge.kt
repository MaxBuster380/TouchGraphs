/*
 * MIT License
 *
 * Copyright (c) 2023 MaxBuster380
 *
 * This is the "Edge.kt" file from the TouchGraphs project.
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
 * # Edge
 *
 * An edge is a relation between one node and another.
 * This implementation is directed, meaning while the `tail` node is related to the `head`, the reverse may not be true.
 *
 * @param tail "Origin" node of the relation.
 * @param head "Destination" node of the relation.
 *
 * [Related Wikipedia Article](https://en.wikipedia.org/wiki/Glossary_of_graph_theory#edge)
 */
data class Edge<Node>(
    val tail: Node,
    val head: Node
) {
    /**
     * Finds the edge's weight in a graph.
     * @param graph Graph to pull the weight from.
     * @return The edge's weight in the graph.
     * @throws NoSuchElementException when the edge doesn't exist in the graph.
     */
    fun weight(graph: Graph<Node>): Double = graph.edgeWeight(tail, head)

    override fun toString(): String {
        return "($tail, $head)"
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (other !is Edge<*>) {
            return false
        }

        return this.tail == other.tail && this.head == other.head
    }

    override fun hashCode(): Int {
        return tail.hashCode() xor head.hashCode()
    }
}
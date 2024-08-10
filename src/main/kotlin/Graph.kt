/*
 * MIT License
 *
 * Copyright (c) 2024 MaxBuster380
 *
 * This is the "Graph.kt" file from the TouchGraphs project.
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

import heaps.BinaryHeap
import heaps.Heap
import kotlin.math.min

/**
 * # Graph
 *
 * A generic graph in the graph theory sense, describing relations between objects.
 * Such objects are called `Nodes` or vertices, and the connections between them are
 * called `Edges`.
 *
 * This implementation allows for graphs of infinite or uninstanciated nodes.
 * This means that you'll often need to provide the nodes you want to work on or specify how deep an algorithm goes before stopping.
 *
 * @param Node Type of the graph's nodes.
 *
 * @see Edge
 */
abstract class Graph<Node> {
    companion object {

        /**
         * Creates a graph object from a successors function.
         *
         * @param successors Function that tallies all nodes that are successors of a given node.
         * @param areJoined Function that checks if a node is a successor of another. Optional parameter.
         * @param edgeWeight Function that gets the weight of a given edge. Optional parameter, 1.0 for all existing edges by default.
         *
         * @return a graph object with the specified definitions.
         *
         * Template :
         *
         * ```kotlin
         * val syracuseGraph = Graph.create<Int>(
         *         {
         *             if (it % 2 == 0) {
         *                 setOf( it / 2 )
         *             } else {
         *                 setOf( 3 * it + 1 )
         *             }
         *         }
         *     )
         * ```
         *
         * Expanded Template :
         *
         * ```kotlin
         * val syracuseGraph = Graph.create(
         *
         *         successorsFunction = {
         *
         *             if (it % 2 == 0) {
         *                 setOf( it / 2 )
         *             } else {
         *                 setOf( 3 * it + 1 )
         *             }
         *         },
         *
         *         areJoinedFunction = { tail : Int, head : Int ->
         *
         *             if (tail % 2 == 0) {
         *                 head == tail / 2
         *             } else {
         *                 head == 3 * tail + 1
         *             }
         *         },
         *
         *         edgeWeightFunction = { tail : Int, head : Int -> { 1.0 }
         *
         *     )
         * ```
         */
        fun <Node> create(
            successors: (Node) -> Set<Node>,
            areJoined: (Node, Node) -> Boolean = { tail: Node, head: Node ->
                successors(tail).contains(
                    head
                )
            },
            edgeWeight: (Node, Node) -> Double = { _: Node, _: Node -> 1.0 }
        ): Graph<Node> {

            return object : Graph<Node>() {
                override fun successors(tail: Node): Set<Node> = successors(tail)
                override fun areJoined(tail: Node, head: Node): Boolean = areJoined(tail, head)
                override fun edgeWeight(tail: Node, head: Node): Double = edgeWeight(tail, head)
            }
        }
    }

    /**
     * Tallies all nodes that are successors of a given node.
     * A successor N of a node O is a node where the edge (O, N) exists in the graph.
     *
     * @param tail Node to get the successors of.
     * @return A set of all successor nodes to the given node.
     */
    abstract fun successors(tail: Node): Set<Node>

    /**
     * Checks if a node is a successor of another.
     *
     * It is recommended to override this method with a constant time implementation.
     *
     * @param tail "Origin" node of the edge.
     * @param head "Destination" node of the edge.
     * @return `true` only if the edge (`tail`, `head`) exists ; `false` otherwise.
     */
    open fun areJoined(tail: Node, head: Node): Boolean {
        return successors(tail).contains(head)
    }

    /**
     * Gets the weight of a given edge. `1.0` for all existing edges by default.
     *
     * @param tail "Origin" node of the edge.
     * @param head "Destination" node of the edge.
     * @return The weight of the edge (`tail`, `head`).
     * @throws NoSuchElementException when the edge (`tail`, `head`) doesn't exist.
     */
    open fun edgeWeight(tail: Node, head: Node): Double {
        if (!areJoined(tail, head)) {
            throw NoSuchElementException("The edge ($tail, $head) doesn't exist.")
        }

        return 1.0
    }
}

/**
 * Conducts a search of nodes using the **Breadth-First Search algorithm**.
 * The Breadth-First algorithm states that the next node to be visited should be the **least recently** reached.
 *
 * [Related Wikipedia Article](https://en.wikipedia.org/wiki/Breadth-first_search)
 *
 * @param origin Node of the graph to start the search from.
 * @param maximumSearchCount Maximum number of nodes to explore. `1000` by default.
 * @return An iterator for all nodes searched from the origin, in the breadth-first order.
 * @throws IllegalArgumentException When the origin node is not in the graph.
 */
fun <Node> Graph<Node>.breadthFirstSearch(
    origin: Node,
    maximumSearchCount: Int = 1_000
): Iterator<Node> {

    return object : Iterator<Node> {
        val list = mutableListOf(origin)
        val visitedNodes = mutableSetOf<Node>()

        override fun hasNext(): Boolean {
            return list.isNotEmpty() && visitedNodes.size < maximumSearchCount
        }

        override fun next(): Node {
            val res = list.removeFirst()

            visitedNodes += res
            val nextNodes = successors(res)
            for (nextNode in nextNodes) {
                if (nextNode !in list && nextNode !in visitedNodes) {
                    list += nextNode
                }
            }
            return res
        }
    }
}

/**
 * Conducts a search of nodes using the **Depth-First Search algorithm**.
 * The Depth-First algorithm states that the next node to be visited should be the **most recently** reached.
 *
 * [Related Wikipedia Article](https://en.wikipedia.org/wiki/Depth-first_search)
 *
 * @param origin Node of the graph to start the search from.
 * @param maximumSearchCount Maximum number of nodes to explore. `1000` by default.
 * @return An iterator for all nodes searched from the origin, in the depth-first order.
 * @throws IllegalArgumentException When the origin node is not in the graph.
 */
fun <Node> Graph<Node>.depthFirstSearch(
    origin: Node,
    maximumSearchCount: Int = 1_000
): Iterator<Node> {

        return object : Iterator<Node> {
            val list = mutableListOf(origin)
            val visitedNodes = mutableSetOf<Node>()

            override fun hasNext(): Boolean {
                return list.isNotEmpty() && visitedNodes.size < maximumSearchCount
            }

            override fun next(): Node {
                val res = list.removeLast()

                visitedNodes += res
                val nextNodes = successors(res)
                for (nextNode in nextNodes) {
                    if (nextNode !in list && nextNode !in visitedNodes) {
                        list += nextNode
                    }
                }
                return res
            }
        }
    }

/**
 * Finds the distance from an `origin` node to all other possible destination nodes, along with the next node on the way to the `origin`, using **Dijkstra's algorithm**.
 *
 * [Related Wikipedia Article](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm)
 *
 * @param origin Node of the graph to find all shortest paths to.
 * @param breakNodes Set of nodes that, if reached, stops the algorithm. Empty set, meaning no stop node, by default.
 * @param maximumSearchCount Maximum number of nodes to explore. `1000` by default.
 * @return A hash map associating a node to its distance to the `origin` and the next node on the path to it. A node that isn't a key in the result hash map doesn't have a path to the `origin`.
 * @throws IllegalStateException When an edge of negative length is found.
 */
fun <Node> Graph<Node>.shortestPathMapping(
    origin: Node,
    breakNodes: Set<Node> = setOf(),
    maximumSearchCount: Int = 1_000
): HashMap<Node, Pair<Double, Node>> {

        val mapping = HashMap<Node, Pair<Double, Node>>()

        val heap: Heap<Triple<Node, Double, Node>> = object : BinaryHeap<Triple<Node, Double, Node>>() {
            override fun rightOrder(a: Triple<Node, Double, Node>, b: Triple<Node, Double, Node>): Boolean {
                return a.second < b.second
            }
        }

        heap.insert(Triple(origin, 0.0, origin))

        while (heap.isNotEmpty() && mapping.size < maximumSearchCount) {

            val currentTile = heap.pop()
            val currentNode = currentTile.first
            val currentDistance = currentTile.second
            val currentPreviousNode = currentTile.third

            if (currentNode in mapping.keys) continue
            mapping[currentNode] = Pair(currentDistance, currentPreviousNode)
            if (breakNodes.contains(currentNode)) {
                break
            }

            val nextNodes = successors(currentNode)
            for(nextNode in nextNodes) {
                val edgeWeight = edgeWeight(currentNode, nextNode)
                if (edgeWeight < 0) {
                    throw IllegalStateException("Found an edge of negative length : |(${currentNode}, ${nextNode})| = $edgeWeight")
                }
                val newDistance = currentDistance + edgeWeight

                heap.insert(Triple(nextNode, newDistance, currentNode))
            }
        }

        return mapping
    }

/**
 * Finds the **shortest path** between two nodes using **Dijkstra's algorithm**.
 *
 * [Related Wikipedia Article](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm)
 *
 * @param origin Node at the beginning of the path to find.
 * @param destination Node at the end of the path to find.
 * @param maximumSearchCount Maximum number of nodes to explore. `1000` by default.
 * @return The shortest path between the `origin` and the `destination` nodes in the graph, `null` if no path between them exists.
 * @throws IllegalStateException When an edge of negative length is found.
 */
fun <Node> Graph<Node>.shortestPath(
    origin: Node,
    destination: Node,
    maximumSearchCount: Int = 1_000
): Path<Node>? {

    val mapping = shortestPathMapping(origin, setOf(destination), maximumSearchCount)

    if (mapping[destination] == null) {
        return null
    }

    return Path.compile(mapping, origin, destination)
}

/**
 * Finds the **shortest paths** between an `origin` node and all other possible nodes using **Dijkstra's algorithm**.
 *
 * [Related Wikipedia Article](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm)
 *
 * @param origin Node at the beginning of the path to find.
 * @param maximumSearchCount Maximum number of nodes to explore. `1000` by default.
 * @return A hash map, associating a destination node to the shortest path between it and the `origin` node.
 * @throws IllegalStateException When an edge of negative length is found.
 */
fun <Node> Graph<Node>.shortestPaths(
    origin: Node,
    maximumSearchCount: Int = 1_000
): HashMap<Node, Path<Node>> {

    val mapping = shortestPathMapping(origin, maximumSearchCount = maximumSearchCount)

    val res = hashMapOf<Node, Path<Node>>()

    for (destination in mapping.keys) {
        res[destination] = Path.compile(mapping, origin, destination)
    }

    return res

}

/**
 * Finds a **path** between two nodes using the **A\* algorithm**.
 * This algorithm doesn't guarantee that the path found is the shortest ; it is however a good estimate.
 *
 * [Related Wikipedia Article](https://en.wikipedia.org/wiki/A*_search_algorithm)
 *
 * @param origin Node of the graph to start from.
 * @param destination Node of the graph to search.
 * @param heuristic The heuristic function should be an optimistic approximation of how close a node is to the destination. It must output a positive or zero value. `0` by default.
 * @param maximumSearchCount Maximum number of nodes to explore. `1000` by default.
 * @return A path between the `origin` and the `destination` if one is found ; `null` if none exist.
 * @throws IllegalStateException When an edge of negative length is found.
 */
fun <Node> Graph<Node>.findPath(
    origin: Node,
    destination: Node,
    heuristic: (Node) -> Double = { _: Node -> 0.0 },
    maximumSearchCount: Int = 1_000
): Path<Node>? {

    val mapping = HashMap<Node, Pair<Double, Node>>()
    val heap: Heap<Triple<Node, Double, Node>> = object : BinaryHeap<Triple<Node, Double, Node>>() {
        override fun rightOrder(a: Triple<Node, Double, Node>, b: Triple<Node, Double, Node>): Boolean {
            return a.second < b.second
        }
    }

    heap.insert(Triple(origin, 0.0, origin))

    var found = false

    while (heap.isNotEmpty() && mapping.size < maximumSearchCount) {

        val currentTile = heap.pop()

        val currentNode = currentTile.first
        val currentDistance = currentTile.second
        val currentPreviousNode = currentTile.third

        if (currentNode in mapping.keys) continue

        mapping[currentNode] = Pair(currentDistance, currentPreviousNode)
        if (currentNode == destination) {
            found = true
            break
        }

        val nextNodes = successors(currentNode)

        for (nextNode in nextNodes) {

            val edgeWeight = edgeWeight(currentNode, nextNode)

            assert(edgeWeight >= 0) { "Found an edge of negative length : |(${currentNode}, ${nextNode})| = $edgeWeight" }

            val newDistance = currentDistance + edgeWeight + heuristic(nextNode)

            heap.insert(Triple(nextNode, newDistance, currentNode))
        }
    }

    val res: Path<Node>? = if (found) {
        Path.compile(mapping, origin, destination)
    } else {
        null
    }

    return res
}

/**
 * Finds the **strongly connected components** of the graph using **Tarjan's algorithm**.
 * Strongly connected components are sub-graphs where there exists a path between any node to all nodes.
 * This method makes most sense on a directed graph, as if it is undirected, it only finds the connected components of the graph.
 *
 * [Related Wikipedia Article](https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm)
 *
 * @param nodes Set of nodes to work on.
 * @return A set of strongly connected components, which are sets of nodes.
 */
fun <Node> Graph<Node>.stronglyConnectedComponents(
    nodes: Set<Node>
): Set<Set<Node>> {

    val res = mutableSetOf<Set<Node>>()

    val nodeIndices = HashMap<Node, Int>()
    val nodeLowlink = HashMap<Node, Int>()

    var index = 0
    val stack = mutableListOf<Node>()

    fun strongConnect(v: Node) {
        nodeIndices[v] = index
        nodeLowlink[v] = index
        index++
        stack.add(v)

        val vSuccessors = successors(v)
        for (w in vSuccessors) {
            if (nodeIndices[w] == null) {
                strongConnect(w)
                nodeLowlink[v] = min(nodeLowlink[v]!!, nodeLowlink[w]!!)
            } else if (stack.contains(w)) {
                nodeLowlink[v] = min(nodeLowlink[v]!!, nodeIndices[w]!!)
            }
        }

        if (nodeLowlink[v] == nodeIndices[v]) {
            val newComponent = mutableSetOf<Node>()
            do {
                val w = stack.removeLast()
                newComponent += w
            } while (w != v)
            res += newComponent
        }
    }

    for (v in nodes) {
        if (nodeIndices[v] == null) {
            strongConnect(v)
        }
    }

    return res
}

/**
 * Finds a **minimum spanning tree** among the given `nodes` using **Kruskal's algorithm**.
 * The minimum spanning tree is a subset of connected edges with all given `nodes`, the minimum sum of edge weights and no loops.
 * Ideally, a graph using this method should be undirected and edge-weighted.
 *
 * [Related Wikipedia Article](https://en.wikipedia.org/wiki/Kruskal%27s_algorithm)
 *
 * @param nodes Set of nodes to work on.
 * @return A set of edges forming the
 */
fun <Node> Graph<Node>.minimumSpanningTree(
    nodes: Set<Node>
): Set<Edge<Node>> {

    val forest = mutableSetOf<UnionFind<Node>>()
    for (node in nodes) {
        forest += UnionFind(node)
    }

    val edges = mutableListOf<Edge<UnionFind<Node>>>()
    for (a in forest) {
        for (b in forest) {
            if (areJoined(a.value, b.value)) {
                edges += Edge(a, b)
            }
        }
    }

    edges.sortBy { edgeWeight(it.tail.value, it.head.value) }

    val res = mutableSetOf<Edge<Node>>()
    for (edge in edges.listIterator()) {
        if (edge.tail.find() != edge.head.find()) {
            res += Edge(edge.tail.value, edge.head.value)
            edge.tail.union(edge.head)
        }
    }

    return res
}

/**
 * In a given set of `nodes`, finds the connected components.
 * A connected component is a set of nodes that share at least one relation with another node of that set.
 *
 * [Related Wikipedia Article](https://en.wikipedia.org/wiki/Connectivity_(graph_theory)#Connected_vertices_and_graphs)
 *
 * @param nodes Set of nodes to work on.
 * @return A set of connected components, which are sets of nodes.
 */
fun <Node> Graph<Node>.connectedComponents(
    nodes: Set<Node>
): Set<Set<Node>> {

    val forestMap = hashMapOf<Node, UnionFind<Node>>()

    for (node in nodes) {
        forestMap[node] = UnionFind(node)
    }

    for (node in nodes) {
        for (next in successors(node)) {
            forestMap[node]?.union(forestMap[next] ?: continue) ?: continue
        }
    }

    val resMap = hashMapOf<Node, MutableSet<Node>>()
    for (node in nodes) {
        val father = forestMap[node]!!.father.value
        if (resMap[father] != null) {
            resMap[father]!! += node
        } else {
            resMap[father] = mutableSetOf(node)
        }
    }

    return resMap.values.toSet()
}

/**
 * In a given set of `nodes`, finds its maximal cliques using the **pivot Bron-Kerbosch algorithm.**
 * A clique is a complete subset of a graph, where all nodes are connected to one another.
 * A maximal clique is a clique to which no other node can be added.
 *
 * [Related Wikipedia Article](https://en.wikipedia.org/wiki/Clique_(graph_theory))
 *
 * @param nodes Set of nodes to work on.
 *
 * @return A set of maximal cliques among the input nodes, where a clique is a set of nodes.
 */
fun <Node> Graph<Node>.maximalCliques(
    nodes: Set<Node>
): Set<Set<Node>> {

    fun pivot(possibilities: Set<Node>): Node {
        return possibilities.maxBy { successors(it).size }
    }

    fun bronKerbosch2(r: Set<Node>, pInput: Set<Node>, xInput: Set<Node>): Set<Set<Node>> {

        if (pInput.isEmpty() && xInput.isEmpty())
            return setOf(r)

        val u = pivot(pInput union xInput)

        val p = pInput.toMutableSet()
        val x = xInput.toMutableSet()

        val res = mutableSetOf<Set<Node>>()

        val neighborsOfU = successors(u)
        val iteratedNodes = p.toMutableSet()
        iteratedNodes.removeAll(neighborsOfU)
        for (v in iteratedNodes) {

            val neighborsOfV = successors(v)
            val subResult = bronKerbosch2(r union setOf(v), p intersect neighborsOfV, x intersect neighborsOfV)
            res.addAll(subResult)

            p -= v
            x += v
        }

        return res
    }

    return bronKerbosch2(setOf(), nodes, setOf())
}

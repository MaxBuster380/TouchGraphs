/*
 * MIT License
 *
 * Copyright (c) 2023 MaxBuster380
 *
 * This is the "TestGraphs.kt" file from the TouchGraphs project.
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

object TestGraphs {

    /**
     * Empty Graph with no edges.
     */
    val empty = object : Graph<Int>() {
        override fun successors(tail: Int): Set<Int> {
            return setOf()
        }
    }

    /**
     * Graph on [this page](https://commons.wikimedia.org/wiki/File:Dijkstra_Animation.gif).
     */
    val dijkstraWikipedia = object : Graph<Int>() {
        val edges = setOf(
            Triple(1, 2, 7.0),
            Triple(1, 3, 9.0),
            Triple(1, 6, 14.0),

            Triple(2, 1, 7.0),
            Triple(2, 3, 10.0),
            Triple(2, 4, 15.0),

            Triple(3, 1, 9.0),
            Triple(3, 2, 10.0),
            Triple(3, 4, 11.0),
            Triple(3, 6, 2.0),

            Triple(4, 2, 15.0),
            Triple(4, 3, 11.0),
            Triple(4, 5, 6.0),

            Triple(5, 4, 6.0),
            Triple(5, 6, 9.0),

            Triple(6, 1, 14.0),
            Triple(6, 3, 2.0),
            Triple(6, 5, 9.0),
        )

        override fun successors(tail: Int): Set<Int> {
            return edges.filter { it.first == tail }.map { it.second }.toSet()
        }

        override fun areJoined(tail: Int, head: Int): Boolean {
            return edges.find { it.first == tail && it.second == head } != null
        }

        override fun edgeWeight(tail: Int, head: Int): Double {
            return edges.find { it.first == tail && it.second == head }!!.third.toDouble()
        }
    }

    /**
     * Graph given as example for the Tarjan algorithm on Wikipedia
     *
     * [Link here](https://commons.wikimedia.org/wiki/File:Tarjan%27s_Algorithm_Animation.gif)
     */
    val tarjanWikipedia = object : Graph<Char>() {
        val successors = hashMapOf<Char, Set<Char>>()

        init {
            successors['A'] = setOf('E')
            successors['B'] = setOf('A')
            successors['C'] = setOf('B', 'D')
            successors['D'] = setOf('C')
            successors['E'] = setOf('B')
            successors['F'] = setOf('B', 'E', 'G')
            successors['G'] = setOf('C', 'F')
            successors['H'] = setOf('D', 'G', 'H')
        }

        override fun successors(tail: Char): Set<Char> {
            return successors[tail]
                ?: throw NoSuchElementException("'$tail' is not a node of the tarjanWikipedia graph")
        }

    }

    /**
     * Graph given as example for the Tarjan algorithm on Wikipedia, but with an (E, F) edge added.
     *
     * [Link here](https://commons.wikimedia.org/wiki/File:Tarjan%27s_Algorithm_Animation.gif)
     */
    val modifiedTarjanWikipedia = object : Graph<Char>() {
        val successors = hashMapOf<Char, Set<Char>>()

        init {
            successors['A'] = setOf('E')
            successors['B'] = setOf('A')
            successors['C'] = setOf('B', 'D')
            successors['D'] = setOf('C')
            successors['E'] = setOf('B', 'F')
            successors['F'] = setOf('B', 'E', 'G')
            successors['G'] = setOf('C', 'F')
            successors['H'] = setOf('D', 'G', 'H')
        }

        override fun successors(tail: Char): Set<Char> {
            return successors[tail]
                ?: throw NoSuchElementException("'$tail' is not a node of the modifiedTarjanWikipedia graph")
        }

    }

    val components = object : Graph<Char>() {
        val edges = setOf(
            Pair('B', 'A'),
            Pair('C', 'A'),
            Pair('C', 'B'),

            Pair('G', 'E'),
            Pair('E', 'F'),
            Pair('F', 'G'),

            Pair('H', 'I'),
        )

        override fun successors(tail: Char): Set<Char> {
            return edges.filter { it.first == tail }.map { it.second }.toSet()
        }

        override fun areJoined(tail: Char, head: Char): Boolean {
            return edges.find { it.first == tail && it.second == head } != null
        }
    }

    /**
     * Graph given for the example of Kruskal's algorithm in [this article](https://www.geeksforgeeks.org/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/)
     */
    val kruskal = object : Graph<Int>() {
        val edges = setOf(
            Triple(0, 1, 4.0),
            Triple(0, 7, 8.0),

            Triple(1, 0, 4.0),
            Triple(1, 2, 8.0),
            Triple(1, 7, 11.0),

            Triple(2, 1, 8.0),
            Triple(2, 3, 7.0),
            Triple(2, 5, 4.0),
            Triple(2, 8, 2.0),

            Triple(3, 2, 7.0),
            Triple(3, 4, 9.0),
            Triple(3, 5, 14.0),

            Triple(4, 3, 9.0),
            Triple(4, 5, 10.0),

            Triple(5, 2, 4.0),
            Triple(5, 3, 14.0),
            Triple(5, 4, 10.0),
            Triple(5, 6, 2.0),

            Triple(6, 5, 2.0),
            Triple(6, 7, 1.0),
            Triple(6, 8, 6.0),

            Triple(7, 0, 8.0),
            Triple(7, 1, 11.0),
            Triple(7, 6, 1.0),
            Triple(7, 8, 7.0),

            Triple(8, 2, 2.0),
            Triple(8, 6, 6.0),
            Triple(8, 7, 7.0)

        )

        override fun successors(tail: Int): Set<Int> {
            return edges.filter { it.first == tail }.map { it.second }.toSet()
        }

        override fun areJoined(tail: Int, head: Int): Boolean {
            return edges.find { it.first == tail && it.second == head } != null
        }

        override fun edgeWeight(tail: Int, head: Int): Double {
            return edges.find { it.first == tail && it.second == head }!!.third
        }

    }
}
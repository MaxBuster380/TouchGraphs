/*
 * MIT License
 *
 * Copyright (c) 2023 MaxBuster380
 *
 * This is the "MinimumSpanningTreeTest.kt" file from the TouchGraphs project.
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

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MinimumSpanningTreeTest {
    @Test
    fun exampleOneTest() {
        val graph = TestGraphs.kruskal

        val nodes = setOf(0, 1, 2, 3, 4, 5, 6, 7, 8)

        val result = graph.minimumSpanningTree(nodes)

        // |MST| = |V| - 1
        assertEquals(nodes.size - 1, result.size)

        assertTrue { result.contains(Edge(0, 1)) || result.contains(Edge(1, 0)) }
        assertTrue { result.contains(Edge(0, 7)) || result.contains(Edge(7, 0)) }
        assertTrue { result.contains(Edge(7, 6)) || result.contains(Edge(6, 7)) }
        assertTrue { result.contains(Edge(6, 5)) || result.contains(Edge(5, 6)) }
        assertTrue { result.contains(Edge(2, 5)) || result.contains(Edge(5, 2)) }
        assertTrue { result.contains(Edge(2, 8)) || result.contains(Edge(8, 2)) }
        assertTrue { result.contains(Edge(2, 3)) || result.contains(Edge(3, 2)) }
        assertTrue { result.contains(Edge(4, 3)) || result.contains(Edge(3, 4)) }
    }
}
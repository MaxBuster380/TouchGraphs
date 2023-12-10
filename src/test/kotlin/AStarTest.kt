/*
 * MIT License
 *
 * Copyright (c) 2023 MaxBuster380
 *
 * This is the "AStarTest.kt" file from the TouchGraphs project.
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

import kotlin.test.*

class AStarTest {
    @Test
    fun emptyGraph() {

        val graph = TestGraphs.empty

        val origin = 8
        val destination = 13
        val heuristic = { _: Int -> 0.0 }

        val result = graph.findPath(origin, destination, heuristic, 10)

        assertTrue { result == null }
    }

    /**
     * Attempts to solve the Wikipedia Dijkstra example graph.
     * This tests if A* does act like Dijkstra if the heuristic is always 0.
     */
    @Test
    fun standard1() {

        val graph = TestGraphs.dijkstraWikipedia

        val origin = 1
        val destination = 5
        val heuristic = { _: Int -> 0.0 }

        val result = graph.findPath(origin, destination, heuristic, 10)

        assertNotNull(result)

        assertContentEquals(
            listOf(origin, 3, 6, destination),
            result
        )

        assertEquals(20.0, result.length(graph))
    }
}
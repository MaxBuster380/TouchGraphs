/*
 * MIT License
 *
 * Copyright (c) 2023 MaxBuster380
 *
 * This is the "DijkstraTest.kt" file from the TouchGraphs project.
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
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DijkstraTest {
    @Test
    fun emptyGraph() {

        val graph = TestGraphs.empty

        val firstNode = 8

        val result = graph.shortestPathMapping(firstNode)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(0.0, result[firstNode]!!.first)
        assertEquals(firstNode, result[firstNode]!!.second)
    }

    /**
     * Attempts to solve the Wikipedia Dijkstra example graph.
     */
    @Test
    fun standardMapping1() {

        val graph = TestGraphs.dijkstraWikipedia

        val result = graph.shortestPathMapping(1)

        assertEquals(6, result.size)

        assertNotNull(result[1])
        assertEquals(1, result[1]!!.second)
        assertEquals(0.0, result[1]!!.first)

        assertNotNull(result[2])
        assertEquals(1, result[2]!!.second)
        assertEquals(7.0, result[2]!!.first)

        assertNotNull(result[3])
        assertEquals(1, result[3]!!.second)
        assertEquals(9.0, result[3]!!.first)

        assertNotNull(result[4])
        assertEquals(3, result[4]!!.second)
        assertEquals(20.0, result[4]!!.first)

        assertNotNull(result[5])
        assertEquals(6, result[5]!!.second)
        assertEquals(20.0, result[5]!!.first)

        assertNotNull(result[6])
        assertEquals(3, result[6]!!.second)
        assertEquals(11.0, result[6]!!.first)
    }

    @Test
    fun wikipediaShortestPath() {

        val graph = TestGraphs.dijkstraWikipedia

        val result = graph.shortestPath(1, 5)

        assertNotNull(result)

        assertContentEquals(
            listOf(1, 3, 6, 5),
            result
        )

    }

    @Test
    fun washingtonShortestPaths() {

        val graph = TestGraphs.dijkstraWikipedia

        val result = graph.shortestPaths(1)

        assertNotNull(result)

        assertNotNull(result[1])
        assertContentEquals(
            listOf(1),
            result[1]
        )

        assertNotNull(result[2])
        assertContentEquals(
            listOf(1, 2),
            result[2]
        )

        assertNotNull(result[3])
        assertContentEquals(
            listOf(1, 3),
            result[3]
        )

        assertNotNull(result[4])
        assertContentEquals(
            listOf(1, 3, 4),
            result[4]
        )

        assertNotNull(result[5])
        assertContentEquals(
            listOf(1, 3, 6, 5),
            result[5]
        )

        assertNotNull(result[6])
        assertContentEquals(
            listOf(1, 3, 6),
            result[6]
        )

    }
}
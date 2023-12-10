/*
 * MIT License
 *
 * Copyright (c) 2023 MaxBuster380
 *
 * This is the "TarjanTest.kt" file from the TouchGraphs project.
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

class TarjanTest {
    @Test
    fun tarjanWikipedia() {
        val graph = TestGraphs.tarjanWikipedia
        val nodes = setOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H')

        val result = graph.stronglyConnectedComponents(nodes)

        val expectedResult = mutableSetOf(
            setOf('A', 'B', 'E'),
            setOf('C', 'D'),
            setOf('F', 'G'),
            setOf('H')
        )

        assertEquals(expectedResult.size, result.size)

        // Check that every set in the expected result is in the actual result,
        // by removing matching sets and testing if the end collection is empty.
        for (setResult in result) {
            for (expectedResultSet in expectedResult) {
                if (setsMatch(expectedResultSet, setResult)) {
                    expectedResult.remove(expectedResultSet)
                    break
                }
            }
        }

        assertTrue { expectedResult.isEmpty() }
    }

    @Test
    fun modifiedTarjanWikipedia() {
        val graph = TestGraphs.modifiedTarjanWikipedia
        val nodes = setOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H')

        val result = graph.stronglyConnectedComponents(nodes)

        val expectedResult = mutableSetOf(
            setOf('A', 'B', 'C', 'D', 'E', 'F', 'G'),
            setOf('H')
        )

        assertEquals(expectedResult.size, result.size)

        // Check that every set in the expected result is in the actual result,
        // by removing matching sets and testing if the end collection is empty.
        for (setResult in result) {
            for (expectedResultSet in expectedResult) {
                if (setsMatch(expectedResultSet, setResult)) {
                    expectedResult.remove(expectedResultSet)
                    break
                }
            }
        }

        assertTrue { expectedResult.isEmpty() }
    }

    private fun <T> setsMatch(a: Set<T>, b: Set<T>): Boolean {
        return a.containsAll(b) && b.containsAll(a)
    }
}
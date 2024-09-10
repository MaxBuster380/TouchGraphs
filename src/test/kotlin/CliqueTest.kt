/*
 * MIT License
 *
 * Copyright (c) 2024 MaxBuster380
 *
 * This is the "CliqueTest.kt" file from the TouchGraphs project.
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
import kotlin.test.assertContains

/*
 * MIT License
 *
 * Copyright (c) 2024 MaxBuster380
 *
 * This is the "CliqueTest.kt" file from the TouchGraphs project.
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

class CliqueTest {

    @Test
    fun test1() {

        /*

        6 - 4 - 5 -- 1
            |   |   /
            3 - 2 -

         */

        val successors = hashMapOf<Int, Set<Int>>()
        successors[1] = setOf(2, 5)
        successors[2] = setOf(1, 3, 5)
        successors[3] = setOf(2, 4)
        successors[4] = setOf(3, 5, 6)
        successors[5] = setOf(1, 2, 4)
        successors[6] = setOf(4)

        val graph = graphOf<Int> {
            successors[it]!!
        }

        val cliques = graph.maximalCliques(setOf(1, 2, 3, 4, 5, 6))

        assertContains(cliques, setOf(4, 6))
        assertContains(cliques, setOf(3, 4))
        assertContains(cliques, setOf(4, 5))
        assertContains(cliques, setOf(2, 3))
        assertContains(cliques, setOf(1, 2, 5))

        println(cliques)
    }
}
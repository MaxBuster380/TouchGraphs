/*
 * MIT License
 *
 * Copyright (c) 2023 MaxBuster380
 *
 * This is the "UnionFind.kt" file from the TouchGraphs project.
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
 * # UnionFind
 *
 * Disjoint-set data structure, implemented with all optimisations.
 *
 * [Related Wikipedia Article](https://en.wikipedia.org/wiki/Disjoint-set_data_structure)
 */
internal class UnionFind<T>(
    val value: T
) {
    private var rank = 0
    private var realFather = this

    val father: UnionFind<T> get() = realFather

    fun union(other: UnionFind<T>) {
        val u = this.find()
        val v = other.find()

        if (u.rank > v.rank) {
            v.realFather = u
        } else {
            u.realFather = v
        }

        if (u.rank == v.rank) {
            v.rank += 1
        }
    }

    fun find(): UnionFind<T> {
        if (this != this.realFather) {
            this.realFather = this.realFather.find()
        }
        return this.realFather
    }
}
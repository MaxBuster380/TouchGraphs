/*
 * MIT License
 *
 * Copyright (c) 2023 MaxBuster380
 *
 * This is the "BinaryHeap.kt" file from the TouchGraphs project.
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

package heaps

/**
 * # Binary Heap
 *
 * Heap data structure, allowing for quick access to a minimum or maximum value in a list.
 * The Binary Heap implementation uses a binary tree structure.
 *
 * [Related Wikipedia Article](https://en.wikipedia.org/wiki/Binary_heap)
 */
abstract class BinaryHeap<T> : Heap<T> {
    private val list = mutableListOf<T>()

    override val size: Int
        get() = list.size

    override fun peek(): T {
        return list.first()
    }

    override fun pop(): T {
        val res = peek()
        removePeek()
        return res
    }

    override fun removePeek() {
        list[0] = list.last()
        list.removeLast()
        heapifyDown()
    }

    override fun replace(element: T): T {
        val res = peek()
        list[0] = element
        heapifyDown()
        return res
    }

    override fun insert(element: T) {
        list += element
        heapifyUp()
    }

    override fun contains(element: T): Boolean = list.contains(element)
    override fun containsAll(elements: Collection<T>): Boolean = list.containsAll(elements)
    override fun iterator(): Iterator<T> = list.iterator()
    override fun isEmpty(): Boolean = size == 0
    override fun toString(): String = list.toString()

    private fun rightOrderIndex(a: Int, b: Int): Boolean = rightOrder(list[a], list[b])

    private fun heapifyDownIndex(index: Int): Int? {
        var targetIndex = index

        if (left(targetIndex) < list.size && !rightOrderIndex(targetIndex, left(targetIndex))) {
            targetIndex = left(targetIndex)
        }
        if (right(targetIndex) < list.size && !rightOrderIndex(targetIndex, right(targetIndex))) {
            targetIndex = right(targetIndex)
        }

        if (targetIndex != index) {
            swap(targetIndex, index)
            return targetIndex
        } else {
            return null
        }
    }

    private fun heapifyDown() {
        var index: Int? = 0

        while (index != null) {
            index = heapifyDownIndex(index)
        }
    }

    private fun heapifyUp() {
        var index: Int? = list.lastIndex

        while (index != null && index > 0) {
            if (!rightOrderIndex(father(index), index)) {
                swap(index, father(index))

                index = father(index)
            } else {
                index = null
            }
        }

    }

    private fun left(index: Int): Int = 2 * index
    private fun right(index: Int): Int = 2 * index + 1

    private fun father(index: Int): Int =
        if (index % 2 == 0) {
            index / 2
        } else {
            (index - 1) / 2
        }

    private fun swap(a: Int, b: Int) {
        val temp = list[a]
        list[a] = list[b]
        list[b] = temp
    }

}
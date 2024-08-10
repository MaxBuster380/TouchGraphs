/*
 * MIT License
 *
 * Copyright (c) 2024 MaxBuster380
 *
 * This is the "Heap.kt" file from the TouchGraphs project.
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
 * # Heap
 *
 * Heap data structure, allowing for quick access to a minimum or maximum value in a list.
 *
 * [Related Wikipedia Article](https://en.wikipedia.org/wiki/Heap_(data_structure))
 */
internal sealed interface Heap<T> : Collection<T> {

    /**
     * Compares two elements of the heap and determines
     *
     * @return `true` only if the sequence (`a`, `b`) is correctly ordered.
     */
    fun rightOrder(a: T, b: T): Boolean

    /**
     * Gets the current top element of the heap.
     *
     * @return The top element of the heap, without removing it.
     */
    fun peek(): T

    /**
     * Adds a new element to the heap.
     *
     * @param element Element to add.
     */
    fun insert(element: T)

    /**
     * Gets the current top element of the heap, then removes it.
     *
     * @return The top element of the heap, before removing it.
     */
    fun pop(): T


    /**
     * Removes the top element of the heap.
     */
    fun removePeek()

    /**
     * Pops the top element and inserts a new element.
     *
     * @return New element to add to the heap.
     * @return The top element of the heap before popping it and inserting the new element.
     *
     * @see pop
     */
    fun replace(element: T): T
}
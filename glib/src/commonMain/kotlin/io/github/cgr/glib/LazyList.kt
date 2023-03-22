package io.github.cgr.glib

import io.github.cgr.glib.impls.asString

class LazyList(private val generator: Iterator<Any?>) : List<Any?> {

    constructor(vararg elements: Any?) : this(elements.iterator())

    constructor(generator: Sequence<Any?>) : this(generator.iterator())

    constructor(generator: Iterable<Any?>) : this(generator.iterator())

    companion object {
        fun repeat(generator: () -> Any?): LazyList = LazyList(object : Iterator<Any?> {
            override fun hasNext(): Boolean = true
            override fun next(): Any? = generator()
        })
    }

    override val size: Int
        get() {
            for (e in generator) {
                backing.add(e)
            }
            return backing.size
        }

    private val backing = mutableListOf<Any?>()

    override fun get(index: Int): Any? {
        if (!hasIndex(index)) {
            throw IndexOutOfBoundsException("Index: $index, size: ${backing.size}")
        }
        return backing[index]
    }

    override fun indexOf(element: Any?): Int {
        for (i in this.indices) {
            if (get(i) == element) return i
        }
        return -1
    }

    override fun isEmpty(): Boolean {
        return !generator.hasNext() && backing.isEmpty()
    }

    override fun contains(element: Any?): Boolean {
        for (e in this) {
            if (e == element) return true
        }
        return false
    }

    override fun containsAll(elements: Collection<Any?>): Boolean = elements.all(::contains)

    override fun iterator(): Iterator<Any?> = listIterator()

    override fun lastIndexOf(element: Any?): Int {
        for (i in size - 1 downTo 0) {
            if (get(i) == element) return i
        }
        return -1
    }

    override fun listIterator(): ListIterator<Any?> = listIterator(0)

    override fun listIterator(index: Int): ListIterator<Any?> = object : ListIterator<Any?> {

        private var currentIndex = index

        override fun hasNext(): Boolean = hasIndex(currentIndex)

        override fun hasPrevious(): Boolean = hasIndex(currentIndex - 1)

        override fun next(): Any? {
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            // The backing list is guaranteed to have the element at the current index because of the call to hasNext(),
            // which calls hasIndex() which adds the element to the backing list if it is not already there.
            return backing[currentIndex++]
        }

        override fun nextIndex(): Int = currentIndex

        override fun previous(): Any? {
            if (!hasPrevious()) {
                throw NoSuchElementException()
            }
            // The backing list is guaranteed to have the element at the current index because we are going backwards.
            return backing[--currentIndex]
        }

        override fun previousIndex(): Int = currentIndex - 1
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<Any?> = LazyList(object : Iterator<Any?> {

        private var currentIndex = fromIndex

        override fun hasNext(): Boolean = hasIndex(currentIndex) && currentIndex < toIndex

        override fun next(): Any? {
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            // The backing list is guaranteed to have the element; see above.
            return backing[currentIndex++]
        }
    })

    private fun hasIndex(index: Int): Boolean {
        while (backing.size <= index) {
            if (!generator.hasNext()) {
                return false
            }
            backing.add(generator.next())
        }
        return true
    }

    // Transformations

    fun map(transform: (Any?) -> Any?): LazyList = LazyList(object : Iterator<Any?> {

        private val iterator = this@LazyList.iterator()

        override fun hasNext(): Boolean = iterator.hasNext()

        override fun next(): Any? = transform(iterator.next())
    })

    fun filter(predicate: (Any?) -> Boolean): LazyList = LazyList(object : Iterator<Any?> {

        private val iterator = this@LazyList.iterator()

        private var next: Any? = null

        override fun hasNext(): Boolean {
            while (next == null && iterator.hasNext()) {
                val element = iterator.next()
                if (predicate(element)) {
                    next = element
                }
            }
            return next != null
        }

        override fun next(): Any? {
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            val result = next
            next = null
            return result
        }
    })

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is List<*>) return false
        for (i in this.indices) {
            if (this[i] != other[i]) return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = 1
        for (element in this) {
            result = 31 * result + (element?.hashCode() ?: 0)
        }
        return result
    }

    override fun toString(): String {
        return joinToString(prefix = "[", postfix = "]", transform = ::asString)
    }
}

fun Iterable<Any?>.lazy(): LazyList = LazyList(this)

fun Sequence<Any?>.lazy(): LazyList = LazyList(this)

fun Iterator<Any?>.lazy(): LazyList = LazyList(this)
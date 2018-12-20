package com.tomlonghurst.edittextvalidator.model

class ListenableArrayList<T>(private val onChange: () -> Unit) : AbstractMutableList<T>() {

    private val delegate = ArrayList<T>()

    override fun add(index: Int, element: T) {
        delegate.add(index, element)
        onChange()
    }

    override fun removeAt(index: Int): T {
        return delegate.removeAt(index).apply {
            onChange()
        }
    }

    override fun set(index: Int, element: T): T {
        return delegate.set(index, element).apply {
            onChange()
        }
    }

    override val size: Int
        get() = delegate.size

    override fun get(index: Int): T {
        return delegate[index]
    }

    private fun onChange() {
        onChange.invoke()
    }
}
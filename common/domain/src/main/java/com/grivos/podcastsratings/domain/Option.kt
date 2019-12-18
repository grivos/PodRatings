package com.grivos.podcastsratings.domain

class Option<out T>(private val optional: T?) {

    val isEmpty: Boolean
        get() = this.optional == null

    fun get(): T {
        if (optional == null) {
            throw NoSuchElementException("No value present")
        }
        return optional
    }

    companion object {

        fun <T> empty(): Option<T> {
            return Option(null)
        }
    }
}

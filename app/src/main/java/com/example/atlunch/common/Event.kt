package com.example.atlunch.common

import java.util.concurrent.atomic.AtomicBoolean

open class Event<out T>(
    private val content: T
) {
    private val hasBeenHandled = AtomicBoolean(false)

    fun getContentIfNotHandled(handleContent: (T) -> Unit) {
        if (!hasBeenHandled.get()) {
            hasBeenHandled.set(true)
            handleContent(content)
        }
    }

    fun peekContent() = content
}
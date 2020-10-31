package com.minosai.typingdnahack.utils.events

data class Event(val type: EventType, val data: Any? = null)

interface EventType {
    val key: String?
        get() = this::class.java.canonicalName
}

object NewTweetEvent : EventType
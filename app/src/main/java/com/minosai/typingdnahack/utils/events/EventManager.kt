package com.minosai.typingdnahack.utils.events

import androidx.lifecycle.Observer
import java.util.concurrent.CopyOnWriteArrayList

class EventManager {

    private val observables: HashMap<String, CopyOnWriteArrayList<Observer<Event>>> = HashMap()

    fun addObserver(type: EventType, observer: Observer<Event>) {
        checkIfNull(type)

        observables[type.key]?.apply {
            if (!contains(observer)) {
                add(observer)
            }
        }
    }

    fun removeObserver(type: EventType, observer: Observer<Event>) {
        observables[type.key]?.remove(observer)
    }

    fun sendEvent(Event: Event) {
        observables[Event.type.key]?.apply {
            forEach { it.onChanged(Event) }
        }
    }

    private fun checkIfNull(type: EventType) {
        if (observables[type.key] == null && type.key != null) {
            observables[type.key!!] = CopyOnWriteArrayList()
        }
    }
}

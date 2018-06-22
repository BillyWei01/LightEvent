package com.horizon.event

import java.lang.ref.WeakReference

class WeakObserver(target: Observer) : Observer {
    private val reference: WeakReference<Observer> = WeakReference(target)
    private val events: IntArray = target.listEvents()

    override fun onEvent(event: Int, vararg args: Any?) {
        reference.get()?.onEvent(event, *args)
    }

    override fun listEvents(): IntArray {
        return events
    }
}
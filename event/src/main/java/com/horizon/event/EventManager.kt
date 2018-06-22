package com.horizon.event

import android.os.Handler
import android.os.Looper
import android.util.SparseArray
import java.util.*

object EventManager {
    private val HANDLER = Handler(Looper.getMainLooper())
    private val OBSERVER_ARRAY = SparseArray<LinkedList<Observer>>(16)

    @Synchronized
    fun register(observer: Observer?) {
        observer?.listEvents()?.forEach { event ->
            var observerList = OBSERVER_ARRAY.get(event)
            if (observerList == null) {
                observerList = LinkedList()
                OBSERVER_ARRAY.put(event, observerList)
            }
            if (observer !in observerList) {
                observerList.add(observer)
            }
        }
    }

    @Synchronized
    fun unregister(observer: Observer?) {
        observer?.listEvents()?.forEach { event ->
            OBSERVER_ARRAY.get(event)?.removeLastOccurrence(observer)
        }
    }

    @Synchronized
    fun notify(event: Int, vararg args: Any?) {
        OBSERVER_ARRAY.get(event)?.forEach { observer ->
            HANDLER.post { observer.onEvent(event, *args) }
        }
    }
}

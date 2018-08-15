/*
 * Copyright (C) 2018 Horizon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.horizon.event

import android.os.Handler
import android.os.Looper
import android.util.SparseArray
import java.util.*

object EventManager {
    private val handler = Handler(Looper.getMainLooper())
    private val observers = SparseArray<LinkedList<Observer>>(16)

    @JvmStatic
    @Synchronized
    fun register(observer: Observer?) {
        observer?.listEvents()?.forEach { event ->
            var list = observers.get(event)
            if (list == null) {
                list = LinkedList()
                observers.put(event, list)
            }
            if (observer !in list) {
                list.add(observer)
            }
        }
    }

    @JvmStatic
    @Synchronized
    fun unregister(observer: Observer?) {
        observer?.listEvents()?.forEach { event ->
            observers.get(event)?.removeLastOccurrence(observer)
        }
    }

    @JvmStatic
    @Synchronized
    fun notify(event: Int, vararg args: Any?) {
        observers.get(event)?.forEach { observer ->
            handler.post { observer.onEvent(event, *args) }
        }
    }
}

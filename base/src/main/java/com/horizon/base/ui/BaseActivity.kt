package com.horizon.base.ui


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.horizon.event.EventManager
import com.horizon.event.Observer
import com.horizon.event.WeakObserver

abstract class BaseActivity : AppCompatActivity(), Observer {
    val  w  : WeakObserver
        get() = WeakObserver(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventManager.register(w)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventManager.unregister(w)
    }

    override fun onEvent(event: Int, vararg args: Any?) {
    }

    override fun listEvents(): IntArray {
        return IntArray(0)
    }

    fun startActivity(activityClazz: Class<*>) {
        startActivity(Intent(this, activityClazz))
    }
}

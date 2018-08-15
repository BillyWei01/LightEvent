package com.horizon.base.ui


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.horizon.event.EventManager
import com.horizon.event.Observer

abstract class BaseActivity : AppCompatActivity(), Observer {
/*    private val weakObserver: WeakObserver
        get() = WeakObserver(this)*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventManager.register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventManager.unregister(this)
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

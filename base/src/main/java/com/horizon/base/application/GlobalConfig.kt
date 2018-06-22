

package com.horizon.base.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

@SuppressLint("StaticFieldLeak")
object GlobalConfig {
    var appContext: Context? = null
        private set

    internal fun setAppContext(context: Application) {
        appContext = context
    }


}

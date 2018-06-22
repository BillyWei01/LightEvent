package com.horizon.base.util

import android.support.annotation.MainThread
import android.widget.Toast
import com.horizon.base.application.GlobalConfig

@MainThread
fun shortTips(message: CharSequence?) {
    if(message != null && message.isNotEmpty()){
        Toast.makeText(GlobalConfig.appContext, message, Toast.LENGTH_SHORT).show()
    }
}

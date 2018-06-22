package com.horizon.base.util

import com.horizon.base.application.GlobalConfig

fun getStr(resId: Int): String {
   return if(resId <= 0) "" else GlobalConfig.appContext!!.getString(resId)
}

fun getStr(resId: Int, vararg formatArgs: Any?): String {
    return if(resId <= 0) "" else GlobalConfig.appContext!!.getString(resId, *formatArgs)
}

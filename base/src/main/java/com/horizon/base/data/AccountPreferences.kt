package com.horizon.base.data

import android.content.Context
import com.horizon.base.application.GlobalConfig

object AccountPreferences {
    private const val ACCOUNT_PREFERENCES = "account_preferences"

    private val sSp = GlobalConfig.appContext!!.getSharedPreferences(ACCOUNT_PREFERENCES, Context.MODE_PRIVATE)
    private val sEditor = sSp.edit()

    const val ACCOUNT = "account"

    fun putString(key: String, value: String) {
        sEditor.putString(key, value)
        sEditor.commit()
    }

    fun getString(key: String): String {
        return sSp.getString(key, "")
    }

    fun remove(key: String) {
        sEditor.remove(key)
        sEditor.commit()
    }
}

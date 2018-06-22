package com.horizon.eventdemo.helper

import android.text.TextUtils
import android.util.Log
import com.horizon.base.data.AccountPreferences
import com.horizon.base.event.Events
import com.horizon.event.EventManager

object AccountManager {
    private val TAG = "AccountManager"

    val isLogin: Boolean
        get() = !TextUtils.isEmpty(AccountPreferences.getString(AccountPreferences.ACCOUNT))

    @Throws(Exception::class)
    fun login(account: String, password: String) {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            throw IllegalArgumentException("帐号或密码为空")
        }

        try {
            Thread.sleep(1000L)
            AccountPreferences.putString(AccountPreferences.ACCOUNT, account)
            EventManager.notify(Events.LOGIN, account)
        } catch (e: InterruptedException) {
            throw Exception(e)
        }
    }

    fun logout() {
        try {
            Thread.sleep(1000L)
            AccountPreferences.remove(AccountPreferences.ACCOUNT)
            EventManager.notify(Events.LOGOUT)
        } catch (e: InterruptedException) {
            Log.e(TAG, e.message, e)
        }

    }

}

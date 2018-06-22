package com.horizon.eventdemo

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.horizon.base.data.AccountPreferences
import com.horizon.base.event.Events
import com.horizon.base.ui.BaseActivity
import com.horizon.base.ui.DialogTask
import com.horizon.base.util.getStr
import com.horizon.base.util.shortTips
import com.horizon.eventdemo.helper.AccountManager

class MainActivity : BaseActivity() {
    private var mLoginStateTv: TextView? = null
    private var mSwitchAccountBtn: Button? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mLoginStateTv = findViewById(R.id.login_state_tv)
        mSwitchAccountBtn = findViewById(R.id.switch_account_btn)
        mSwitchAccountBtn!!.setOnClickListener {
            if (!AccountManager.isLogin) {
                startActivity(LoginActivity::class.java)
            } else {
                LogoutTask(this@MainActivity).execute()
            }
        }

        setViews(AccountPreferences.getString(AccountPreferences.ACCOUNT))
    }

    @SuppressLint("StringFormatInvalid")
    private fun setViews(account: String?) {
        if (TextUtils.isEmpty(account)) {
            mLoginStateTv!!.text = getStr(R.string.not_logged_in)
            mSwitchAccountBtn!!.text = getStr(R.string.login)
        } else {
            mLoginStateTv!!.text = getStr(R.string.hello, account)
            mSwitchAccountBtn!!.text = getStr(R.string.logout)
        }
    }

    private class LogoutTask internal constructor(activity: Activity) : DialogTask<String, Void, String>(activity) {
        init {
            mMessage = getStr(R.string.logging_off)
        }

        override fun doInBackground(vararg params: String): String? {
            try {
                AccountManager.logout()
            } catch (e: Exception) {
                Log.e("MainActivity", e.message, e)
                val msg = e.message
                return if (msg.isNullOrEmpty()) getStr(R.string.logout_failed) else msg
            }
            return null
        }

        override fun onResult(result: String?) {
            shortTips(if (result.isNullOrEmpty()) getStr(R.string.logout_success) else result)
        }
    }

    override fun listEvents(): IntArray {
        return intArrayOf(Events.LOGIN, Events.LOGOUT)
    }

    override fun onEvent(event: Int, vararg args: Any?) {
        when (event) {
            Events.LOGIN -> setViews(args[0] as String?)
            Events.LOGOUT -> setViews(null)
        }
    }
}

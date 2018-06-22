package com.horizon.eventdemo


import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import com.horizon.eventdemo.helper.AccountManager
import com.horizon.base.ui.BaseActivity
import com.horizon.base.ui.DialogTask
import com.horizon.base.util.getStr
import com.horizon.base.util.shortTips


class LoginActivity : BaseActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        findViewById<View>(R.id.login_btn).setOnClickListener { login() }
    }

    private fun login() {
        val account = (findViewById<View>(R.id.account_et) as EditText).text.toString()
        if (TextUtils.isEmpty(account)) {
            shortTips(getString(R.string.enter_account_tips))

            return
        }
        val password = (findViewById<View>(R.id.password_et) as EditText).text.toString()
        if (TextUtils.isEmpty(password)) {
            shortTips(getString(R.string.enter_password_tips))
            return
        }

        LoginTask(this).execute(account, password)
    }

    private class LoginTask internal constructor(activity: Activity) : DialogTask<String, Void, String>(activity) {
        init {
            mMessage = activity.getString(R.string.logging_in)
            mFinishIfDone = true
        }

        override fun doInBackground(vararg params: String): String? {
            try {
                AccountManager.login(params[0], params[1])
            } catch (e: Exception) {
                Log.e("LoginActivity", e.message, e)
                val msg = e.message
                return if (msg.isNullOrEmpty()) getStr(R.string.login_failed) else msg
            }
            return null
        }

        override fun onResult(result: String?) {
            shortTips(if (result.isNullOrEmpty()) getStr(R.string.login_success) else result)
        }
    }
}


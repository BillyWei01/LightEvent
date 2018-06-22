@file:Suppress("DEPRECATION")

package com.horizon.base.ui


import android.app.Activity
import android.app.ProgressDialog
import android.os.AsyncTask
import java.lang.ref.WeakReference

abstract class DialogTask<Params, Void, Result>(activity: Activity) : AsyncTask<Params, Void, Result>() {
    private var mDialog: ProgressDialog? = null
    private val mContextRef: WeakReference<Activity> = WeakReference(activity)
    protected var mMessage = "请稍后..."
    protected var mFinishIfDone = false

    protected abstract fun onResult(result: Result?)

    override fun onPreExecute() {
        val activity = mContextRef.get()
        if (activity != null && !activity.isFinishing) {
            mDialog = ProgressDialog(activity)
            mDialog!!.setMessage(mMessage)
            mDialog!!.setOnCancelListener {
                if (status != AsyncTask.Status.FINISHED) {
                    cancel(true)
                }
            }
            mDialog!!.show()
        } else {
            cancel(false)
        }
    }

    override fun onPostExecute(result: Result) {
        val activity = mContextRef.get()
        if (activity != null && !activity.isFinishing && mDialog != null && mDialog!!.isShowing) {
            try {
                mDialog!!.dismiss()
            } catch (ignore: Exception) {
            }

            onResult(result)

            if (mFinishIfDone) {
                activity.finish()
            }
        }
    }
}

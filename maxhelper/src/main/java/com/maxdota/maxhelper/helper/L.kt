package com.maxdota.maxhelper.helper

import android.util.Log

open class L {
    open fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    open fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

    open fun e(tag: String, message: String) {
        Log.e(tag, message)
    }

    companion object {
        var instance = L()

        fun d(tag: String, message: String) {
            instance.d(tag, message)
        }

        fun w(tag: String, message: String) {
            instance.w(tag, message)
        }

        fun e(tag: String, message: String) {
            instance.e(tag, message)
        }
    }
}
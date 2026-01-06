package com.maxdota.maxhelper.helper

import android.content.Context
import android.widget.Toast

open class DisplayHelper {
  open fun showToast(context: Context?, message: CharSequence?) {
    message?.takeIf { it.isNotEmpty() }?.let {
      Toast.makeText(context, it, Toast.LENGTH_LONG).show()
    }
  }
}
package com.maxdota.core.extension

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: CharSequence) {
  Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showToast(resId: Int) {
  Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
}

fun Context.showShortToast(message: CharSequence) {
  Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showShortToast(resId: Int) {
  Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}
package com.maxdota.core.extension

import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.maxdota.core.lib.AllowClickAndTextSelectionMovementMethod

fun TextView.setDrawableStart(@DrawableRes drawableId: Int?) {
  setCompoundDrawablesWithIntrinsicBounds(
    drawableId ?: 0,
    0,
    0,
    0
  )
}

fun TextView.setTextOrHideWhenEmpty(value: String?) {
  if (value.isNullOrEmpty()) {
    gone()
  } else {
    visible()
    text = value
  }
}

fun TextView.setTextOrMakeInvisibleWhenEmpty(value: String?) {
  if (value.isNullOrEmpty()) {
    invisible()
  } else {
    visible()
    text = value
  }
}

// on fragment resume, the selectable attribute will be lost unexpectedly
// resetting it as in this method will help to fix it
fun TextView.resetIsSelectable() {
  Handler(Looper.getMainLooper()).post {
    setTextIsSelectable(false)
    setTextIsSelectable(true)
    movementMethod = AllowClickAndTextSelectionMovementMethod()
  }
}

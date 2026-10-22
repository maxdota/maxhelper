package com.maxdota.core.listener

import android.view.View

abstract class OnThrottledClickListener(
  val throttleTime: Long
) : View.OnClickListener {
  private var lastClickTime = 0L

  override fun onClick(view: View?) {
    val now = System.currentTimeMillis()
    if (now - lastClickTime < throttleTime) {
      return
    }
    lastClickTime = now
    onThrottledClick(view)
  }

  abstract fun onThrottledClick(view: View?)
}
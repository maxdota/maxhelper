package com.maxdota.core.extension

import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.maxdota.core.listener.OnThrottledClickListener

fun View.visible() {
  visibility = VISIBLE
}

fun View.invisible() {
  visibility = INVISIBLE
}

fun View.gone() {
  visibility = GONE
}

fun View.setOnThrottleClickListener(
  throttleTime: Long = 1000L,
  listener: View.OnClickListener
) {
  setOnClickListener(
    object : OnThrottledClickListener(throttleTime) {
      override fun onThrottledClick(view: View?) {
        listener.onClick(view)
      }
    }
  )
}

fun View.setOnThrottleClickListener(
  listener: View.OnClickListener
) {
  setOnClickListener(
    object : OnThrottledClickListener(1000L) {
      override fun onThrottledClick(view: View?) {
        listener.onClick(view)
      }
    }
  )
}
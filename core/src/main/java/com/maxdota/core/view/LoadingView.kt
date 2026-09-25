package com.maxdota.core.view

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.widget.ContentLoadingProgressBar
import com.maxdota.core.R

class LoadingView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {
  init {
    inflate(context, R.layout.view_loading, this)
    isClickable = true
    isFocusable = true
    onInit()
  }

  private lateinit var pbLoading: ContentLoadingProgressBar

  private fun onInit() {
    pbLoading = findViewById(R.id.pbLoading)
  }

  fun show() {
    visibility = VISIBLE
  }

  fun hide() {
    visibility = GONE
  }
}
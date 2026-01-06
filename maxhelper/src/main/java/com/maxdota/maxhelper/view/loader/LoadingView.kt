package com.maxdota.maxhelper.view.loader

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.widget.ContentLoadingProgressBar
import com.maxdota.maxhelper.R

class LoadingView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {
    init {
        inflate(context, R.layout.view_loading, this)
        onInit()
    }

    private lateinit var pbLoading: ContentLoadingProgressBar

    private fun onInit() {
        pbLoading = findViewById(R.id.pbLoading)
    }

    fun triggerVisibility(isVisible: Boolean) {
        visibility = if (isVisible) {
            pbLoading.show()
            VISIBLE
        } else {
            pbLoading.hide()
            GONE
        }
    }
}
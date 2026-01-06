package com.maxdota.maxhelper.view

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.google.android.material.appbar.MaterialToolbar
import com.maxdota.maxhelper.R

class BasicToolbarView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {
    init {
        inflate(context, R.layout.view_basic_toolbar, this)
        onInit()
    }

    lateinit var toolbar: MaterialToolbar

    private fun onInit() {
        toolbar = findViewById(R.id.toolbar)
    }

    fun setTitleToCenter() {
        toolbar.isTitleCentered = true
    }
}
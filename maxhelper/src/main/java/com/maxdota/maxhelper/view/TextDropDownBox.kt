package com.maxdota.maxhelper.view

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import com.maxdota.maxhelper.R

class TextDropDownBox(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {
    init {
        inflate(context, R.layout.box_dropdown_text, this)
        onInit()
    }

    private lateinit var textLabel: TextView

    private fun onInit() {
        textLabel = findViewById(R.id.textLabel)
    }

    fun setData(label: String?) {
        textLabel.text = label
    }
}
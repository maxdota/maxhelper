package com.maxdota.core.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity.CENTER_HORIZONTAL
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.withStyledAttributes
import com.maxdota.core.R
import com.maxdota.core.extension.setTextOrHideWhenEmpty
import com.maxdota.core.extension.visible

class ListEmptyView(context: Context, attrs: AttributeSet?) : LinearLayoutCompat(context, attrs) {
  init {
    inflate(context, R.layout.view_list_empty, this)
    onInit()
    setAttributes(attrs)
  }

  private lateinit var imageView: AppCompatImageView
  private lateinit var textLabel: AppCompatTextView
  private lateinit var textDescription: AppCompatTextView

  private fun onInit() {
    orientation = VERTICAL
    gravity = CENTER_HORIZONTAL
    imageView = findViewById(R.id.imageView)
    textLabel = findViewById(R.id.textLabel)
    textDescription = findViewById(R.id.textDescription)
  }

  @SuppressLint("CustomViewStyleable")
  private fun setAttributes(attrs: AttributeSet?) {
    context.withStyledAttributes(attrs, R.styleable.BaseAttributes) {
      getDrawable(R.styleable.BaseAttributes_imageIcon)?.let { drawable ->
        imageView.setImageDrawable(drawable)
      }
      textLabel.setTextOrHideWhenEmpty(
        getString(R.styleable.BaseAttributes_textLabel)
      )
      textDescription.setTextOrHideWhenEmpty(
        getString(R.styleable.BaseAttributes_textDescription)
      )
    }
  }

  fun setData(labelRes: Int? = null, descriptionRes: Int? = null) {
    labelRes?.let {
      textLabel.visible()
      textLabel.setText(it)
    }
    descriptionRes?.let {
      textDescription.visible()
      textDescription.setText(it)
    }
  }
}
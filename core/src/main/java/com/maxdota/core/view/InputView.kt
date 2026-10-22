package com.maxdota.core.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import com.maxdota.core.R
import com.maxdota.core.extension.gone
import com.maxdota.core.extension.setTextOrHideWhenEmpty
import com.maxdota.core.extension.visible

class InputView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
  init {
    inflate(context, R.layout.view_input, this)
    orientation = VERTICAL
    onInit()
    setAttributes(attrs)
  }

  private lateinit var editText: EditText
  private lateinit var textError: TextView

  private fun onInit() {
    editText = findViewById(R.id.editText)
    textError = findViewById(R.id.textError)
  }

  @SuppressLint("CustomViewStyleable")
  private fun setAttributes(attrs: AttributeSet?) {
    context.withStyledAttributes(attrs, R.styleable.BaseAttributes) {
      editText.hint = getString(R.styleable.BaseAttributes_textHint).orEmpty()
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        editText.setAutofillHints(
          getString(R.styleable.BaseAttributes_textAutofillHint).orEmpty()
        )
      }
      editText.inputType = getInt(R.styleable.BaseAttributes_android_inputType, 0)
    }
  }

  fun setError(value: String?) {
    if (value.isNullOrBlank()) {
      textError.gone()
      editText.setBackgroundResource(R.drawable.background_round_corner_stroke_gray)
    } else {
      textError.text = value
      textError.visible()
      editText.setBackgroundResource(R.drawable.background_round_corner_stroke_red)
    }
  }

  fun removeError() {
    if (textError.isVisible) {
      setError(null)
    }
  }

  fun getText() = editText.text.toString()

  fun getEditText() = editText
}
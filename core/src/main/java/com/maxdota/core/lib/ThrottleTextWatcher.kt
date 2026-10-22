package com.maxdota.core.lib

import android.text.Editable
import android.text.TextWatcher

class ThrottleTextWatcher(
  val throttleTime: Long = 400L,
  private val onTextChange: (String) -> Unit
) : TextWatcher {
  private var lastTextChangeText = ""
  private var lastTextChangeTime = 0L

  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    val searchText = s.toString().trim()
    if (searchText == lastTextChangeText) return

    val now = System.currentTimeMillis()
    if (now - lastTextChangeTime < throttleTime) {
      return
    }
    lastTextChangeTime = now

    lastTextChangeText = searchText
    onTextChange.invoke(lastTextChangeText)
  }

  override fun afterTextChanged(s: Editable?) = Unit
  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
}
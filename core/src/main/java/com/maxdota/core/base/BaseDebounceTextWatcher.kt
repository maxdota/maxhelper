package com.maxdota.core.base

import android.text.Editable
import android.text.TextWatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BaseDebounceTextWatcher(
  private val debounceTime: Long = 400L,
  private val onTextChange: (String) -> Unit
) : TextWatcher {
  private var searchFor = ""

  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    val searchText = s.toString().trim()
    if (searchText == searchFor) return

    searchFor = searchText

    GlobalScope.launch {
      delay(debounceTime)  //debounce timeOut
      if (searchText != searchFor) return@launch
      onTextChange.invoke(searchFor)
    }
  }

  override fun afterTextChanged(s: Editable?) = Unit
  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
}
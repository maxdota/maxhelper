package com.maxdota.core.extension

import android.widget.ImageView
import java.lang.reflect.Field

// drawableClass = R.drawable::class.java
fun ImageView.setImageResourceFromString(
  drawableClass: Class<Any>, resName: String?, defaultRes: Int
) {
  setImageResource(
    resName?.takeIf { it.isNotBlank() }?.let {
      try {
        val idField: Field = drawableClass.getDeclaredField((it))
        idField.getInt(idField)
      } catch (e: Exception) {
        defaultRes
      }
    } ?: defaultRes
  )
}